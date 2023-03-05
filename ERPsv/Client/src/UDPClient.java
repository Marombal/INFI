/* import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a new DatagramSocket to send UDP packets
            socket = new DatagramSocket();

            // Convert the message to bytes
            String message = "Hello, Miguel Marombal na área com suporte do meu puto G P T!";
            byte[] data = message.getBytes();

            // Specify the IP address of the server
            InetAddress address = InetAddress.getByName("127.0.0.1");

            // Create a new DatagramPacket to send to the server
            DatagramPacket packet = new DatagramPacket(data, data.length, address, 54321);

            // Send the packet to the server
            socket.send(packet);

            System.out.println("Sent data: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the socket
            if (socket != null) {
                socket.close();
            }
        }
    }
}*/

import java.io.*;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;

        try {
            // Create a new DatagramSocket to send UDP packets
            socket = new DatagramSocket();

            // Read the contents of the XML file into a byte array /Users/miguelmarombal/Desktop/INFI
            File file = new File("/Users/miguelmarombal/Desktop/INFI/OrderTest.xml");
            byte[] data = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(data);
            fileInputStream.close();

            // Specify the IP address of the server
            InetAddress address = InetAddress.getByName("127.0.0.1");

            // Create a new DatagramPacket to send to the server
            DatagramPacket packet = new DatagramPacket(data, data.length, address, 54321);

            // Send the packet to the server
            socket.send(packet);

            System.out.println("Sent data: " + file.getName());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the socket
            if (socket != null) {
                socket.close();
            }
        }
    }
}

