package part2_maze;

import java.util.Random;
import java.util.ArrayList;

public class Player
{
    private StringBuilder moves;
    private ArrayList<Position> movedPositions;

    public Player(int mapSize)
    {
        Random random = new Random();

        moves = new StringBuilder();
        movedPositions = new ArrayList<Position>();

        setPlayerStartPosition(new Position (random.nextInt(mapSize), random.nextInt(mapSize)));

        //System.out.println("Start Position " + playerStartPosition.toString());
    }

    public boolean move(char moveDirection, Map map)
    {
        Position playerPosition = movedPositions.get(movedPositions.size() - 1);

        switch (moveDirection)
        {
            case 'U':
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
                    System.out.println("Error in dimension. Player Position is " + playerPosition.toString());
                    return false;
                }
            }

            case 'L':
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
                    System.out.println("Error in dimension. Player Position is " + playerPosition.toString());
                    return false;
                }
            }

            case 'D':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Down");

                System.out.println(map.getMapDetail().length);

                if (playerPosition.getYCoordinate() + 1 < map.getMapDetail().length)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate(), playerPosition.getYCoordinate() + 1));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is " + playerPosition.toString());
                    return false;
                }
            }

            case 'R':
            {
                if (moves.length() > 1) {   moves.append(", ");  }
                moves.append("Right");

                if(playerPosition.getXCoordinate() + 1 < map.getMapDetail()[0].length)
                {
                    this.movedPositions.add(new Position(playerPosition.getXCoordinate() + 1, playerPosition.getYCoordinate()));
                    return true;
                }
                else
                {
                    System.out.println("Error in dimension. Player Position is " + playerPosition.toString());
                    return false;
                }
            }

            default:
            {
                return false;
            }
        }
    }

    public boolean isInMovedList(Position positionToCheck)
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

    public void setPlayerStartPosition(Position startPosition)
    {
        if (movedPositions.size() != 0)
        {
            movedPositions.set(0, startPosition);
        }
        else
        {
            movedPositions.add(startPosition);
        }
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
