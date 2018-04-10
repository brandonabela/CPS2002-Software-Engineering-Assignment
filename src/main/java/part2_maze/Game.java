package part2_maze;

import java.util.Scanner;

public class Game
{
    private int player_count;
    private int table_dimension;
    private int turns;
    private Player players[];
    private Map map;

    private Game(int player_count, int table_dimension)
    {
        this.player_count = player_count;
        this.table_dimension = table_dimension;
    }

    private void startGame()
    {
        if(player_count >= 2 && player_count <= 4 && table_dimension >= 5 && table_dimension <= 50)
        {
            System.out.println("Player Count: " + player_count + "\n" + "Map Dimension: " + table_dimension + "x" + table_dimension);
        }
        else if(player_count >= 5 && player_count <= 8 && table_dimension >= 8 && table_dimension <= 50)
        {
            System.out.println("Player Count: "+ player_count + "\n" + "Map Dimension: " + table_dimension + "x" + table_dimension);
        }
        else
        {
            System.out.println("Error Incorrect dimensions or player count");
        }
    }

    public static void main(String args[]){

        Scanner sc = new Scanner(System.in);

        int player_number;
        int table_size;

        System.out.println("Please enter the number of players");
        player_number = sc.nextInt();

        System.out.println("Please enter the dimension size for the tables");
        table_size = sc.nextInt();

        Game game = new Game(player_number,table_size);
        game.startGame();
    }
}
