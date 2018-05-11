package part3_maze;

import part3_maze.gameMaps.GameMap;

import java.util.Random;

class Team
{
    Player[] players;

    Team(int teamSize, GameMap generatedMap, int[] playerID)
    {
        players = new Player[teamSize];
        Random random = new Random();

        // The Player ID will be the same size as teamSize
        for (int i = 0; i < teamSize; i ++)
        {
            players[i] = new Player(generatedMap.sizeOfMap, playerID[i]);

            while(!generatedMap.isTileNotUsed(players[i].getLastPosition()))
            {
                players[i].setPlayerStartPosition(new Position(random.nextInt(generatedMap.sizeOfMap), random.nextInt(generatedMap.sizeOfMap)));
            }
        }

    }
}
