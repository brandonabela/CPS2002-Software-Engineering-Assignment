package part2_maze;

/**
 * Class which is responsible for defining the positions within the game
 */
public class Position
{
    private int xCoordinate;    // The x value moving left or right
    private int yCoordinate;    // The y value moving up or down

    /**
     * Constructor which defines the position object
     *
     * @param xCoordinate the x coordinate to be assigned
     * @param yCoordinate the y coordinate to be assigned
     */
    Position(int xCoordinate, int yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    // Get Methods

    int getXCoordinate()
    {
        return xCoordinate;
    }

    int getYCoordinate()
    {
        return yCoordinate;
    }

    // Set Methods

    void setXCoordinate(int xCoordinate)
    {
        this.xCoordinate = xCoordinate;
    }

    void setYCoordinate(int yCoordinate)
    {
        this.yCoordinate = yCoordinate;
    }

    /**
     * Method which is responsible for overriding the default behaviour of the toString method
     *
     * @return a string based on the given return string format
     */
    @Override
    public String toString()
    {
        return "(xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + ")";
    }
}
