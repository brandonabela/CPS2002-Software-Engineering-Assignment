package part2_maze;

class PlayerMapRatioException extends Exception
{
    PlayerMapRatioException(int amountOfPlayers, int mapSize)
    {
        super("The map size of " + mapSize + " x " + mapSize + " is not allowed for " + amountOfPlayers + " players");
    }
}
