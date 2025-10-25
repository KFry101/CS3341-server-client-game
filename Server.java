import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
    private static final int PORT = 24175;
    private static final int[] valid_numbers = {50, 51, 52, 54, 55, 56, 57, 58, 60, 62, 63, 64, 65, 66, 68, 69, 
                            70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88, 90,
                            91, 92, 93, 94, 95, 96, 98, 99};

    public static boolean isValidNumber(int input){
        for (int n : valid_numbers) {
            if (n == input) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args){
        //The server should be started at the background, and it will listen for client connecting. (Use port 24175 for this program
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            System.out.println("Waiting for players to connect...");

            //Once the server collects the names, it should print the statement “<player one ID vs. <player two ID>: Game start”.
            Socket player1Socket = serverSocket.accept();
            System.err.println("Player 1 is connected");
            PrintWriter out1 = new PrintWriter(player1Socket.getOutputStream(), true);
            BufferedReader in1 = new BufferedReader(new InputStreamReader(player1Socket.getInputStream()));
            out1.println("1"); // Inform client they are player 1


            Socket player2Socket = serverSocket.accept();
            System.err.println("Player 2 is connected");
            PrintWriter out2 = new PrintWriter(player2Socket.getOutputStream(), true);
            BufferedReader in2 = new BufferedReader(new InputStreamReader(player2Socket.getInputStream()));
            out2.println("2"); // Inform client they are player 2

            //Once the server collects the names, it should print the statement “<player one ID> vs. <player two ID>: Game start”.


            //Then the server should send a request to ask the two players to send in the first number.


            /*Once the server receives the numbers, it should calculate the score for this round. 
            And then sent the score of both players, together with the numbers both player picked, back to the client.*/


            //The server should then print the result of round 1: including the number selected,and the score of each player. 


            // Then the server should calculate the score for round 2, and send the following to the players
            //The score of round 2 for each player
            //The total score for each player
            //Whether the player win/lose/draw the match (sending 1/-1/0 respectively

            
            /*The server should then print the result of round 2: including the number selected,
            and the score of each player, and then print out who is the winner. */


            //The server should also quit.


          
        } catch (Exception e) {

        }
    }


}

   
