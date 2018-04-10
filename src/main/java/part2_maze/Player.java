package part2_maze;

import javafx.geometry.Pos;

import java.util.Random;
import java.util.ArrayList;

public class Player
{
    private StringBuilder moves;
    private ArrayList<Position> movedPositions;

    public Player(int dimension)
    {
        Random random = new Random();

        moves = new StringBuilder();
        movedPositions = new ArrayList<Position>();

        Position playerStartPosition = new Position(random.nextInt(dimension), random.nextInt(dimension));
        this.movedPositions.add(playerStartPosition);

        //System.out.println("Start Position (" + playerStartPosition.getXCoordinate() + ", " + playerStartPosition.getYCoordinate() + ")");
    }

    boolean move(char moveDirection, Map map)
    {
        Position playerPosition = movedPositions.get(movedPositions.size() - 1);

        switch (moveDirection)
        {
            case 'W':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Up");

                if(0 <= playerPosition.getYCoordinate() - 1)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate(), playerPosition.getYCoordinate() - 1));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is" + playerPosition.toString());
                    return false;
                }
            }

            case 'A':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Left");

                if (0 <= playerPosition.getXCoordinate() - 1)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate() - 1, playerPosition.getYCoordinate()));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is" + playerPosition.toString());
                    return false;
                }
            }

            case 'S':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Down");

                if (playerPosition.getYCoordinate() + 1 < map.getMapDetail().length - 1)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate(), playerPosition.getYCoordinate() + 1));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is" + playerPosition.toString());
                    return false;
                }
            }

            case 'D':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Right");

                if(playerPosition.getXCoordinate() + 1 < map.getMapDetail()[0].length - 1)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate() + 1, playerPosition.getYCoordinate()));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is" + playerPosition.toString());
                    return false;
                }
            }

            default:
            {
                return false;
            }
        }
    }

    boolean isInMovedList(Position positionToCheck)
    {
        for (Position movedPosition : movedPositions)
        {
            if (movedPosition.getXCoordinate() == positionToCheck.getXCoordinate() &&
                    movedPosition.getYCoordinate() == positionToCheck.getYCoordinate())
            {
                return true;
            }
        }

        return false;
    }

    ArrayList<Position> getMovedPositions()
    {
        return movedPositions;
    }

    String getMoves()
    {
        return moves.toString();
    }
}
