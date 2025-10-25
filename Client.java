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
            for(int i =0; i<2; i++){
                System.out.println("ROUND " + (i + 1) + ":");
                 
                //Once the client receives the information, it should send in a number
                String request = in.readLine();
                if(request.equals("SEND_NUMBER")){
                    

                }


            }

            
           


            //The client, after receiving the information about who wins, should print a statement


            //After that, the client should disconnect from the server, and quit


            
        } catch (IOException e) {
            System.out.println("Client error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
