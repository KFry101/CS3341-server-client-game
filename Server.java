import java.io.*;
import java.net.*;
import java.util.*;


public class Server {
    private static final int PORT = 24175;
    private static final int[] valid_numbers = {50, 51, 52, 54, 55, 56, 57, 58, 60, 62, 63, 64, 65, 66, 68, 69, 
                            70, 72, 74, 75, 76, 77, 78, 80, 81, 82, 84, 85, 86, 87, 88, 90,
                            91, 92, 93, 94, 95, 96, 98, 99};

    public static int findLCM(int a, int b) {
        return (a * b) / HCF.findHCF(a, b);
    }

    public static boolean isValidNumber(int input, int picked){
        for (int n : valid_numbers) {
            if (n == input) {
                if (picked ==1){
                    return true;
                }
                else{
                    if(input >= 60){
                        return true;
                    }
                }
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
            String player1ID = in1.readLine();
            String player2ID = in2.readLine();

            System.out.println(player1ID + " vs. " + player2ID + ": Game start\n");

            int[] player1Rounds = new int[2];
            int[] player2Rounds = new int[2];
            int player1TotalScore = 0;
            int player2TotalScore = 0;

            //play 2 rounds
            for(int i =0; i<2; i++){
                System.out.println("\nROUND " + (i + 1) + ":");
                 
                //Then the server should send a request to ask the two players to send in the first number.
                out1.println("SEND_NUMBER");
                out2.println("SEND_NUMBER");

                //Once the server receives the numbers, it should calculate the score for this round. 
                String num1In = in1.readLine();
                String num2In= in2.readLine();

                int num1 = Integer.parseInt(num1In);
                int num2 = Integer.parseInt(num2In);

                int scoreP1 = 0;
                int scoreP2 = 0;

                // Determine who is player one/two in this round
                int p1Num, p2Num;
                boolean p1Valid, p2Valid;

                if (i == 0){
                    // Round 1: Player 1 picks first (range 50-99), Player 2 picks second (range 60-99)
                    p1Valid = isValidNumber(num1, 1);
                    p2Valid = isValidNumber(num2, 2);

                } else {
                    // Round 2: Player 2 picks first (range 50-99), Player 1 picks second (range 60-99)
                    p2Valid = isValidNumber(num2, 1);
                    p1Valid = isValidNumber(num1, 2);
                }


                // Check if players picked the same number in both rounds
                boolean p1SameNumber = (i == 1 && num1 == player1Rounds[0]);
                boolean p2SameNumber = (i == 1 && num2 == player2Rounds[0]);


                //SCORE CALCULATIONS -----------------------------------
                if(num1 != num2){

                    if(!p1Valid || p1SameNumber){ // if player 1's # is prime, not in range, or it is same as round 1 during round 2
                        scoreP1 = 0;
                        if (p2Valid && !p2SameNumber) scoreP2 = 100;
                    }
                
                    if ( !p2Valid || p2SameNumber){ // if player 2's # is prime, not in range, or it is same as round 1 during round 2
                        scoreP2 = 0;
                        if (p1Valid && !p1SameNumber) scoreP1 = 100;
                    }

                    if (p1Valid && !p1SameNumber && p2Valid && !p2SameNumber) {
                        if (i == 0) {
                            // Round 1: P1 is first player, gets HCF of the two numbers
                            scoreP1 = HCF.findHCF(num1, num2);
                            // P2 is second player, gets last digit of LCM of the two numbers
                            int lcm = findLCM(num1, num2);
                            scoreP2 = lcm % 10;
                        } else { 
                            // Round 2: P2 is first player, gets HCF
                            scoreP2 = HCF.findHCF(num1, num2);
                            // P1 is second player, gets last digit of LCM
                            int lcm = findLCM(num1, num2);
                            scoreP1 = lcm % 10;
                        }
                    }
                } 
                else{ //If both players make the same choices, both will get 0 points
                    scoreP1 = 0;
                    scoreP2 = 0;
                }

                player1Rounds[i] = num1;
                player2Rounds[i] = num2;

                //Total Score Calculations
                player1TotalScore += scoreP1;
                player2TotalScore += scoreP2;

                //And then sent the score of both players, together with the numbers both player picked, back to the client.
                //The server should then print the result of round 1: including the number selected,and the score of each player. 
                if (i == 0) {
                    // sending it back to clients
                    out1.println(num1 + "," + num2 + "," + scoreP1 + "," + scoreP2); //Sending results to client player 1
                    out2.println(num1 + "," + num2 + "," + scoreP1 + "," + scoreP2); //Sending results to client player 2

                    //Printing in server
                    System.out.println("Player 1 (" + player1ID + ") chose: " + num1 + ", Score: " + scoreP1); 
                    System.out.println("Player 2 (" + player2ID + ") chose: " + num2 + ", Score: " + scoreP2 + "\n");

                } else {
                    //Then the server should calculate the score for round 2, and send the following to the players:
                    // The score of round 2 for each player 
                        //Calculated Above
                    // The total score for each player 
                        //Calculated Above
                    
                    //Whether the player win/lose/draw the match (sending 1/-1/0 respectively)
                    int resultP1 = (player1TotalScore > player2TotalScore) ? 1 : (player1TotalScore < player2TotalScore) ? -1 : 0;
                    int resultP2 = (player2TotalScore > player1TotalScore) ? 1 : (player2TotalScore < player1TotalScore) ? -1 : 0;

                    // sending it back to clients
                    out1.println(num1 + "," + num2 + "," + scoreP1 + "," + scoreP2 + "," + player1TotalScore + "," + player2TotalScore + "," + resultP1); //Sending all results to client player 1
                    out2.println(num1 + "," + num2 + "," + scoreP1 + "," + scoreP2 + "," + player1TotalScore + "," + player2TotalScore + "," + resultP2); //Sending all results to client player 2


                    /*The server should then print the result of round 2: including the number selected,
                    and the score of each player, and then print out who is the winner. */
                    System.out.println("Player 1 (" + player1ID + ") chose: " + num1 + ", Score: " + scoreP1);
                    System.out.println("Player 2 (" + player2ID + ") chose: " + num2 + ", Score: " + scoreP2);
                    System.out.println("\n=== GAME OVER ===");
                    System.out.println("Final Score - " + player1ID + ": " + player1TotalScore + ", " + player2ID + ": " + player2TotalScore);

                }

            }
        
            player1Socket.close();
            player2Socket.close();
            //The server should also quit.
            System.out.println("\nServer shutting down...");          

        } catch (IOException e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

   
