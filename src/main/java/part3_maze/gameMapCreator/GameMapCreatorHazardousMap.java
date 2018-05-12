package part3_maze.gameMapCreator;

import part3_maze.gameMaps.GameMap;
import part3_maze.gameMaps.HazardousMap;

public class GameMapCreatorHazardousMap implements GameMapCreator
{
    public void generateGameMap(int xSize, int ySize)
    {
        GameMap hazardousMap = new HazardousMap();
        hazardousMap.setMapSize(xSize, ySize);
        hazardousMap.generate();
    }
}
