package part3_maze.gameMaps;

import java.util.Random;

public class HazardousMap extends GameMap
{
    public HazardousMap(){}

    public void generate()
    {
        gameMapName = "Hazardous Map";

        if(mapInstance == null)
        {
            mapInstance = this;
            Random random = new Random();
            float percentage = random.nextInt((35-25) + 1) + 25; // Random number between 25 and 35
            int waterLimit = (int)Math.ceil((sizeOfMap*sizeOfMap) * (percentage/100));
            createMap(waterLimit);
        }
    }
}
