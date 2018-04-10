package part2_maze;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Game
{
    private int player_count;
    private int table_dimension;
    private static int playerTurn;
    private static Player[] players;
    private static Map map;

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
        playerTurn = rand.nextInt(player_count);

        map.setMapSize(table_dimension, table_dimension);
        map.generate();

        while (!win){
            System.out.println("Player: "+playerTurn+" Maker your move");
            char mv = sc.next().charAt(0);
            if(!players[playerTurn].move(mv,table_dimension)){
                System.out.println("Invalid Move");
            }
            playerTurn+=1;
            if(playerTurn >= player_count-1)
                playerTurn = 0;

            generateHTMLFiles();
        }

    }

    private static void generateHTMLFiles()
    {
        try
        {
            BufferedWriter bufferedWriter = null;

            File playerMapFile = new File("src/gameFiles/map_player_" + String.format("%02d", playerTurn) + ".html");
            playerMapFile.createNewFile();

            FileWriter fw = new FileWriter(playerMapFile);
            bufferedWriter = new BufferedWriter(fw);
            bufferedWriter.write(htmlString());

            bufferedWriter.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private static String htmlString()
    {
        StringBuilder htmlString = new StringBuilder();

        htmlString.append("<!DOCTYPE html>\n" +
                "<html>\n" +
                "\n" +
                "<head>\n" +
                "    <link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">\n" +
                "</head>\n" +
                "\n" +
                "<body>\n" +
                "\n" +
                "<style type=\"text/css\">\n" +
                "    h2, p {margin: 0;}\n" +
                "    i {font-size: 40px; color: white;}\n" +
                "    table {height: 95vh; width: 95vw;}\n" +
                "    .tg td{font-family:Arial, sans-serif; font-size:25px; border-style:solid; border-width:1px; border-color:black; text-align: center;}\n" +
                "    .tg th{font-family:Arial, sans-serif; font-size:25px; border-style:solid; border-width:1px; border-color:black; text-align: center;}\n" +
                "    .tg .tableHeading{font-family:\"Arial Black\", sans-serif !important; border-color:#ffffff; background-color: blue; color: white;}\n" +
                "    .tg .coordinateCell{font-family:\"Arial Black\", sans-serif !important; border-color:#ffffff}\n" +
                "    .tg .grassTile{font-family:\"Arial Black\", sans-serif !important; background-color:#008000; border-color:#000000}\n" +
                "    .tg .unknownTile{font-family:\"Arial Black\", sans-serif !important; background-color:#c0c0c0; border-color:#000000}\n" +
                "</style>\n" +
                "\n" +
                "<table class=\"tg\">");

        htmlString.append("\n        <th class=\"tableHeading\" colspan=\"")
                .append(map.getMapDetail().length+2)
                .append("\"> <h2> Player ")
                .append(String.format("%02d", playerTurn))
                .append("</h2> <p> Moves: ")
                .append(players[playerTurn].getMoves())
                .append(" </p> </th>\n");

        for (int i = 0; i < map.getMapDetail().length+1; i ++)
        {
            htmlString.append("    <tr>\n");

            for (int j = 0; j < map.getMapDetail()[0].length+1; j ++)
            {
                if (i == 0 && j == 0)
                {
                    htmlString.append("    <th class=\"coordinateCell\"></th> \n");
                }
                else if (i == 0)
                {
                    htmlString.append("    <th class=\"coordinateCell\">").append(String.format("%02d", j-1)).append("</th> \n");
                }
                else if (j == 0)
                {
                    htmlString.append("    <th class=\"coordinateCell\">").append(String.format("%02d", i-1)).append("</th> \n");
                }
                else
                {
                    htmlString.append("    <td class=\"")
                            .append(map.tileToString(map.getTileType(i-1, j-1)))
                            .append("\"></td>  \n");
                }
            }

            htmlString.append("    </tr>\n");
        }

        htmlString.append("</table>\n </body>\n </html>");

        return htmlString.toString();
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
