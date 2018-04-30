package part3_maze;
import java.util.ArrayList;
import java.util.Random;
public class Team {
    Player[] players;


    Team(int teamSize,Map map,int[] playerIDs){
        int MapSize = map.getMapDetail().length;

        players = new Player[teamSize];
        Random rand = new Random();
        //playerIDs will be the same size as teamSIze
        for (int i = 0; i < teamSize; i ++)
        {
            players[i] = new Player(MapSize,playerIDs[i]);

            while(!map.isTileNotUsed(players[i].getLastPosition()))
            {
                players[i].setPlayerStartPosition(new Position(rand.nextInt(MapSize), rand.nextInt(MapSize)));
            }
        }

    }

}
