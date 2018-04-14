package part2_maze;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
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
        assertEquals(2,game.amountOfPlayers);
    }
    @Test
    public void testMapSet()
    {
        assertEquals(5,game.mapSize);
    }
    @Test
    public void testPlayerDoesNotStartonWaterorTreasure(){
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        Game.map.changeTileType(0,1,TileType.WATER);
        game.setNumberOfPlayers(2);
        if(Game.players[0].getLastPos().getXCoordinate() == 0 && Game.players[0].getLastPos().getYCoordinate() == 0)
            fail();
    }

    @Test
    public void testExceptionCatch(){
        String data = "10\n5\n2\n5";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game = new Game();

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
    public void testCurrentPlayerAfterPlayerDies(){
        Game.playerTurn = 1;
        game.lostPlayers.add(1);
        game.SwitchToAlivePlayer();
        assertEquals(0,Game.playerTurn);
    }

   @Test
   public void testPlayerNotDead(){
       assertEquals(false,game.isPlayerDead(1));
   }
   @Test
   public void testAllDeadPlayer(){
       game.lostPlayers.add(0);
       game.lostPlayers.add(1);
       assertEquals(true,game.CheckIfAllPlayerAreDead());
   }

    @Test
    public void testOneDeadPlayer(){
        game.lostPlayers.add(1);
        assertEquals(false,game.CheckIfAllPlayerAreDead());
    }
    @Test
    public void testPlayerSwitch(){
        game.lostPlayers.add(1);
        game.SwitchToAlivePlayer();
        assertEquals(0,Game.playerTurn);
    }

    @Test
    public void TestCorrectInputForMove(){
        Game.playerTurn = 0;
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        String data = "R";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game.TryToMove();
        assertEquals(0,Game.players[0].getLastPos().getYCoordinate());
    }

    @Test
    public void testIncorrectMoveThenCorrect(){
        Game.playerTurn = 0;
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        String data = "U\nR";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        game.TryToMove();
        assertEquals(1,Game.players[0].getLastPos().getXCoordinate());
    }

    @Test
    public void testPlayerMovingIntoWaterTile(){
        Game.playerTurn = 0;
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        String data = "R";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game.map.changeTileType(0,1,TileType.WATER);
        game.TryToMove();
        game.CheckMovedTile();
        assertEquals(true,game.isPlayerDead(0));
    }

    @Test
    public void testPlayerMovingIntoTreasure(){
        Game.playerTurn = 0;
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        String data = "R";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game.map.changeTileType(0,1,TileType.TREASURE);
        game.TryToMove();
        game.CheckMovedTile();
        assertEquals(true,game.playerWon);
    }
    @Test
    public void teststartGameWin(){
        game.playerWon = true;
        game.startGame();

    }

    @Test
    public void teststartGameAllDeadPlayers(){
        game.lostPlayers.add(0);
        game.lostPlayers.add(1);
        game.startGame();
        assertEquals(false,game.playerWon);
    }

    @Test
    public void teststartGamePlayerWins(){
        game.lostPlayers.add(1);
        Game.players[0].setPlayerStartPosition(new Position(0,0));
        String data = "R";
        System.setIn(new ByteArrayInputStream(data.getBytes()));
        Game.map.changeTileType(0,1,TileType.TREASURE);
        game.startGame();
        assertEquals(true,game.playerWon);
    }

    @After
    public void cleanup()
    {
        game = null;
    }
}
