package part3_maze;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class GameMapTest
{
    private static GameMap gameMap;

    @Before
    public void setup()
    {
        gameMap = GameMap.getMapInstance();
    }

    @Test
    public void test_mapSize_Correct()
    {
        assertTrue(gameMap.setMapSize(5, 5));
    }

    @Test
    public void test_mapSize_Incorrect()
    {
        assertFalse(gameMap.setMapSize(4, 5));
    }

    @Test
    public void test_generate_map_size()
    {
        gameMap.setMapSize(5,5);
        gameMap.generate();
        TileType[][] game_map = gameMap.getMapDetail();

        assertEquals(5,game_map.length);

        for (TileType[] aGame_map : game_map)
        {
            assertEquals(5, aGame_map.length);
        }
    }

    @Test
    public void test_getTileType_SingleTile_Correct()
    {
        gameMap.setMapSize(5,5);
        gameMap.generate();
        TileType tileType = gameMap.getTileType(new Position(0,0));

        if(tileType == TileType.ERROR)
        {
            fail();
        }
    }

    @Test
    public void test_getTileType_SingleTileIncorrect()
    {
        gameMap.setMapSize(5,5);
        gameMap.generate();
        TileType tileType = gameMap.getTileType(new Position(0,6));
        assertEquals(TileType.ERROR,tileType);
    }

    @Test
    public void test_getTileToString_Grass()
    {
        assertEquals("grassTile", gameMap.tileToString(TileType.GRASS));
    }

    @Test
    public void test_getTileToString_Water()
    {
        assertEquals("waterTile", gameMap.tileToString(TileType.WATER));
    }

    @Test
    public void test_getTileToString_Treasure()
    {
        assertEquals("treasureTile", gameMap.tileToString(TileType.TREASURE));
    }

    @Test
    public void test_getTileToString_Error()
    {
        assertEquals("unknownTile", gameMap.tileToString(TileType.ERROR));
    }

    @After
    public void cleanup()
    {
        gameMap = null;
    }
}
