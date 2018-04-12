package part2_maze;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game
{
    private static Map map;
    public static int playerTurn;
    private static Player[] players;
    public static String current_file_name;
    private ArrayList<Integer> lost_players;

    public void startGame()
    {
        lost_players = new ArrayList<Integer>();
        boolean playerWon = false;
        Random rand = new Random();
        InputStream stdin = System.in;
        Scanner scanner = new Scanner(stdin);

        System.out.println("Please enter the number of players");
        int amountOfPlayers = scanner.nextInt();

        System.out.println("Please enter the dimension size of the map");
        int mapSize = scanner.nextInt();

        map = new Map();
        map.setMapSize(mapSize, mapSize);
        map.generate();
        players = new Player[amountOfPlayers];
        playerTurn = rand.nextInt(players.length);
        setNumberOfPlayers(amountOfPlayers);

        while (!playerWon)
        {
            boolean validInput = false;
            generateHTMLFiles();
            if(lost_players.size() == amountOfPlayers){
                System.out.println("All players are dead");
                break;
            }

            while(!checkifplayerisdead(playerTurn)){
                playerTurn+=1;
                if(playerTurn >= players.length)    {   playerTurn = 0; }
            }

            System.out.println("Player " + playerTurn + ": Make your move");

            do
            {
                char moveDirection = scanner.next().charAt(0);

                if(!players[playerTurn].move(moveDirection, map))
                {
                    System.out.println("Try again and make sure 'U' or 'L' or 'D' or 'R' are inputted and are within the map");
                }
                else
                {
                    generateHTMLFiles();
                    validInput = true;
                }
            } while (!validInput);

            System.out.println("Successfully moved from " + players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 2) +
                                " to " + players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 1));

            Position pos = players[playerTurn].getLastPos();
            switch (map.getTileType(pos.getXCoordinate(),pos.getYCoordinate())){
                case WATER:
                    System.out.println("Player: "+playerTurn+" Has died");
                    lost_players.add(playerTurn);
                break;
                case TREASURE:
                    System.out.println("Player: "+playerTurn+" Has Won the Game!");
                    playerWon = true;
            }

            if(playerTurn ++ >= players.length - 1)    {   playerTurn = 0; }
        }

    }

    public static void generateHTMLFiles()
    {
        try
        {
            current_file_name = "map_player_"+String.format("%02d", playerTurn) + ".html";
            File playerMapFile = new File("src/gameFiles/map_player_" + String.format("%02d", playerTurn) + ".html");
            playerMapFile.createNewFile();

            FileWriter fw = new FileWriter(playerMapFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fw);
            bufferedWriter.write(htmlString());

            bufferedWriter.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    public static String htmlString()
    {
        StringBuilder htmlString = new StringBuilder();

        htmlString.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<style type=\"text/css\">\n" +
                "    h2, p {margin: 0;}\n" +
                "    i {font-size: 40px; color: white;}\n" +
                "    table {height: 100vh; width: 100vw;}\n" +
                "    .tg td{font-family:Arial, sans-serif; font-size:25px; border-style:solid; border-width:1px; border-color:black; text-align: center;}\n" +
                "    .tg th{font-family:Arial, sans-serif; font-size:25px; border-style:solid; border-width:1px; border-color:black; text-align: center;}\n" +
                "    .tg .tableHeading{font-family:\"Arial Black\", sans-serif !important; border-color:#ffffff; background-color: blue; color: white;}\n" +
                "    .tg .coordinateCell{font-family:\"Arial Black\", sans-serif !important; border-color:#ffffff}\n" +
                "    .tg .grassTile{font-family:\"Arial Black\", sans-serif !important; background-color:#008000; border-color:#000000}\n" +
                "    .tg .waterTile{font-family:\"Arial Black\", sans-serif !important; background-color:#ADD8E6; border-color:#000000}\n" +
                "    .tg .treasureTile{font-family:\"Arial Black\", sans-serif !important; background-color:#FFFF00; border-color:#000000}\n" +
                "    .tg .unknownTile{font-family:\"Arial Black\", sans-serif !important; background-color:#c0c0c0; border-color:#000000}\n" +
                "</style>\n" +
                "\n" +
                "<table class=\"tg\">");

        htmlString.append("\n        <th class=\"tableHeading\" colspan=\"")
                    .append(map.getMapDetail().length + 1)
                    .append("\"> <h2> Player ")
                    .append(String.format("%02d", playerTurn))
                    .append("</h2> <p> Moves: ")
                    .append(players[playerTurn].getMoves())
                    .append(" </p> </th>\n");

        for (int i = 0; i < map.getMapDetail().length + 1; i ++)
        {
            htmlString.append("    <tr>\n");

            for (int j = 0; j < map.getMapDetail()[0].length + 1; j ++)
            {
                if (i == 0 && j == 0)
                {
                    htmlString.append("        <th class=\"coordinateCell\"></th> \n");
                }
                else if (i == 0)
                {
                    htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", j - 1)).append("</th> \n");
                }
                else if (j == 0)
                {
                    htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", i - 1)).append("</th> \n");
                }
                else
                {
                    htmlString.append("        <th class=\"");

                    if (players[playerTurn].isInMovedList(new Position(j - 1, i - 1)))
                    {
                        htmlString.append(map.tileToString(map.getTileType(j - 1, i - 1))).append("\">");

                        if (players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 1).getYCoordinate() == i - 1 &&
                                players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 1).getXCoordinate() == j - 1)
                        {
                            htmlString.append("<i class=\"glyphicon glyphicon-user\"></i>");
                        }
                    }
                    else
                    {
                        htmlString.append("unknownTile\">");
                    }

                    htmlString.append("</th>\n");
                }
            }

            htmlString.append("    </tr>\n");
        }

        htmlString.append("</table>\n </body>\n </html>");

        return htmlString.toString();
    }

    public boolean setNumberOfPlayers(int amountOfPlayers)
    {
        if(2 <= amountOfPlayers && amountOfPlayers <= 8)
        {
            for (int i = 0; i < amountOfPlayers; i ++)
            {
                players[i] = new Player(map.getMapDetail().length);
            }

            return true;
        }
        else
        {
            return false;
        }
    }
    public boolean checkifplayerisdead(int player){
        for (int lost_player : lost_players) {
            if (lost_player == player)
                return false;
        }
        return true;
    }
    public static void main(String args[])
    {
        Game game = new Game();
        game.startGame();
    }
}
