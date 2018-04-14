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
    public void mapSize_Correct_test()
    {
        assertTrue(map.setMapSize(5, 5));
    }

    @Test
    public void mapSize_Incorrect_test()
    {
        assertFalse(map.setMapSize(4, 5));
    }

    @Test
    public void generate_map_size_test()
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
    public void getTileType_SingleTile_Correct_test()
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
    public void getTileType_SingleTile_Incorrect_test()
    {
        map.setMapSize(5,5);
        map.generate();
        TileType tileType = map.getTileType(0,6);
        assertEquals(TileType.ERROR,tileType);
    }

    @Test
    public void getTileToString_Grass_test()
    {
        assertEquals("grassTile",map.tileToString(TileType.GRASS));
    }

    @Test
    public void getTileToString_Water_test()
    {
        assertEquals("waterTile", map.tileToString(TileType.WATER));
    }

    @Test
    public void getTileToString_Treasure_test()
    {
        assertEquals("treasureTile", map.tileToString(TileType.TREASURE));
    }

    @Test
    public void getTileToString_Error_test()
    {
        assertEquals("unknownTile", map.tileToString(TileType.ERROR));
    }

    @After
    public void cleanup()
    {
        map = null;
    }
}
