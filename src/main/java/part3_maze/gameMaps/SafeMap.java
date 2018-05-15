package part3_maze.gameMaps;

// Importing Libraries and Classes

import java.util.Random;

public class SafeMap extends GameMap
{
    /**
     *  Method which is responsible for generating a safe map
     */
    public void generate()
    {
        if(mapInstance == null)
        {
            mapInstance = this;

            Random random = new Random();
            float percentage = random.nextInt(10 + 1); // Random number from 0 to 10

            int waterLimit = (int)Math.ceil((sizeOfMap*sizeOfMap)*(percentage/100));
            createMap(waterLimit);
        }
    }
}
