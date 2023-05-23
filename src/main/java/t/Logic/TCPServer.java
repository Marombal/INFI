package t.Logic;

import java.io.*;
import java.net.*;

public class TCPServer extends Thread{

    @Override
    public void run() {
        try {
            // Create a server socket that listens on port 8080
            ServerSocket serverSocket = new ServerSocket(9090);

            System.out.println("TCP Server listening on port 8080...");
            // Accept incoming connections from clients
            while (true) {
                Socket clientSocket = serverSocket.accept();

                // Read the message sent by the client
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String message = reader.readLine();

                // Print the message to the console
                System.out.println("Received message: " + message);

                // Close the socket and the reader
                reader.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseMessage(String message) {
        if (message.length() < 15) {
            System.out.println("Invalid message format!");
            return;
        }

        // Extract the day from the message
        String dayString = message.substring(1, 3);
        int day = Integer.parseInt(dayString);

        // Extract the time taken for each piece type
        int[] timeTaken = new int[7];
        for (int i = 0; i < 7; i++) {
            String timeString = message.substring(5 + i * 2, 7 + i * 2);
            int time = Integer.parseInt(timeString);
            timeTaken[i] = time;
        }

        // Print the parsed values
        System.out.println("Day: " + day);
        for (int i = 0; i < 7; i++) {
            System.out.println("Time taken for piece type " + (i + 3) + ": " + timeTaken[i] + " seconds");
        }
    }

}