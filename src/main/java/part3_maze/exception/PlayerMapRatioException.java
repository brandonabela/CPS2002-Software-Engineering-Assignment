package part3_maze.exception;

/**
 * A class which is responsible for defining a custom exception to be called every time the player inputs an
 * incorrect ratio between the player and the map
 */
public class PlayerMapRatioException extends Exception
{
    /**
     * Constructor which is responsible for storing an exception which is throw every time the player
     * inputs an incorrect ratio between the player and the map
     *
     * @param amountOfPlayers the amount of players to be played
     * @param mapSize the size of the map
     */
    public PlayerMapRatioException(int amountOfPlayers, int mapSize)
    {
        super("The map size of " + mapSize + " x " + mapSize + " is not allowed for " + amountOfPlayers + " players");
    }
}
