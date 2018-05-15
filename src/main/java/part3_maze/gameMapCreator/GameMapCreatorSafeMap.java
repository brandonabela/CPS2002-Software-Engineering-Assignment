package part3_maze.gameMapCreator;

// Importing Libraries and Classes

import part3_maze.gameMaps.GameMap;
import part3_maze.gameMaps.SafeMap;

public class GameMapCreatorSafeMap implements GameMapCreator
{
    /**
     *  Method which si responsible for using the safe map to generate the safe map
     *
     * @param xSize the width of the map
     * @param ySize the height of the map
     */
    public void generateGameMap(int xSize, int ySize)
    {
        GameMap safeMap = new SafeMap();
        safeMap.setMapSize(xSize, ySize);
        safeMap.generate();
    }
}
