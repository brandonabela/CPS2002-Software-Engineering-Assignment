package part3_maze;
import part3_maze.gameMaps.SafeMap;

public class GameMapCreatorSafeMap implements GameMapCreator {
    public void generateGameMap(int xSize, int ySize) {
        GameMap safeMap = new SafeMap();
        safeMap.setMapSize(xSize, ySize);
        safeMap.generate();
    }
}
