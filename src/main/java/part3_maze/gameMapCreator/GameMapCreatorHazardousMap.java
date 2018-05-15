package part3_maze.gameMapCreator;

// Importing Libraries and Classes

import part3_maze.gameMaps.GameMap;
import part3_maze.gameMaps.HazardousMap;

public class GameMapCreatorHazardousMap implements GameMapCreator
{
    /**
     *  Method which is responsible for used the hazardous map to generate the hazardous map
     *
     * @param xSize the width of the map
     * @param ySize the height of the map
     */
    public void generateGameMap(int xSize, int ySize)
    {
        GameMap hazardousMap = new HazardousMap();
        hazardousMap.setMapSize(xSize, ySize);
        hazardousMap.generate();
    }
}
