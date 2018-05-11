package part3_maze.gameMaps;

import part3_maze.Position;
import part3_maze.TileType;

import java.util.Random;

public abstract class GameMap
{
    protected String gameMapName = null; // Stores the name of the map

    public int sizeOfMap; // Stores the size of the map
    private TileType[][] mapDetail; // Stores the actual map
    protected static GameMap mapInstance; // Stores a single instance of the actual map

    protected GameMap()
    {
        this.gameMapName = "Game Map";
    }

    public abstract void generate();

    /**
     * Changes the tile colour in the position given in the map.
     * Water or treasure then the player is relocated into a new starting position.
     *
     * Used in testing
     *
     * @param tileCoordinate position to change
     * @param tileType tile to add
     */
    public void changeTileType(Position tileCoordinate, TileType tileType)
    {
        mapDetail[tileCoordinate.getXCoordinate()][tileCoordinate.getYCoordinate()] = tileType;
    }

    // Get Method for Map Detail
    public static GameMap getMapInstance()
    {
        return mapInstance;
    }

    // Reset Static Instance needed in testing
    public static void reset()
    {
        mapInstance = null;
    }

    public TileType[][] getMapDetail()
    {
        return mapDetail;
    }

    /**
     * Checks tile type at a players starting location if a tile is water or treasure then the
     * player is relocated into a new starting position.
     *
     * @param positionToCheck is the position to be check
     * @return true if not used / false if used
     */
    public boolean isTileNotUsed(Position positionToCheck)
    {
        return tileToString(mapDetail[positionToCheck.getXCoordinate()][positionToCheck.getYCoordinate()]).equals("grassTile");
    }

    /**
     * Checks the tile colour at a location in the map
     *
     * Used in Game.htmlString() and Game.CheckMovedTile()
     *
     * @param tilePosition the tile position to be obtained
     * @return the tile at the parameter's coordinates
     */
    public TileType getTileType(Position tilePosition)
    {
        if (tilePosition.getXCoordinate() < mapDetail[0].length && tilePosition.getYCoordinate() < mapDetail.length)
        {
            return mapDetail[tilePosition.getYCoordinate()][tilePosition.getXCoordinate()];
        }
        else
        {
            return TileType.ERROR;
        }
    }

    /**
     * To cater for future map types since the difference will only be the amount of water tiles
     * had to change the way creation works to have the required tile amount
     *
     * @param maxWaterTiles the maximum amount of water tiles
     */
    protected void createMap(int maxWaterTiles)
    {
        mapDetail = new TileType[sizeOfMap][sizeOfMap];
        Random random = new Random();

        for (int i = 0; i < mapDetail.length; i ++)
        {
            for (int j = 0; j < mapDetail[0].length; j ++)
            {
                mapDetail[i][j] = TileType.GRASS;
            }
        }

        Position randomPosition = new Position(0, 0);
        int waterCount = 0;
        int total = 0; //total tiles covered
        randomPosition.setXCoordinate(random.nextInt(sizeOfMap));
        randomPosition.setYCoordinate(random.nextInt(sizeOfMap));

        while (total < (sizeOfMap*sizeOfMap))
        {
            while (mapDetail[randomPosition.getXCoordinate()][randomPosition.getYCoordinate()] == TileType.WATER)
            {
                randomPosition.setXCoordinate(random.nextInt(sizeOfMap));
                randomPosition.setYCoordinate(random.nextInt(sizeOfMap));
            }

            if (waterCount <= maxWaterTiles)
            {
                mapDetail[randomPosition.getXCoordinate()][randomPosition.getYCoordinate()] = TileType.WATER;
                waterCount+=1;
            }
            else
            {
                break;
            }

            total += 1;
        }

        int random_x = random.nextInt(sizeOfMap);
        int random_y = random.nextInt(sizeOfMap);

        while(mapDetail[random_x][random_y] == TileType.WATER)
        {
            random_x = random.nextInt(sizeOfMap);
            random_y = random.nextInt(sizeOfMap);
        }

        mapDetail[random_x][random_y] = TileType.TREASURE;
    }

    /**
     * Checks map dimensions requested by the user.
     *
     * Used in Game.checkPlayerMap()
     *
     * @param xSize - size of rows
     * @param ySize - size of columns
     * @return true if parameters are equal / false if parameters are not equal
     */
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

    /**
     * Returns a string that represents the tile type
     *
     * Used in htmlString()
     *
     * @param tileType tile to convert
     * @return type of tile
     */
    public String tileToString(TileType tileType)
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
