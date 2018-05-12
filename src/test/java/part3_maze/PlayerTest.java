package part3_maze;

import org.junit.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import part3_maze.gameMapCreator.GameMapCreator;
import part3_maze.gameMapCreator.GameMapCreatorHazardousMap;
import part3_maze.gameMaps.GameMap;

import static org.junit.Assert.*;

public class PlayerTest
{
    private static GameMap generatedMap;
    private GameMapCreator gameMapCreator;
    private Player player;

    @Before
    public void Setup()
    {
        gameMapCreator = new GameMapCreatorHazardousMap();

        player = new Player(2,1);
    }

    @Test
    public void testMoveUp()
    {
        gameMapCreator.generateGameMap(2,2);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('U', generatedMap);

        Assert.assertEquals(1, player.getMovedPositions().get(1).getYCoordinate());
    }

    @Test
    public void testMoveUpException()
    {
        gameMapCreator.generateGameMap(2,2);
        generatedMap = GameMap.getMapInstance();

        player.setPlayerStartPosition(new Position(10, 10));

        Assert.assertTrue(player.move('U', generatedMap));
    }

    @Test
    public void testMoveDown()
    {
        gameMapCreator.generateGameMap(2,2);
        generatedMap = GameMap.getMapInstance();

        player.setPlayerStartPosition(new Position(2, 2));
        player.move('D', generatedMap);

        Assert.assertEquals(3, player.getMovedPositions().get(1).getYCoordinate());
    }

    @Test
    public void testMoveDownException()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();

        player.setPlayerStartPosition(new Position(0, 0));

        Assert.assertTrue(player.move('D', generatedMap));
    }

    @Test
    public void testMoveLeft()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();

        player.setPlayerStartPosition(new Position(2, 2));
        player.move('L', generatedMap);

        Assert.assertEquals(1, player.getMovedPositions().get(1).getXCoordinate());
    }

    @Test
    public void testMoveLeftException()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(10, 10));

        Assert.assertTrue(player.move('L', generatedMap));
    }

    @Test
    public void testMoveRight()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(2, 2));
        player.move('R', generatedMap);

        Assert.assertEquals(3, player.getMovedPositions().get(1).getXCoordinate());
    }

    @Test
    public void testMoveRightException()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(2, 2));

        Assert.assertTrue(player.move('R', generatedMap));
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
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.move('U', generatedMap);

        Assert.assertEquals("Up", player.getMoveDirections());
    }

    @Test
    public void testMoveDefault()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        assertFalse(player.move(' ', generatedMap));
    }

    @Test
    public void testMoveUpIncorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(1, 0));
        Assert.assertFalse(player.move('U', generatedMap));
    }

    @Test
    public void testMoveLeftIncorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(0, 1));
        Assert.assertFalse(player.move('L', generatedMap));
    }

    @Test
    public void testMoveRightIncorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(10, 0));
        Assert.assertFalse(player.move('R', generatedMap));
    }

    @Test
    public void testMoveDownIncorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(0, 10));
        Assert.assertFalse(player.move('D', generatedMap));
    }

    @Test
    public void testCheckMoveIncorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.move('U', generatedMap);

        assertFalse(player.isInMovedList(new Position(9,9)));
    }

    @Test
    public void testLastPlayerX()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(1, 0));
        assertEquals(1, player.getLastPosition().getXCoordinate());
    }

    @Test
    public void testLastPlayerY()
    {
        gameMapCreator.generateGameMap(5,5);
        generatedMap = GameMap.getMapInstance();
        player.setPlayerStartPosition(new Position(1, 0));
        assertEquals(0, player.getLastPosition().getYCoordinate());
    }

    @After
    public void cleanup()
    {
        generatedMap = null;
        player = null;
    }
}
