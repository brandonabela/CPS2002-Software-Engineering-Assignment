package part2_maze;

import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;

import static org.junit.Assert.*;

public class GameTest
{
    private Game game;

    @Before
    public void setup()
    {
        String data = "2\n5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));

        game = new Game();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testPlayerSet()
    {
        assertEquals(2, Game.players.length);
    }

    @Test
    public void testMapSet()
    {
        assertEquals(5, game.mapSizeInput);
    }

    @Test
    public void testPlayerSetIncorrect() throws PlayerMapRatioException
    {
        exception.expect(PlayerMapRatioException.class);
        game.checkPlayerMap(9,5);
    }

    @Test
    public void testMapSetIncorrect() throws PlayerMapRatioException
    {
        exception.expect(PlayerMapRatioException.class);
        game.checkPlayerMap(5,80);
    }

    @Test
    public void testPlayerMapRatioException()
    {
        try
        {
            game.checkPlayerMap(5,80);
        }
        catch (PlayerMapRatioException playerMapRatioException)
        {
            Assert.assertEquals("The map size of 80 x 80 is not allowed for 5 players", playerMapRatioException.getExceptionMessage());
        }
    }

    @Test
    public void testIncorrectPlayerCount()
    {
        assertFalse(game.setNumberOfPlayers(10));
    }

    @Test
    public void testCorrectPlayerCount()
    {
        assertTrue(game.setNumberOfPlayers(2));
    }

    @Test
    public void testMoreThanFivePlayersAmount()
    {
        String data = "8\n20";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();

        assertEquals(8,game.amountOfPlayersInput);
    }

    @Test
    public void testMoreThanFivePlayersMap()
    {
        String data = "8\n20";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();

        assertEquals(20, game.mapSizeInput);
    }

    @Test
    public void testHTMLString()
    {
        assertNotEquals("", Game.htmlString());
    }

    @Test
    public void testGenerateHTMLOpenFile()
    {
        Game.generateHTMLFiles();
    }

    @Test
    public void testPlayerDead()
    {
        game.lostPlayers.add(1);
        assertTrue(game.isPlayerDead(1));
    }

    @Test
    public void testPlayerNotDead()
    {
       assertFalse(game.isPlayerDead(1));
    }

    @After
    public void cleanup()
    {
        game = null;
    }
}
