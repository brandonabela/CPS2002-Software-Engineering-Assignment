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

    private void generate()
    {
    }

    public TileType getTileType(int xCoordinate, int yCoordinate)
    {
        if (mapDetail.length >= yCoordinate && mapDetail[0].length >= xCoordinate)
        {
            return mapDetail[yCoordinate][yCoordinate];
        }
        else
        {
            return TileType.ERROR;
        }
    }
}
