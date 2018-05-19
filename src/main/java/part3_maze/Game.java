package part3_maze;

// Importing Libraries and Classes

import part3_maze.exception.PlayerMapRatioException;
import part3_maze.gameMapCreator.GameMapCreator;
import part3_maze.gameMapCreator.GameMapCreatorHazardousMap;
import part3_maze.gameMapCreator.GameMapCreatorSafeMap;
import part3_maze.gameMaps.GameMap;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/** This class is responsible for running the game
 *
 *  All class variables are package bound to be able to execute different tests
 *
 *  Total Amount of Methods: 19
 *  Total Amount of Variables: 7
 */

public class Game
{
    boolean playerWon;                      // Boolean which will store whether a player has won the game
    static int playerTurn;                  // Index of which player is going to make a move
    private int totalPlayers;               // Stores a number of total players
    static GameMap generatedMap;            // Stores the generated map which will be used by the team or the players
    static ArrayList<Team> teams;           // Array which stores a team of individuals or a number of players
    ArrayList<Integer> lostPlayers;         // Array which stores all the players who lost
    private Scanner scanner;                // Used for user input

    /**
     *  Game constructor which is responsible for determining the type of game is going to be played whether it is
     *  a solo game or a team game. Based on the type of game which is going to be played a method will be called
     *
     *  Method is used in the main method
     */
    Game()
    {
        scanner = new Scanner(System.in); // Initialising the scanner
        teams = new ArrayList<Team>(); // Initialising the teams array
        lostPlayers = new ArrayList<Integer>(); // Initialising a lost player team array

        int gameModeInput = 0;
        boolean validInput = false;

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
            case 1: {   soloMode();             break;  } // If solo game
            case 2: {   collaborationMode();    break;  } // If collaboration game
        }

        playerWon = false;
    }

    /**
     *  Method which is responsible for handling the solo game mode. Where the player inputs the amount of players
     *  which are going to be played and the map size and the type of game map which will be used whether it is a
     *  safe map or hazard map
     *
     *  Each player is stored in a different object of type Team
     *
     *  Used in Game constructor.
     */
    private void soloMode()
    {
        int mapSizeInput = 1;
        int amountOfPlayersInput = 0;
        int mapChoice = 0;

        boolean validInput = false;

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
        inputMapChoice(mapSizeInput, mapChoice);

        for (int i = 0; i < amountOfPlayersInput; i ++)
        {
            teams.add(new Team(1, generatedMap, new int[]{i}));
        }
    }

    /**
     *  Method is responsible for handling the collaboration game mode. Which would require the user to input that
     *  is the amount of players in the team and amount of teams and the type of map which will be used
     *
     *  Used in Game Constructor
     */
    private void collaborationMode()
    {
        int mapSizeInput = 1;
        int teamLimitInput = 0;
        int amountOfPlayersInput = 0;
        int mapChoice = 0;

        boolean validInput = false;

        while (!validInput)
        {
            try
            {
                System.out.println("Please enter the number of players");
                amountOfPlayersInput = scanner.nextInt();

                System.out.println("Please enter the number of teams");
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
        inputMapChoice(mapSizeInput, mapChoice);
        createTeams(amountOfPlayersInput, teamLimitInput);

        for (int i = 0; i < teams.size(); i++)
        {
            System.out.print("Team " + i + ":");

            for (int j = 0; j < teams.get(i).players.length; j++)
            {
                System.out.print(" " + teams.get(i).players[j].playerID + " ");
            }

            System.out.println();
        }
    }

    /**
     *  This method handles the type of map which will be used that are the safe map or the hazard map
     *
     *  Only 1 instance of the map can be created.
     *
     *  Used in collaborationMode() and soloMode()
     *
     * @param mapSizeInput the size of map which the user inputted
     * @param mapChoice stores the type of map which will be generated
     */
    private void inputMapChoice(int mapSizeInput, int mapChoice)
    {
        while (mapChoice != 1 && mapChoice != 2) // Keep looping while not a valid input
        {
            System.out.println("Please input 1 for Safe Map and 2 for Hazardous Map");
            mapChoice = scanner.nextInt();

            // The game map creator which is responsible for creating the game maps
            GameMapCreator gameMapCreator;

            if (mapChoice == 1) // If safe map
            {
                gameMapCreator = new GameMapCreatorSafeMap();
                gameMapCreator.generateGameMap(mapSizeInput, mapSizeInput);
                generatedMap = GameMap.getMapInstance();
            }
            else if (mapChoice == 2) // If hazard map
            {
                gameMapCreator = new GameMapCreatorHazardousMap();
                gameMapCreator.generateGameMap(mapSizeInput, mapSizeInput);
                generatedMap = GameMap.getMapInstance();
            }
            else // Output that it was not a correct input
            {
                System.out.println("Please enter a correct choice.");
            }
        }
    }

    /**
     *  Method is responsible for handling the creating of the team based on the amount of players and the team amount
     *
     *  Used in collaborationMode()
     *
     * @param playerAmount the amount of players which are going to be spread amount the teams
     * @param teamAmount the amount of teams which are going to be played in the game
     */
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

    /**
     *  Method which checks if there are enough players to be stored in teams
     *
     *  Used in createTeams()
     *
     * @param amountOfPlayer the amount of players which will play in the game
     * @param teamSize the amount of teams which will be used to spread the players
     * @return true if it is possible to split the players among the teams
     */
    private boolean checkValidateTeamToPlayerRatio(int amountOfPlayer, int teamSize)
    {
        return amountOfPlayer / teamSize != 0;
    }

    /**
     *  Method which creates a team by selecting at random a certain amount of player where the players are stored
     *  within a team object
     *
     *  Used in createTeams() and soloMode()
     *
     * @param teamSize the number of players which will be added to the team
     * @param unusedPlayers the array of players which have not be assigned to a team so far
     */
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

        teams.add(new Team(teamSize, generatedMap, teamPlayers));
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
     * Core method to play the game which checks if the players wins after input.
     *
     * Uses different methods in the class to verify a player's status in the game.
     *
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
            generateHTMLFiles();
            tryToMove();
            checkMovedTile();

            if((playerTurn += 1) >= totalPlayers)    {   playerTurn = 0; }
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

    /**
     *  Method which is responsible for obtaining the player which will be played in the game based on the player turn variable
     *
     *  Used in almost every function
     *
     * @return a player which is going to be played
     */
    static Player currentPlayer()
    {
        Player currentPlayer = null;

        for (Team team : teams)
        {
            for (Player player : team.players)
            {
                if (player.playerID == playerTurn)
                {
                    currentPlayer = player;
                    break;
                }
            }

            if (currentPlayer != null)  {   break;  }
        }

        return currentPlayer;
    }

    /**
     *  Method which is responsible for obtaining the team which has a player which is going to be played
     *
     *  Used in generateHTMLFile()
     *
     * @return a team which has a player who is going to be played
     */
    private static Team currentTeam()
    {
        Team currentTeam = null;

        for (Team team : teams)
        {
            for (Player player : team.players)
            {
                if (player.playerID == playerTurn)
                {
                    currentTeam = team;
                    break;
                }
            }

            if (currentTeam != null)  {   break;  }
        }

        return currentTeam;
    }

    /**
     * Checks the tile that a user moves into.
     * if the tile is water the user dies.
     * if the tile is treasure the user wins.
     *
     * Used in startGame()
     */
    void checkMovedTile()
    {
        Position lastPlayerPosition = currentPlayer().getLastPosition();

        switch (generatedMap.getTileType(new Position(lastPlayerPosition.getXCoordinate(), lastPlayerPosition.getYCoordinate())))
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
        catch (IOException exception) { exception.printStackTrace();}
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

                    int isCurrentPlayerTile = 0;

                    for (Player aPlayer : currentTeam().players)
                    {
                        if (aPlayer.isPositionInMovedList(new Position(j - 1, i - 1)))
                        {
                            isCurrentPlayerTile = (aPlayer == currentPlayer()) ? 1 : 2;
                        }
                    }

                    if (isCurrentPlayerTile == 1 || isCurrentPlayerTile == 2)
                    {
                        htmlString.append(generatedMap.tileToString(generatedMap.getTileType(new Position(j - 1, i - 1)))).append("\">");

                        if (isCurrentPlayerTile == 1 &&
                                currentPlayer().getMovedPositions().get(currentPlayer().getMovedPositions().size() - 1).getYCoordinate() == i - 1 &&
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
