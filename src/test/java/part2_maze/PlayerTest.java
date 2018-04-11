package part2_maze;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest
{
    private Map map;
    private Player player;

    @Before
    public void Setup()
    {
        map = new Map();
        map.setMapSize(10, 10);
        map.generate();

        player = new Player(2);
    }

    @Test
    public void testMoveUp()
    {
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('U', map);

        Assert.assertEquals(1, player.getMovedPositions().get(1).getYCoordinate());
    }

    @Test
    public void testMoveUpException()
    {
        player.setPlayerStartPosition(new Position(10, 10));

        Assert.assertEquals(true, player.move('U', map));
    }

    @Test
    public void testMoveDown()
    {
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('D', map);

        Assert.assertEquals(3, player.getMovedPositions().get(1).getYCoordinate());
    }

    @Test
    public void testMoveDownException()
    {
        player.setPlayerStartPosition(new Position(0, 0));

        Assert.assertEquals(true, player.move('D', map));
    }

    @Test
    public void testMoveLeft()
    {
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('L', map);

        Assert.assertEquals(1, player.getMovedPositions().get(1).getXCoordinate());
    }

    @Test
    public void testMoveLeftException()
    {
        player.setPlayerStartPosition(new Position(10, 10));

        Assert.assertEquals(true, player.move('L', map));
    }

    @Test
    public void testMoveRight()
    {
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('R', map);

        Assert.assertEquals(3, player.getMovedPositions().get(1).getXCoordinate());
    }


    @Test
    public void testMoveRightException()
    {
        player.setPlayerStartPosition(new Position(2, 2));

        Assert.assertEquals(true, player.move('R', map));
    }

    @Test
    public void testIsInMovedList()
    {


        Assert.assertTrue(player.isInMovedList(player.getMovedPositions().get(0)));
    }

    @Test
    public void testGetMovedPositionsSize()
    {
        Assert.assertEquals(1, player.getMovedPositions().size());
    }

    @Test
    public void testGetMoves()
    {
        player.move('U', map);

        Assert.assertEquals("Up", player.getMoves());
    }

    @Test
    public void testMoveDefault(){
       assertEquals(false,player.move(' ',map));
    }

    @Test
    public void testMoveUpIncorrect(){

        player.setPlayerStartPosition(new Position(1, 0));
        Assert.assertEquals(false, player.move('U', map));
    }


    @Test
    public void testMoveleftIncorrect(){
        player.setPlayerStartPosition(new Position(0, 1));
        Assert.assertEquals(false, player.move('L', map));
    }


    @Test
    public void testMoveRightIncorrect(){
        player.setPlayerStartPosition(new Position(10, 0));
        Assert.assertEquals(false, player.move('R', map));
    }

    @Test
    public void testMoveDownIncorrect(){
        player.setPlayerStartPosition(new Position(0, 10));
        Assert.assertEquals(false, player.move('D', map));
    }

    @Test
    public void testCheckMoveIncorrect(){
        player.move('U', map);

        assertFalse(player.isInMovedList(new Position(9,9)));
    }

    @After
    public void cleanup()
    {
        map = null;
        player = null;
    }
}
