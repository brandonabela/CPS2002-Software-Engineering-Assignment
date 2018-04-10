package part2_maze;

import java.util.Random;

enum TileType
{
    GRASS,
    ERROR
}

public class Map
{
    private int sizeOfMap;
    private TileType[][] mapDetail;
    private Position mapStartPosition;

    public boolean setMapSize(int xSize, int ySize)
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

        Random rand = new Random();
        mapStartPosition = new Position(rand.nextInt(sizeOfMap) + 1, rand.nextInt(sizeOfMap) + 1);

        for (int i = 0; i < mapDetail.length; i ++)
        {
            for (int j = 0; j < mapDetail[0].length; j ++)
            {
                mapDetail[i][j] = TileType.GRASS;
            }
        }
    }

    public TileType getTileType(int xCoordinate, int yCoordinate)
    {
        if (mapDetail.length > yCoordinate && mapDetail[0].length > xCoordinate)
        {
            return mapDetail[yCoordinate][yCoordinate];
        }
        else
        {
            return TileType.ERROR;
        }
    }

    public String tileToString(TileType tileType)
    {
        switch (tileType)
        {
            case GRASS :    return "grassTile";
            case ERROR :    return "unknownTile";
            default :       return "unknownTile";
        }
    }

    public TileType[][] getMapDetail()
    {
        return mapDetail;
    }
}