package part2_maze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game
{
    private static Map map;
    private static int playerTurn;
    private static Player[] players;

    private Game(int amountOfPlayers, int mapSize)
    {
        map = new Map();
        map.setMapSize(mapSize, mapSize);
        map.generate();

        players = new Player[amountOfPlayers];
        setNumberOfPlayers(amountOfPlayers);
    }

    private void startGame()
    {
        boolean playerWon = false;
        Random rand = new Random();

        Scanner scanner = new Scanner(System.in);
        playerTurn = rand.nextInt(players.length);

        while (!playerWon)
        {
            boolean validInput = false;

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
                    validInput = true;
                }
            } while (!validInput);

            System.out.println("Successfully moved from " + players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 2) +
                                " to " + players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 1));

            generateHTMLFiles();

            if(playerTurn ++ >= players.length - 1)    {   playerTurn = 0; }
        }

    }

    private static void generateHTMLFiles()
    {
        try
        {
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

    private static String htmlString()
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

    private boolean setNumberOfPlayers(int amountOfPlayers)
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

    public static void main(String args[])
    {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the number of players");
        int amountOfPlayers = sc.nextInt();

        System.out.println("Please enter the dimension size of the map");
        int mapSize = sc.nextInt();

        Game game = new Game(amountOfPlayers, mapSize);
        game.startGame();
    }
}
