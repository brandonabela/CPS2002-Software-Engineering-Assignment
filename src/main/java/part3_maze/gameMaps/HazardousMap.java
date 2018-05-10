package part3_maze.gameMaps;

import part3_maze.GameMap;
import part3_maze.Position;
import part3_maze.TileType;

import java.util.Random;

public class HazardousMap extends GameMap
{
    public HazardousMap()   {   }

    protected void generate()
    {
        gameMapName = "Hazardous Map";
        if(mapInstance == null){
            mapInstance = this;
            Random random = new Random();
            float percentage = random.nextInt((35-25)+1)+25;
            int waterLimit = (int)Math.ceil((sizeOfMap*sizeOfMap)*(percentage/100));
            createMap(waterLimit);
        }



    }
}
