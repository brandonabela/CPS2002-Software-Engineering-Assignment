package part2_maze;

import java.util.Random;

class Map
{
    private int sizeOfMap;
    private TileType[][] mapDetail;

    boolean setMapSize(int xSize, int ySize)
    {
        if (xSize == ySize)
        {
            this.sizeOfMap = xSize;
            return true;
        }
        else
        {
            return false;
        }
    }

    void generate()
    {
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

    TileType getTileType(int xCoordinate, int yCoordinate)
    {
        if (xCoordinate < mapDetail[0].length && yCoordinate < mapDetail.length)
        {
            return mapDetail[yCoordinate][xCoordinate];
        }
        else
        {
            return TileType.ERROR;
        }
    }

    boolean isTileNotUsed(Position positionToCheck)
    {
        return tileToString(mapDetail[positionToCheck.getXCoordinate()][positionToCheck.getYCoordinate()]).equals("grassTile");
    }

    TileType[][] getMapDetail()
    {
        return mapDetail;
    }

    void changeTileType(Position tileCoordinate, TileType tileType)
    {
        mapDetail[tileCoordinate.getXCoordinate()][tileCoordinate.getYCoordinate()] = tileType;
    }

    String tileToString(TileType tileType)
    {
        switch (tileType)
        {
            case GRASS      :   return "grassTile";
            case WATER      :   return "waterTile";
            case TREASURE   :   return "treasureTile";
            default         :   return "unknownTile";
        }
    }
}

