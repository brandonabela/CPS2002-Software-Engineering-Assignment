package part3_maze;

import org.junit.*;
import org.junit.rules.ExpectedException;
import part3_maze.exception.PlayerMapRatioException;
import part3_maze.gameMaps.GameMap;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class GameTest
{
    private Game game;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setup()
    {
        String data = "1\n2\n5\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();
    }

    @Test
    public void test_GameConstructor_PlayerSet()
    {
        assertEquals(2, Game.teams.size());
    }

    @Test
    public void test_GameConstructor_MapSet()
    {
        assertEquals(20, Game.generatedMap.getMapDetail().length);
    }

    @Test
    public void test_checkPlayerMap_PlayerSetException() throws PlayerMapRatioException
    {
        exception.expect(PlayerMapRatioException.class);
        game.checkPlayerMap(9,5);
    }

    @Test
    public void test_GameConstructor_ExceptionCatch()
    {
        String data = "1\n10\n5\n2\n5\n2";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        game = new Game();
    }

    @Test
    public void test_checkPlayMap_MapSetException() throws PlayerMapRatioException
    {
        exception.expect(PlayerMapRatioException.class);
        game.checkPlayerMap(5,80);
    }

    @Test
    public void test_checkPlayerMap_PlayerMapRatioException()
    {
        try
        {
            game.checkPlayerMap(5,80);
        }
        catch (PlayerMapRatioException playerMapRatioException)
        {
            Assert.assertEquals("The map size of 80 x 80 is not allowed for 5 players", playerMapRatioException.getMessage());
        }
    }

    @Test
    public void test__checkPlayerMap_MoreThanFivePlayersAmount()
    {
        String data = "1\n8\n20\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        game = new Game();
        assertEquals(8, Game.teams.size());
    }

    @Test
    public void test__mapTypeIncorrect()
    {
        String data = "1\n2\n5\n0\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        game = new Game();
    }

    @Test
    public void test_checkPlayerMap_MoreThanFivePlayersMap()
    {
        String data = "1\n8\n20\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        game = new Game();

        assertEquals(20, Game.generatedMap.getMapDetail().length);
    }

    @Test
    public void test_HTMLString()
    {
        assertNotEquals("", Game.htmlString());
    }

    @Test
    public void test_generateHTMLFiles_OpenFile()
    {
        Game.generateHTMLFiles();
    }

    @Test
    public void test_isPlayerDead_PlayerDead()
    {
        game.lostPlayers.add(1);
        assertTrue(game.isPlayerDead(1));
    }

    @Test
    public void test_switchToAlivePlayer_CurrentPlayerAfterPlayerDies()
    {
        Game.playerTurn = 1;
        game.lostPlayers.add(1);
        game.switchToAlivePlayer();
        assertEquals(0, Game.playerTurn);
    }

   @Test
   public void test_isPlayerDead_PlayerNotDead()
   {
       assertFalse(game.isPlayerDead(1));
   }

   @Test
   public void test_allPlayersAreDead_AllDeadPlayer()
   {
       game.lostPlayers.add(0);
       game.lostPlayers.add(1);
       assertTrue(game.allPlayersAreDead());
   }

    @Test
    public void test_allPlayersAreDead_OneDeadPlayer()
    {
        game.lostPlayers.add(1);
        assertFalse(game.allPlayersAreDead());
    }

    @Test
    public void test_switchToAlivePlayer_PlayerSwitch()
    {
        game.lostPlayers.add(1);
        game.switchToAlivePlayer();
        assertEquals(0,Game.playerTurn);
    }

    @Test
    public void Test_tryToMove_CorrectInputForMove()
    {
        Game.playerTurn = 0;

        if (Game.currentPlayer() != null)
        {
            Game.currentPlayer().setPlayerStartPosition(new Position(0,0));

            String data = "R";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            game.tryToMove();
            assertEquals(0,Game.currentPlayer().getLastPosition().getYCoordinate());
        }
    }

    @Test
    public void test_tryToMove_IncorrectMoveThenCorrect()
    {
        Game.playerTurn = 0;

        if (Game.currentPlayer() != null)
        {
            Game.currentPlayer().setPlayerStartPosition(new Position(0,0));

            String data = "U\nR";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            game.tryToMove();
            assertEquals(1,Game.currentPlayer().getLastPosition().getXCoordinate());
        }
    }

    @Test
    public void test_CheckMovedTile_PlayerMovingIntoWaterTile()
    {
        Game.playerTurn = 0;

        if (Game.currentPlayer() != null)
        {
            Game.currentPlayer().setPlayerStartPosition(new Position(0,0));

            String data = "R";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Game.generatedMap.changeTileType(new Position(0, 1), TileType.WATER);
            game.tryToMove();
            game.CheckMovedTile();
            assertTrue(game.isPlayerDead(0));
        }
    }

    @Test
    public void test_CheckMovedTile_PlayerMovingIntoTreasure()
    {
        Game.playerTurn = 0;

        if (Game.currentPlayer() != null)
        {
            Game.currentPlayer().setPlayerStartPosition(new Position(0,0));

            String data = "R";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Game.generatedMap.changeTileType(new Position(0, 1), TileType.TREASURE);
            game.tryToMove();
            game.CheckMovedTile();
            assertTrue(game.playerWon);
        }
    }

    @Test
    public void test_startGame_StartGameWin()
    {
        game.playerWon = true;
        game.startGame();
    }

    @Test
    public void test_startGame_AllDeadPlayers()
    {
        game.lostPlayers.add(0);
        game.lostPlayers.add(1);
        game.startGame();
        assertFalse(game.playerWon);
    }

    @Test
    public void test_StartGame_PlayerWins()
    {
        game.lostPlayers.add(1);

        if (Game.currentPlayer() != null)
        {
            Game.currentPlayer().setPlayerStartPosition(new Position(0,0));
            String data = "R";
            System.setIn(new ByteArrayInputStream(data.getBytes()));
            Game.generatedMap.changeTileType(new Position(0, 1), TileType.TREASURE);
            game.startGame();
            assertTrue(game.playerWon);
        }
    }

    @Test
    public void test_collaborativeMode_correct()
    {
        String data = "2\n6\n4\n10\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        Game game = new Game();
        assertEquals(4, Game.teams.size());
    }

    @Test
    public void test_collaborativeMode_IncorrectPlayerCount()
    {
        String data = "2\n19\n4\n10\n4\n4\n5\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        Game game = new Game();
        assertEquals(4, Game.teams.size());
    }

    @Test
    public void test_collaborativeMode_IncorrectPlayerTeamRatio()
    {
        String data = "2\n2\n6\n5\n6\n4\n10\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        GameMap.reset();
        Game game = new Game();
        assertEquals(4, Game.teams.size());
    }

    @Test
    public void test_collaborativeMode_EvenTeams()
    {
        String data = "2\n8\n2\n12\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(2, Game.teams.size());
    }

    @Test
    public void test_GameConstructor_WrongInput()
    {
        String data = "3\n2\n6\n3\n12\n1";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        assertEquals(1, Game.teams.get(0).players.length);
    }

    @Test
    public void test_currentPlayerNull()
    {
        Game.teams = new ArrayList<Team>();
        assertNull(Game.currentPlayer());
    }

    @After
    public void cleanup()
    {
        game = null;
    }
}
