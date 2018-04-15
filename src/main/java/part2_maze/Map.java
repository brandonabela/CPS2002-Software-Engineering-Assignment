package part2_maze;

import java.util.Random;

/**
 * Class used to generate and maintain a map in the class Game.
 *
 *
 * Total Amount of Methods: 8
 * Total Amount of Variables: 2
 */
class Map
{
    private int sizeOfMap; // Stores the size of the map
    private TileType[][] mapDetail; // Stores the actual map

    /**
     * Checks map dimensions requested by the user.
     *
     * Used in Game.checkPlayerMap()
     *
     * @param xSize - size of rows
     * @param ySize - size of columns
     * @return true if parameters are equal / false if parameters are not equal
     */
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

    /**
     * Generates a map with 1 random treasure tile and the rest are grass and water tiles.
     *
     * Used in Game.checkPlayerMap()
     */
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

    /**
     * Checks the tile colour at a location in the map
     *
     * Used in Game.htmlString() and Game.CheckMovedTile()
     *
     * @param tilePosition the tile position to be obtained
     * @return the tile at the parameter's coordinates
     */
    TileType getTileType(Position tilePosition)
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

    // Get Method for Map Detail
    TileType[][] getMapDetail()
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
    boolean isTileNotUsed(Position positionToCheck)
    {
        return tileToString(mapDetail[positionToCheck.getXCoordinate()][positionToCheck.getYCoordinate()]).equals("grassTile");
    }

    /**
     * Changes the tile colour in the position given in the map.
     * Water or treasure then the player is relocated into a new starting position.
     *
     * Used in testing
     *
     * @param tileCoordinate position to change
     * @param tileType tile to add
     */
    void changeTileType(Position tileCoordinate, TileType tileType)
    {
        mapDetail[tileCoordinate.getXCoordinate()][tileCoordinate.getYCoordinate()] = tileType;
    }

    /**
     * Returns a string that represents the tile type
     *
     * Used in htmlString()
     *
     * @param tileType tile to convert
     * @return type of tile
     */
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
