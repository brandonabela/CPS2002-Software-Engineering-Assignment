package part2_maze;

import java.util.Random;
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
        players = new Player[player_count];
        this.map = new Map();
        this.player_count = player_count;
        this.table_dimension = table_dimension;
    }

    private void startGame()
    {
        Random rand = new Random();
        setNumPlayers();
        boolean win = false;
        Scanner sc = new Scanner(System.in);
        int current_player_num = rand.nextInt(player_count);
        while (!win){
            System.out.println("Player: "+current_player_num+" Maker your move");
            char mv = sc.next().charAt(0);
            if(!players[current_player_num].move(mv,table_dimension)){
                System.out.println("Invalid Move");
            }
            current_player_num+=1;
            if(current_player_num > player_count)
                current_player_num = 0;
        }

    }

    private boolean setNumPlayers(){
     if(player_count >= 2 && player_count <=8){
         for (int i = 0; i < player_count; i++) {
             players[i] = new Player(table_dimension);
         }
        return true;
     }else
        return false;
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
