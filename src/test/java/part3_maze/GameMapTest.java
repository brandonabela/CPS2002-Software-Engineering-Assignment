package part3_maze;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import part3_maze.gameMapCreator.GameMapCreator;
import part3_maze.gameMapCreator.GameMapCreatorHazardousMap;
import part3_maze.gameMapCreator.GameMapCreatorSafeMap;
import part3_maze.gameMaps.GameMap;

import static org.junit.Assert.*;

public class GameMapTest
{
    private static GameMap gameMap;

    private GameMapCreator gameMapCreator;
    @Before
    public void setup()
    {
        gameMapCreator = new GameMapCreatorSafeMap();
    }

    @Test
    public void test_Singleton_UsingMapSize(){
        gameMapCreator.generateGameMap(5,5);
        gameMapCreator.generateGameMap(2,2);

        assertEquals(5,GameMap.getMapInstance().sizeOfMap);
    }

    @Test
    public void test_mapSize_Correct()
    {
        gameMapCreator.generateGameMap(5,5);
        assertTrue(GameMap.getMapInstance().setMapSize(5, 5));
    }

    @Test
    public void test_mapSize_CorrectHazard(){
        GameMap.reset();
        gameMapCreator.generateGameMap(5,5);
        assertTrue(GameMap.getMapInstance().setMapSize(5, 5));
    }

    @Test
    public void test_mapSize_Incorrect()
    {
        gameMapCreator.generateGameMap(5,5);
        assertFalse(GameMap.getMapInstance().setMapSize(4, 5));
    }

    @Test
    public void test_generate_map_size()
    {
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
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
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        gameMap.setMapSize(5,5);
        gameMap.generate();
        TileType tileType = gameMap.getTileType(new Position(0,0));

        if(tileType == TileType.ERROR)  {   fail(); }
    }

    @Test
    public void test_getTileType_SingleTileIncorrect()
    {
        GameMap.reset();
        gameMapCreator = new GameMapCreatorHazardousMap();
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        gameMap.setMapSize(5,5);
        gameMap.generate();
        TileType tileType = gameMap.getTileType(new Position(0,6));
        assertEquals(TileType.ERROR,tileType);
    }

    @Test
    public void test_getTileToString_Grass()
    {
        GameMap.reset();
        gameMapCreator = new GameMapCreatorHazardousMap();
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        assertEquals("grassTile", gameMap.tileToString(TileType.GRASS));
    }

    @Test
    public void test_getTileToString_Water()
    {
        GameMap.reset();
        gameMapCreator = new GameMapCreatorHazardousMap();
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        assertEquals("waterTile", gameMap.tileToString(TileType.WATER));
    }

    @Test
    public void test_getTileToString_Treasure()
    {
        GameMap.reset();
        gameMapCreator = new GameMapCreatorHazardousMap();
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        assertEquals("treasureTile", gameMap.tileToString(TileType.TREASURE));
    }

    @Test
    public void test_getTileToString_Error()
    {
        GameMap.reset();
        gameMapCreator = new GameMapCreatorHazardousMap();
        gameMapCreator.generateGameMap(5,5);
        gameMap = GameMap.getMapInstance();
        assertEquals("unknownTile", gameMap.tileToString(TileType.ERROR));
    }

    @After
    public void cleanup()
    {
        gameMap = null;
    }
}
