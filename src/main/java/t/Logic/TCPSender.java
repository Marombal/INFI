package t.Logic;

import java.io.*;
import java.net.*;

public class TCPSender {
    public static void sendString(String message) {
        try {
            // Create a socket to connect to the same machine on port 8080
            Socket socket = new Socket("localhost", 8080);

            // Get the output stream of the socket
            OutputStream outputStream = socket.getOutputStream();

            // Create a PrintWriter to send the message as a string
            PrintWriter printWriter = new PrintWriter(outputStream, true);

            // Send the message
            printWriter.println(message);

            System.out.println(".." + message);

            // Close the socket and the output stream
            printWriter.close();
            outputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}