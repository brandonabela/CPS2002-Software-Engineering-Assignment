package part2_maze;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game
{
    static Map map;
    boolean playerWon;
    static int playerTurn;
    static Player[] players;
    ArrayList<Integer> lostPlayers;

    Game()
    {
        map = new Map();

        Scanner scanner = new Scanner(System.in);
        lostPlayers = new ArrayList<Integer>();

        boolean validInput = false;

        while (!validInput)
        {
            try
            {
                System.out.println("Please enter the number of players");
                int amountOfPlayersInput = scanner.nextInt();

                System.out.println("Please enter the dimension size of the map");
                int mapSizeInput = scanner.nextInt();

                checkPlayerMap(amountOfPlayersInput, mapSizeInput);
                validInput = true;
            }
            catch (PlayerMapRatioException playerMapRatioException)
            {
                System.out.println(playerMapRatioException.getMessage());
            }
        }

        playerWon = false;
    }

    void checkPlayerMap(int amountOfPlayers, int mapSize) throws PlayerMapRatioException
    {
        if((2 <= amountOfPlayers && amountOfPlayers <= 4) && (5 <= mapSize && mapSize <= 50))
        {
            System.out.println("Player Count: " + amountOfPlayers + "\n" + "Map Dimension: " + mapSize + "x" + mapSize);
        }
        else if((5 <= amountOfPlayers && amountOfPlayers <= 8) && (8 <= mapSize && mapSize <= 50))
        {
            System.out.println("Player Count: " + amountOfPlayers + "\n" + "Map Dimension: " + mapSize + "x" + mapSize);
        }
        else
        {
            throw new PlayerMapRatioException(amountOfPlayers, mapSize);
        }

        map.setMapSize(mapSize, mapSize);
        map.generate();

        setNumberOfPlayers(amountOfPlayers, mapSize);
    }

    boolean allPlayersAreDead()
    {
        if(lostPlayers.size() == players.length)
        {
            System.out.println("All players are dead");
            return true;
        }

        return false;
    }

    void switchToAlivePlayer()
    {
        while(isPlayerDead (playerTurn))
        {
            if(playerTurn++ >= players.length)    {   playerTurn = 0; }
        }
    }

    void startGame()
    {
        Random rand = new Random();
        playerTurn = rand.nextInt(players.length);

        while (!playerWon)
        {
            generateHTMLFiles();
            if(allPlayersAreDead())   {   break;    }

            switchToAlivePlayer();
            tryToMove();
            CheckMovedTile();

            if(playerTurn ++ >= players.length - 1)    {   playerTurn = 0; }
        }
    }

    void tryToMove()
    {
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);

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

        System.out.println("Successfully moved from " +
                            players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 2) + " to " +
                            players[playerTurn].getMovedPositions().get(players[playerTurn].getMovedPositions().size() - 1));
    }

    void CheckMovedTile()
    {
        Position lastPlayerPosition = players[playerTurn].getLastPosition();

        switch (map.getTileType(lastPlayerPosition.getXCoordinate(), lastPlayerPosition.getYCoordinate()))
        {
            case WATER :
            {
                System.out.println("Player: " + playerTurn + " has died");
                lostPlayers.add(playerTurn);
                break;
            }

            case TREASURE:
            {
                System.out.println("\nPlayer: " + playerTurn + " has Won the Game!");
                playerWon = true;
                break;
            }
        }
    }

    static void generateHTMLFiles()
    {
        try
        {
            File playerMapFile = new File("src/gameFiles/map_player_" + String.format("%02d", playerTurn) + ".html");
            playerMapFile.createNewFile();

            FileWriter fileWriter = new FileWriter(playerMapFile);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(htmlString());

            bufferedWriter.close();
        }
        catch (IOException exception)
        {
            exception.printStackTrace();
        }
    }

    static String htmlString()
    {
        StringBuilder htmlString = new StringBuilder();

        htmlString.append("<!DOCTYPE html>\n" +
                "<html>\n<head>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<style type=\"text/css\">\n" +
                "    h2, p {margin: 0;}\n" +
                "    i {font-size: 25px; color: white;}\n" +
                "    table {height: 100vh; width: 100vh;}\n" +
                "    .tg th{font-family:Arial, sans-serif; font-size: 18px; border-style:solid; border-width: 3px; border-color:black; text-align: center;}\n" +
                "    .tg .tableHeading{font-family:\"Arial Black\", sans-serif !important; border:none; background-color: blue; color: white;}\n" +
                "    .tg .coordinateCell{font-family:\"Arial Black\", sans-serif !important; border:none;}\n" +
                "    .tg .grassTile{font-family:\"Arial Black\", sans-serif !important; background-color:#008000; border-color:#000000}\n" +
                "    .tg .waterTile{font-family:\"Arial Black\", sans-serif !important; background-color:#ADD8E6; border-color:#000000}\n" +
                "    .tg .treasureTile{font-family:\"Arial Black\", sans-serif !important; background-color:#FFFF00; border-color:#000000}\n" +
                "    .tg .unknownTile{font-family:\"Arial Black\", sans-serif !important; background-color:#c0c0c0; border-color:#000000}\n" +
                "</style>\n\n" +
                "<table class=\"tg\">");

        htmlString.append("\n    <th class=\"tableHeading\" colspan=\"")
                    .append(map.getMapDetail().length + 1)
                    .append("\"> <h2> Player ")
                    .append(String.format("%02d", playerTurn))
                    .append(" </h2> <p> Moves: ")
                    .append(players[playerTurn].getMoves())
                    .append(" </p> </th>\n");

        for (int i = 0; i < map.getMapDetail().length + 1; i ++)
        {
            htmlString.append("    <tr>\n");

            for (int j = 0; j < map.getMapDetail()[0].length + 1; j ++)
            {
                if (i == 0 && j == 0)   {   htmlString.append("        <th class=\"coordinateCell\"></th> \n");                                                     }
                else if (i == 0)        {   htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", j - 1)).append("</th> \n");     }
                else if (j == 0)        {   htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", i - 1)).append("</th> \n");     }
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

        htmlString.append("</table>\n</body>\n</html>");

        return htmlString.toString();
    }

    boolean setNumberOfPlayers(int amountOfPlayers, int mapSize)
    {
        Random rand = new Random();
        players = new Player[amountOfPlayers];

        if(2 <= amountOfPlayers && amountOfPlayers <= 8)
        {
            for (int i = 0; i < amountOfPlayers; i ++)
            {
                players[i] = new Player(mapSize);

                while(!map.isTileBeingUsed(players[i].getLastPosition()))
                {
                    players[i].setPlayerStartPosition(new Position(rand.nextInt(mapSize), rand.nextInt(mapSize)));
                }
            }

            return true;
        }
        else
        {
            return false;
        }
    }

    boolean isPlayerDead(int player)
    {
        for (int aLostPlayer : lostPlayers)
        {
            if (aLostPlayer == player)  {   return true;   }
        }

        return false;
    }

    public static void main(String args[])
    {
        Game game = new Game();
        game.startGame();
    }
}
