package part2_maze;

class PlayerMapRatioException extends Exception
{
    public PlayerMapRatioException()
    {
        System.out.println("Incorrect number of players to match map dimensions");
    }

}
