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

    private void generate()
    {
        mapDetail = new TileType[sizeOfMap][sizeOfMap];

        Random rand = new Random();
        mapStartPosition = new Position(rand.nextInt(sizeOfMap) + 1, rand.nextInt(sizeOfMap) + 1);

        mapDetail[mapStartPosition.getYCoordinate()][mapStartPosition.getXCoordinate()] = TileType.GRASS;
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
