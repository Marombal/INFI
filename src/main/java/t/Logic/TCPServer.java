package t.Logic;

import java.io.*;
import java.net.*;
import java.util.Arrays;

public class TCPServer extends Thread{

    @Override
    public void run() {
        try {
            // Create a server socket that listens on port 8080
            //ServerSocket serverSocket = new ServerSocket(9090);
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
                parseMessage(message);

                // Close the socket and the reader
                reader.close();
                clientSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void parseMessage(String message) {
        // Extract the day and transformation times from the message
        String dayString = message.substring(0, 2);
        int day = Integer.parseInt(dayString);

        int[] transformationTimes = new int[7];
        for (int i = 0; i < 7; i++) {
            String timeString = message.substring(2 + (i * 3), 5 + (i * 3));
            int time = Integer.parseInt(timeString);
            transformationTimes[i] = time;
        }

        // Print the parsed values
        System.out.println("Day: " + day);
        System.out.println("Transformation times: " + Arrays.toString(transformationTimes));

        for (int i = 3; i <= 9; i++){
            MPS.UpdateProductionPlan(day, i, transformationTimes[i-3]);
        }

    }

}