package part3_maze;

import part3_maze.gameMaps.HazardousMap;
import part3_maze.gameMaps.SafeMap;

class GameMapCreator
{
    enum MapType
    {
        MAP_SAFE,
        MAP_HAZARDOUS
    }

    void generateGameMap(MapType mapType, int xSize, int ySize)
    {
        switch (mapType)
        {
            case MAP_SAFE :
            {
                GameMap safeMap = new SafeMap();
                safeMap.setMapSize(xSize, ySize);
                safeMap.generate();

            }

            case MAP_HAZARDOUS:
            {
                GameMap hazardousMap = new HazardousMap();
                hazardousMap.setMapSize(xSize, ySize);
                hazardousMap.generate();

            }
        }

    }
}
