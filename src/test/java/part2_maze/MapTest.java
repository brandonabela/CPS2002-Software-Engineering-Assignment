package part2_maze;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MapTest
{
    private Map map;

    @Before
    public void setup()
    {
        map = new Map();
    }

    @Test
    public void test_mapSize_Correct()
    {
        assertTrue(map.setMapSize(5, 5));
    }

    @Test
    public void test_mapSize_Incorrect()
    {
        assertFalse(map.setMapSize(4, 5));
    }

    @Test
    public void test_generate_map_size()
    {
        map.setMapSize(5,5);
        map.generate();
        TileType[][] game_map = map.getMapDetail();

        assertEquals(5,game_map.length);

        for (TileType[] aGame_map : game_map)
        {
            assertEquals(5, aGame_map.length);
        }
    }

    @Test
    public void test_getTileType_SingleTile_Correct()
    {
        map.setMapSize(5,5);
        map.generate();
        TileType tileType = map.getTileType(0,0);

        if(tileType == TileType.ERROR)
        {
            fail();
        }
    }

    @Test
    public void test_getTileType_SingleTileIncorrect()
    {
        map.setMapSize(5,5);
        map.generate();
        TileType tileType = map.getTileType(0,6);
        assertEquals(TileType.ERROR,tileType);
    }

    @Test
    public void test_getTileToString_Grass()
    {
        assertEquals("grassTile",map.tileToString(TileType.GRASS));
    }

    @Test
    public void test_getTileToString_Water()
    {
        assertEquals("waterTile", map.tileToString(TileType.WATER));
    }

    @Test
    public void test_getTileToString_Treasure()
    {
        assertEquals("treasureTile", map.tileToString(TileType.TREASURE));
    }

    @Test
    public void test_getTileToString_Error()
    {
        assertEquals("unknownTile", map.tileToString(TileType.ERROR));
    }

    @After
    public void cleanup()
    {
        map = null;
    }
}
