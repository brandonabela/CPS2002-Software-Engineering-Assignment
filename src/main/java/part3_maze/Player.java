package part3_maze;

import java.util.Random;
import java.util.ArrayList;

/**
 * Class which is responsible for defining a player object which represent a user within a game
 */
class Player
{
    private StringBuilder moveDirections;       // The directions which the player have moved
    private ArrayList<Position> movedPositions; // The positions the player has visited
    int playerID;

    /**
     * Constructor which is responsible for defining the player
     *
     * @param mapSize the size of the map which the player will be randomly placed within the map
     */
    Player(int mapSize, int playerID)
    {
        Random random = new Random();

        moveDirections = new StringBuilder();
        movedPositions = new ArrayList<Position>();

        setPlayerStartPosition(new Position (random.nextInt(mapSize), random.nextInt(mapSize)));
        this.playerID = playerID;
    }

    /**
     * Responsible for moving the player in the map
     *
     * @param moveDirection the direction which the player would like to move
     * @param gameMap the game map which the player will move on
     * @return true if the player successfully moved / false if the player made incorrect move
     */
    boolean move(char moveDirection, GameMap gameMap)
    {
        Position playerPosition = movedPositions.get(movedPositions.size() - 1);

        switch (moveDirection)
        {
            case 'U':
            {
                if (moveDirections.length() > 1) {   moveDirections.append(", ");  }
                moveDirections.append("Up");

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
                if (moveDirections.length() > 1) {   moveDirections.append(", ");  }
                moveDirections.append("Left");

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
                if (moveDirections.length() > 1) {   moveDirections.append(", ");  }
                moveDirections.append("Down");

                if (playerPosition.getYCoordinate() + 1 < gameMap.getMapDetail().length)
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
                if (moveDirections.length() >= 1) {   moveDirections.append(", ");  }
                moveDirections.append("Right");

                if(playerPosition.getXCoordinate() + 1 < gameMap.getMapDetail()[0].length)
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

    /**
     * Responsible for determining if the given coordinate was visited by the player
     *
     * @param positionToCheck the position which is going to be check
     * @return true if the position was visited / false if the position was not visited by the player
     */
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

    /**
     * Sets the start position as the first visited position
     * if movedPosition is not empty set the first position to be the start position
     * if movedPosition is empty add the start position to the list
     *
     * @param startPosition the start position of the player
     */
    void setPlayerStartPosition(Position startPosition)
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

    // Get Methods

    ArrayList<Position> getMovedPositions()
    {
        return movedPositions;
    }

    Position getLastPosition()
    {
        return movedPositions.get(movedPositions.size() - 1);
    }

    String getMoveDirections()
    {
        return moveDirections.toString();
    }
}
