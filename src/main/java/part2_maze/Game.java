package part2_maze;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class to run game
 *
 * All class variables are package bound to be able to
 * execute different tests.
 *
 * Total Amount of Methods: 13
 * Total Amount of Variables: 5
 * */

public class Game
{
    static Map map;
    boolean playerWon;
    static int playerTurn;
    static Player[] players;
    ArrayList<Integer> lostPlayers;
    /**
     * Game constructor.
     *
     * Receives the amount of players and map size from user input.
     * user input is verified trough different methods in the class.
     *
     * Used in main method.
     *
     * */
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

    /**
     * Verifies the input from the user too see whether the player count
     * and map size are acceptable. If not a custom exception is thrown.
     *
     * This method is used in the game constructor.
     *
     * @param amountOfPlayers: the amount of players
     * @param mapSize: the size of the map(MapSize x MapSize)
     *
     * */
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

    /**
    * Checks whether all players are dead.
    * Used in startGame()
    *
    *@noparam
    *@return true if all dead / false if not all dead.
    *
    * */
    boolean allPlayersAreDead()
    {
        if(lostPlayers.size() == players.length)
        {
            System.out.println("All players are dead");
            return true;
        }

        return false;
    }

    /**
     * Switches to a player that is still alive.
     * Used in startGame()
     *
     *@noparam
     *@return  void
     *
     * */
    void switchToAlivePlayer()
    {
        while(isPlayerDead (playerTurn))
        {
            if((playerTurn += 1) >= players.length)    {   playerTurn = 0; }
        }
    }

    /**
     * Core method to play the game.
     * Checks if the players wins after input.
     * Uses different methods in the class to verify a player's status in the game.
     * Also uses generateHTMLFiles() to generate files for specific players.
     *
     *@noparam
     *@return  void
     * */
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

            if((playerTurn += 1) >= players.length - 1)    {   playerTurn = 0; }
        }
    }

    /**
     * Gets input from user and verifies that it's a valid move.
     * Used in startGame()
     *
     *@noparam
     *@return  void
     * */
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

    /**
     * Checks the tile that a user moves into.
     * if the tile is water the user dies.
     * if the tile is treasure the user wins.
     * Used in startGame()
     *
     *@noparam
     *@return  void
     * */
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

    /**
     * Generates the required html for the current player.
     * Used in startGame().
     *
     *@noparam
     *@return  void
     * */
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

    /**
     * Generates the required string for the html.
     * Used in generateHTMLFiles().
     *
     *@noparam
     *@return  string
     * */
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

    /**
     * Gives the players starting positions which are non water tiles and non treasure tiles
     * Used in checkPlayerMap()
     *
     *@param amountOfPlayers - number of players
     *@param mapSize - map size
     *@return  true for correct amount of player / false for incorrect amount of players
     * */
    boolean setNumberOfPlayers(int amountOfPlayers, int mapSize)
    {
        Random rand = new Random();
        players = new Player[amountOfPlayers];

        if(2 <= amountOfPlayers && amountOfPlayers <= 8)
        {
            for (int i = 0; i < amountOfPlayers; i ++)
            {
                players[i] = new Player(mapSize);

                while(!map.isTileNotUsed(players[i].getLastPosition()))
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

    /**
     * Checks if a player is dead.
     * Used in switchToAlivePlayer()
     *
     *@param player - the player to check for.
     *@return  true if dead / false if alive
     * */
    boolean isPlayerDead(int player)
    {
        for (int aLostPlayer : lostPlayers)
        {
            if (aLostPlayer == player)  {   return true;   }
        }

        return false;
    }

    /**
     * Main method used to invoke startGame()
     *
     *
     * */
    public static void main(String args[])
    {
        Game game = new Game();
        game.startGame();
    }
}
