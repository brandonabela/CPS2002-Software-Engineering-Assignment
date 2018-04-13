package part2_maze;

class PlayerMapRatioException extends Exception
{
    private String message;

    PlayerMapRatioException(int amountOfPlayers, int mapSize)
    {
        message = "The map size of " + mapSize + " x " + mapSize + " is not allowed for " + amountOfPlayers + " players";
    }

    String getExceptionMessage()
    {
        return message;
    }
}
