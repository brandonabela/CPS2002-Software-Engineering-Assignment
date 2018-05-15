package part3_maze.gameMaps;

// Importing Libraries and Classes

import java.util.Random;

public class HazardousMap extends GameMap
{
    /**
     * Method which is responsible for generating a hazardous map
     */
    public void generate()
    {
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
