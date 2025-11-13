import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";
    private static final int PORT = 24175;

    public static void main(String[] args){

        //The client will send in a request to the server requesting a game (for this program, you always assume the client and server is on the same machine 
        try (Socket socket = new Socket(SERVER_IP, PORT)){
            PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            Scanner scanner = new Scanner(System.in);

            System.out.println("Connected to server!");

            //Once the server accepts a connection from the client, it should inform the client which player (player one or player two) that they are
            String playerNum = in.readLine();
            System.out.println("You are player" + playerNum);

            //The client should then send an ID (for this program, a single string) to the server for the record.
            System.out.print("Enter your player ID: ");
            String playerID = scanner.nextLine();
            out.println(playerID);

            System.out.println("\nWaiting for game to start...\n");

            int myPlayerNum = Integer.parseInt(playerNum);

            //Play the 2 rounds
            for(int r=0; r<2; r++){
                 
                //Once the client receives the information, it should send in a number
                String request = in.readLine();
                int minRange, maxRange;
                if(request.equals("SEND_NUMBER")){
                    if ((r  == 0 && myPlayerNum == 1) || (r == 1 && myPlayerNum == 2)) {
                        // Player picks first (50-99)
                        minRange = 50;
                        maxRange = 99;
                        System.out.println("ROUND " + (r + 1) + ":");
                        System.out.println("You are picking FIRST number this round (range: 50-99)");
                    } else {
                        // Player picks second (60-99)
                        minRange = 60;
                        maxRange = 99;
                        System.out.println("ROUND " + (r + 1) + ":");
                        System.out.println("You are picking the SECOND this round (range: 60-99)");
                    }

                    /*
                    ADDING SOME MORE GAME LIKE MECHANICS (I felt like it):
                    A Retry Mechanic for number entry if input is a non-number 
                    (Does not check for number value validity since that is part of scoring)
                    */
                    String numberStr = null;
                    boolean validInput = false;
                    while(!validInput){ // simple game valid imput testing and retry loop
                        System.out.print("Enter a non-prime number between " + minRange + "-" + maxRange + ": ");
                        numberStr = scanner.nextLine();

                        try {
                            Integer.parseInt(numberStr);
                            validInput = true;
                        } catch (NumberFormatException e) {
                            System.out.println("Input Error: Please enter a number.");
                        }
                    }
                    out.println(numberStr);
                }


                String scoreResult = in.readLine(); //Recieving the round results 
                String[] resultParts = scoreResult.split(",");

                int num1 = Integer.parseInt(resultParts[0]);
                int num2 = Integer.parseInt(resultParts[1]);
                int scoreP1 = Integer.parseInt(resultParts[2]);
                int scoreP2 = Integer.parseInt(resultParts[3]);

                //The client, after receiving the information about who wins (the round), should print a statement                
                System.out.println("\n--- Round " + (r + 1) + " Results ---");
                System.out.println("Player 1 chose: " + num1 + " (Score: " + scoreP1 + ")");
                System.out.println("Player 2 chose: " + num2 + " (Score: " + scoreP2 + ") \n");

                if(r==1){
                    // The client, after receiving the information about who wins (the whole game), should print a statement (you can select what statement to print, it must be different for the 3 cases, and please, no inappropriate language).
                    int totalP1 = Integer.parseInt(resultParts[4]);
                    int totalP2 = Integer.parseInt(resultParts[5]);
                    int winResult = Integer.parseInt(resultParts[6]);

                    System.out.println("\n---- GAME OVER ----");
                    System.out.println("Player 1 Final Score: " + totalP1);
                    System.out.println("Player 2 Final Score: " + totalP2);

                    if (winResult == 1) {
                        System.out.println("\nCongratulations! You WON!");
                    } else if (winResult == -1) {
                        System.out.println("\nYou LOST. Better luck next time!");
                    } else {
                        System.out.println("\nIt's a DRAW! Good game.");
                    }
                }

            }

            //After that, the client should disconnect from the server, and quit
            System.out.println("\nDisconnecting from server...");

            
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
