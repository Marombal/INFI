import java.io.*;
import java.net.*;

public class TCPServer extends Thread{

    @Override
    public void run() {
        try {
            // Create a server socket that listens on port 8080
            ServerSocket serverSocket = new ServerSocket(8080);

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
}
