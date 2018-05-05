package part3_maze;

import java.util.Random;

class Team
{
    Player[] players;

    Team(int teamSize, int[] playerID)
    {
        int mapSize = GameMap.getMapInstance().sizeOfMap;

        players = new Player[teamSize];
        Random random = new Random();

        // The Player ID will be the same size as teamSize
        for (int i = 0; i < teamSize; i ++)
        {
            players[i] = new Player(mapSize, playerID[i]);

            while(!GameMap.getMapInstance().isTileNotUsed(players[i].getLastPosition()))
            {
                players[i].setPlayerStartPosition(new Position(random.nextInt(mapSize), random.nextInt(mapSize)));
            }
        }

    }
}
