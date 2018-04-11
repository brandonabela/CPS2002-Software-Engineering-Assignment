package part2_maze;

public class Position
{
    private int xCoordinate;
    private int yCoordinate;

    Position(int xCoordinate, int yCoordinate)
    {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    int getXCoordinate()
    {
        return xCoordinate;
    }

    int getYCoordinate()
    {
        return yCoordinate;
    }

    void setXCoordinate(int xCoordinate)
    {
        this.xCoordinate = xCoordinate;
    }

    void setYCoordinate(int yCoordinate)
    {
        this.yCoordinate = yCoordinate;
    }

    @Override
    public String toString()
    {
        return "(xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + ")";
    }
}
