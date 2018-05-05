package part3_maze;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Main class to run game
 *
 * All class variables are package bound to be able to execute different tests.
 *
 * Total Amount of Methods: 13
 * Total Amount of Variables: 5
 */
public class Game
{
    GameMapCreator gameMapCreator;          // The game map creator which is responsible for creating the game maps
    static GameMap generatedMap;                   // Stores the created map for the team or players
    boolean playerWon;                      // Boolean to determine if a player has won the game
    static int playerTurn;                  // The index of which player is going to play
    static Player[] players;                // An array which stores all the players within the game
    ArrayList<Integer> lostPlayers;         // An array of integers which stores all the players who lost the game
    static ArrayList<Team> teams;
    private int totalPlayers;

    /**
     * Game constructor.
     *
     * Receives the amount of players and map size from user input.
     * user input is verified trough different methods in the class.
     *
     * Used in main method.
     */
    Game()
    {
        gameMapCreator = new GameMapCreator(); // Creating a Game Map Creator

        Scanner scanner = new Scanner(System.in);
        lostPlayers = new ArrayList<Integer>();
        teams = new ArrayList<Team>();
        boolean validInput = false;

        int gameModeInput = 0;

        while (!validInput)
        {
            System.out.println("Please choose the Game mode");
            System.out.println("1) Solo Mode");
            System.out.println("2) Team Mode");
            gameModeInput = scanner.nextInt();

            if(gameModeInput != 1 && gameModeInput != 2)    {   System.out.println("Please enter a valid option");  }
            else                                            {   validInput = true;                                  }
        }

        switch (gameModeInput)
        {
            case 1: {   soloMode();             break;  }
            case 2: {   collaborationMode();    break;  }
        }

        playerWon = false;
    }

    private void soloMode()
    {
        int mapSizeInput = 1;
        boolean validInput = false;
        int amountOfPlayersInput = 0;
        Scanner scanner = new Scanner(System.in);

        while (!validInput)
        {
            try
            {
                System.out.println("Please enter the number of players");
                amountOfPlayersInput = scanner.nextInt();

                System.out.println("Please enter the dimension size of the map");
                mapSizeInput = scanner.nextInt();

                checkPlayerMap(amountOfPlayersInput, mapSizeInput);
                validInput = true;
            }
            catch (PlayerMapRatioException playerMapRatioException)
            {
                System.out.println(playerMapRatioException.getMessage());
            }
        }

        totalPlayers = amountOfPlayersInput;
        generatedMap = gameMapCreator.generateGameMap(GameMapCreator.MapType.MAP_SAFE, mapSizeInput, mapSizeInput);

        for (int i = 0; i < amountOfPlayersInput; i++)
        {
            teams.add(new Team(1, new int[]{i}));
        }
    }

    private void collaborationMode()
    {
        int mapSizeInput;
        int teamLimitInput = 0;
        int amountOfPlayersInput = 0;
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);

        while (!validInput)
        {
            try
            {
                System.out.println("Please enter the number of players");
                amountOfPlayersInput = scanner.nextInt();

                System.out.println("Please enter the number of players per team");
                teamLimitInput = scanner.nextInt();

                System.out.println("Please enter the dimension size of the map");
                mapSizeInput = scanner.nextInt();

                if(checkValidateTeamToPlayerRatio(amountOfPlayersInput,teamLimitInput))
                {
                    checkPlayerMap(amountOfPlayersInput, mapSizeInput);
                    validInput = true;
                }
                else
                {
                    System.out.println("Incorrect Player to team ratio");
                }
            }
            catch (PlayerMapRatioException playerMapRatioException)
            {
                System.out.println(playerMapRatioException.getMessage());
            }
        }

        totalPlayers = amountOfPlayersInput;
        createTeams(amountOfPlayersInput, teamLimitInput);
    }

    private void createTeams(int playerAmount, int teamAmount)
    {
        ArrayList<Integer> uniquePlayers = new ArrayList<Integer>();

        for (int i = 0; i < playerAmount; i++)  {   uniquePlayers.add(i);   }

        if(playerAmount % teamAmount != 0)
        {
            int temp = playerAmount;

            for (int i = 0; i < teamAmount; i++)
            {
                if(i == teamAmount-1)
                {
                    createTeam(temp, uniquePlayers);
                }
                else
                {
                    int team_size = (int) Math.ceil(temp/teamAmount) + 1;

                    temp -= team_size;
                    createTeam(team_size, uniquePlayers);
                }
            }
        }
        else
        {
            for (int i = 0; i < teamAmount; i++)
            {
                createTeam(playerAmount/teamAmount, uniquePlayers);
            }
        }
    }

    private boolean checkValidateTeamToPlayerRatio(int amountOfPlayer, int teamSize)
    {
        return amountOfPlayer / teamSize != 0;
    }

    private void createTeam(int teamSize, ArrayList<Integer> unusedPlayers)
    {
        Random rand = new Random();
        int[] teamPlayers = new int[teamSize];

        for (int i = 0; i < teamSize; i++)
        {
            int player = unusedPlayers.get(rand.nextInt(unusedPlayers.size()));
            teamPlayers[i] = player;

            for (int j = 0; j < unusedPlayers.size(); j++)
            {
                if(unusedPlayers.get(j) == player)  {   unusedPlayers.remove(j);    }
            }
        }

        teams.add(new Team(teamSize, teamPlayers));
    }

    /**
     * Verifies the input from the user too see whether the player count
     * and map size are acceptable. If not a custom exception is thrown.
     *
     * This method is used in the game constructor.
     *
     * @param amountOfPlayers the amount of players
     * @param mapSize the size of the map(MapSize x MapSize)
     * @throws PlayerMapRatioException if the amount of players is not allowed in the map size
     */
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

        generatedMap = gameMapCreator.generateGameMap(GameMapCreator.MapType.MAP_SAFE, mapSize, mapSize);

        setNumberOfPlayers(amountOfPlayers, mapSize);
    }

    /**
     * Checks whether all players are dead.
     *
     * Used in startGame()
     *
     * @return true if all dead / false if not all dead.
     */
    boolean allPlayersAreDead()
    {
        if(lostPlayers.size() == totalPlayers)
        {
            System.out.println("All players are dead");
            return true;
        }

        return false;
    }

    /**
     * Switches to a player that is still alive.
     *
     * Used in startGame()
     */
    void switchToAlivePlayer()
    {
        while(isPlayerDead (playerTurn))
        {
            if((playerTurn += 1) >= totalPlayers)    {   playerTurn = 0; }
        }
    }

    /**
     * Core method to play the game.
     * Checks if the players wins after input.
     * Uses different methods in the class to verify a player's status in the game.
     * Also uses generateHTMLFiles() to generate files for specific players
     */
    void startGame()
    {
        Random rand = new Random();
        playerTurn = rand.nextInt(totalPlayers);

        while (!playerWon)
        {
            generateHTMLFiles();
            if(allPlayersAreDead())   {   break;    }

            switchToAlivePlayer();
            tryToMove();
            CheckMovedTile();

            if((playerTurn += 1) >= totalPlayers - 1)    {   playerTurn = 0; }
        }
    }

    /**
     * Gets input from user and verifies that it's a valid move.
     *
     * Used in startGame()
     */
    void tryToMove()
    {
        boolean validInput = false;
        Scanner scanner = new Scanner(System.in);

        System.out.println("Player " + playerTurn + ": Make your move");

        do
        {
            char moveDirection = scanner.next().charAt(0);

            if(!currentPlayer().move(moveDirection, generatedMap))
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
                currentPlayer().getMovedPositions().get(currentPlayer().getMovedPositions().size() - 2) + " to " +
                currentPlayer().getMovedPositions().get(currentPlayer().getMovedPositions().size() - 1));
    }

    static Player currentPlayer()
    {
        for (Team team : teams)
        {
            for (Player player : team.players)
            {
                if (player.playerID == playerTurn)
                {
                    return player;
                }
            }
        }

        return null;
    }

    /**
     * Checks the tile that a user moves into.
     * if the tile is water the user dies.
     * if the tile is treasure the user wins.
     *
     * Used in startGame()
     */
    void CheckMovedTile()
    {
        Position lastPlayerPosition = currentPlayer().getLastPosition();

        switch (GameMap.getMapInstance().getTileType(new Position(lastPlayerPosition.getXCoordinate(), lastPlayerPosition.getYCoordinate())))
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
     *
     * Used in startGame().
     */
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
     *
     * @return the contents of the html to be write to the file
     */
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
                "    i {font-size: 25px; color: black;}\n" +
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
                    .append(generatedMap.getMapDetail().length + 1)
                    .append("\"> <h2> Player ")
                    .append(String.format("%02d", playerTurn))
                    .append(" </h2> <p> Moves: ")
                    .append(currentPlayer().getMoveDirections())
                    .append(" </p> </th>\n");

        for (int i = 0; i < generatedMap.getMapDetail().length + 1; i ++)
        {
            htmlString.append("    <tr>\n");

            for (int j = 0; j < generatedMap.getMapDetail()[0].length + 1; j ++)
            {
                if (i == 0 && j == 0)   {   htmlString.append("        <th class=\"coordinateCell\"></th> \n");                                                     }
                else if (i == 0)        {   htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", j - 1)).append("</th> \n");     }
                else if (j == 0)        {   htmlString.append("        <th class=\"coordinateCell\">").append(String.format("%02d", i - 1)).append("</th> \n");     }
                else
                {
                    htmlString.append("        <th class=\"");

                    if (currentPlayer().isInMovedList(new Position(j - 1, i - 1)))
                    {
                        htmlString.append(generatedMap.tileToString(generatedMap.getTileType(new Position(j - 1, i - 1)))).append("\">");

                        if (currentPlayer().getMovedPositions().get(currentPlayer().getMovedPositions().size() - 1).getYCoordinate() == i - 1 &&
                                currentPlayer().getMovedPositions().get(currentPlayer().getMovedPositions().size() - 1).getXCoordinate() == j - 1)
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
     *
     * Used in checkPlayerMap()
     *
     * @param amountOfPlayers the number of players
     * @param mapSize the size of the map
     * @return true for correct amount of player / false for incorrect amount of players
     */
    private boolean setNumberOfPlayers(int amountOfPlayers, int mapSize)
    {
        Random rand = new Random();
        players = new Player[amountOfPlayers];

        if(2 <= amountOfPlayers && amountOfPlayers <= 8)
        {
            for (int i = 0; i < amountOfPlayers; i ++)
            {
                players[i] = new Player(mapSize,1);

                while(!generatedMap.isTileNotUsed(players[i].getLastPosition()))
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
     *
     * Used in switchToAlivePlayer()
     *
     * @param player - the player to check for
     * @return true if dead / false if alive
     */
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
     */
    public static void main(String args[])
    {
        Game game = new Game();
        game.startGame();
    }
}
