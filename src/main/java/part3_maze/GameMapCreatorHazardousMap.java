package part3_maze;

import part3_maze.gameMaps.HazardousMap;



public class GameMapCreatorHazardousMap implements GameMapCreator{

    public void generateGameMap(int xSize, int ySize) {
        GameMap hazardousMap = new HazardousMap();
        hazardousMap.setMapSize(xSize, ySize);
        hazardousMap.generate();

    }
}
