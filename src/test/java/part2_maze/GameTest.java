package part2_maze;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;

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
        assertEquals(2,game.amountOfPlayers);
    }
    @Test
    public void testMapSet()
    {
        assertEquals(5,game.mapSize);
    }



    @Test
    public void testPlayerSetIncorrect() throws PlayerToMapRatioException {
        exception.expect(PlayerToMapRatioException.class);
        game.CheckPlayersAndMap(9,5);
    }

    @Test
    public void testMapSetIncorrect() throws PlayerToMapRatioException {
        exception.expect(PlayerToMapRatioException.class);
        game.CheckPlayersAndMap(5,80);
    }

    @Test
    public void testIncorrectPlayerCount(){
        assertEquals(false,game.setNumberOfPlayers(10));
    }

    @Test
    public void testCorrectPlayerCount(){
        assertEquals(true,game.setNumberOfPlayers(2));
    }

    @Test
    public void testMoreThanFivePlayersC(){
        String data = "8\n20";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();
        assertEquals(8,game.amountOfPlayers);
    }

    @Test
    public void testMoreThanFivePlayersM(){
        String data = "8\n20";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();
        assertEquals(20,game.mapSize);
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
    public void testGenerateHTMLOpenFileName()
    {
        Game.generateHTMLFiles();
        assertNotEquals("", Game.current_file_name);
    }

    @Test
    public void testPlayerDead(){
        game.lostPlayers.add(1);
        assertEquals(true,game.isPlayerDead(1));
    }

   @Test
   public void testPlayerNotDead(){
       assertEquals(false,game.isPlayerDead(1));
   }

    @After
    public void cleanup()
    {
        game = null;
    }
}
