package part3_maze.gameMaps;

import part3_maze.GameMap;
import part3_maze.Position;
import part3_maze.TileType;

import java.util.Random;

public class SafeMap extends GameMap
{
    public SafeMap()    {   }

    protected void generate()
    {
        gameMapName = "Safe Map";
        mapInstance = new SafeMap();

        mapDetail = new TileType[sizeOfMap][sizeOfMap];

        for (int i = 0; i < mapDetail.length; i ++)
        {
            for (int j = 0; j < mapDetail[0].length; j ++)
            {
                mapDetail[i][j] = TileType.GRASS;
            }
        }

        Random random = new Random();
        Position randomPosition = new Position(0, 0);

        while (randomPosition.getXCoordinate() < mapDetail[0].length && randomPosition.getYCoordinate() < mapDetail.length)
        {
            randomPosition.setXCoordinate(randomPosition.getXCoordinate() + random.nextInt(3));
            randomPosition.setYCoordinate(randomPosition.getYCoordinate() + random.nextInt(3));

            if (randomPosition.getXCoordinate() < mapDetail[0].length - 1 && randomPosition.getYCoordinate() < mapDetail.length - 1)
            {
                mapDetail[randomPosition.getXCoordinate()][randomPosition.getYCoordinate()] = TileType.WATER;
            }
        }

        mapDetail[random.nextInt(sizeOfMap)][random.nextInt(sizeOfMap)] = TileType.TREASURE;
    }
}
