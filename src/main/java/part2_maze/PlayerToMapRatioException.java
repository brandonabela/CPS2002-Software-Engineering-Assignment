package part2_maze;

public class PlayerToMapRatioException extends Exception{
    public PlayerToMapRatioException(){
        System.out.println("Error Incorrect dimensions to match player count");
    }

}
