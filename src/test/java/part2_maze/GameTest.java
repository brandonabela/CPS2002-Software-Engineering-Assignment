package part2_maze;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.naming.ldap.StartTlsRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import static org.junit.Assert.*;

public class GameTest
{
    Game game;
    @Before
    public void setup(){
        game = new Game();
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();



    @After
    public void cleanup() {
        game = null;
    }



    @Test
    public void testPlayerSet(){
        assertEquals(true,game.setNumberOfPlayers(2));
    }

    @Test
    public void testPlayerSetIncorrect(){
        assertEquals(false,game.setNumberOfPlayers(9));
    }

    @Test
    public void testHTMLString(){
        assertNotEquals("", Game.htmlString());
    }

    @Test
    public void testGenerateHTMLOpenFile(){
            Game.generateHTMLFiles();
    }

    @Test
    public void testGenerateHTMLOpenFileName(){
        Game.generateHTMLFiles();
        assertNotEquals("",Game.current_file_name);
    }

    @Test
    public void testGenerateHTMLOpenFileIncorrect() throws IOException{
        Game.generateHTMLFiles();
        final RandomAccessFile raFile = new RandomAccessFile("src/gameFiles/"+Game.current_file_name,"rw");
        raFile.getChannel().lock();
        exception.expect(IOException.class);

    }

    @Test
    public void testStartGame(){
        String data = "2" +
                "\n5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game.startGame();
    }






}
