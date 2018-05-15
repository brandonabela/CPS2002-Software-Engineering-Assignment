package part3_maze.gameMapCreator;

public interface GameMapCreator
{
    /**
     *  Method which will be responsible for generating a game map
     *
     * @param xSize the width of the map
     * @param ySize the height of the map
     */
    void generateGameMap(int xSize, int ySize);
}
