package part2_maze;
import java.util.Random;
import java.util.ArrayList;

public class Player
{
    private StringBuilder moves;
    private Random rand;
    private ArrayList<Position> positions;

    public Player(int dimension){
        rand = new Random();
        Position p = new Position(rand.nextInt(dimension),rand.nextInt(dimension));
        positions = new ArrayList<Position>();
        this.positions.add(p);
        System.out.println("x value: "+p.getXCoordinate()+ " y value: "+p.getYCoordinate());
        moves = new StringBuilder();
    }


    public boolean move(char direction,int dimension){
        Position p_current = positions.get(positions.size() - 1);
        switch (direction){
            case 'U': moves.insert(moves.length(),"Up");
                      if(p_current.getXCoordinate() -1 < 0){
                          System.out.println("Error in dimesions");
                          return false;
                      }else{
                          Position np = new Position(p_current.getXCoordinate()-1,p_current.getYCoordinate());
                          this.positions.add(np);
                          return true;
                      }

            case 'R': moves.insert(moves.length(),"Right");
                      if(p_current.getYCoordinate() -1 < 0){
                        System.out.println("Error in dimesions");
                        return false;
                    }else{
                        Position np = new Position(p_current.getXCoordinate(),p_current.getYCoordinate()-1);
                        this.positions.add(np);
                        return true;
                     }

            case 'L': moves.insert(moves.length(),"Left");
                    if(p_current.getYCoordinate() +1 >= dimension){
                        System.out.println("Error in dimesions");
                        return false;
                    }else{
                        Position np = new Position(p_current.getXCoordinate(),p_current.getYCoordinate()+1);
                        this.positions.add(np);
                        return true;
                     }

            case 'D': moves.insert(moves.length(),"Down");
                    if(p_current.getXCoordinate() +1 >= dimension){
                      System.out.println("Error in dimesions");
                      return false;
                    }else{
                        Position np = new Position(p_current.getXCoordinate()+1,p_current.getYCoordinate());
                        this.positions.add(np);
                        return true;
                }
            default: return false;
        }
    }


    String getMoves()
    {
        return moves.toString();
    }
}
