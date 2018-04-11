package part2_maze;

import org.junit.Assert;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

public class PositionTest
{
    private Position position;

    @Before
    public void Setup()
    {
        position = new Position(8, 10);
    }

    @Test
    public void testGetXCoordinate()
    {
        Assert.assertEquals(8, position.getXCoordinate());
    }

    @Test
    public void testGetYCoordinate()
    {
        Assert.assertEquals(10, position.getYCoordinate());
    }

    @Test
    public void testSetXCoordinate()
    {
        position.setXCoordinate(15);

        Assert.assertEquals(15, position.getXCoordinate());
    }

    @Test
    public void testSetYCoordinate()
    {
        position.setYCoordinate(3);

        Assert.assertEquals(3, position.getYCoordinate());
    }

    @Test
    public void testToString()
    {
        Assert.assertEquals("(xCoordinate=8, yCoordinate=10)", position.toString());
    }

    @After
    public void cleanup()
    {
        position = null;
    }
}
