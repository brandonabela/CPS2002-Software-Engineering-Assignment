package part3_maze.gameMaps;

import part3_maze.GameMap;
import part3_maze.Position;
import part3_maze.TileType;

import java.util.Random;

public class SafeMap extends GameMap
{
    public SafeMap()    {   }

    protected void generate()
    {
        gameMapName = "Safe Map";
        if(mapInstance == null){
            mapInstance = this;
            mapDetail = new TileType[sizeOfMap][sizeOfMap];
            Random random = new Random();
            float percentage = random.nextInt(10+1);
            int waterLimit = (int)Math.ceil((sizeOfMap*sizeOfMap)*(percentage/100));
            createMap(waterLimit);
        }



    }
}
