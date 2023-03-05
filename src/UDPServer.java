import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.ByteArrayInputStream;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

public class UDPServer {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        DatagramPacket packet = null;

        System.out.println("ERP here, waiting orders via UDP (port: 54321)... ");

        /* */
        try {
            // Create a new DatagramSocket to receive UDP packets
            socket = new DatagramSocket(54321);

            // Create a new byte array to store incoming packet data
            byte[] buffer = new byte[1024];

            // Create a new DatagramPacket to receive incoming packets
            packet = new DatagramPacket(buffer, buffer.length);

            // Wait for incoming packets and process them
            while (true) {
                // Receive a packet from the client
                socket.receive(packet);

                // Convert the packet data to a String
                String message = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received data: " + message);

                /* Saves Received data as XML file */
                String fileName = "Order.xml";

                try {
                    FileWriter writer = new FileWriter(fileName);
                    writer.write(message);
                    writer.close();
                    System.out.println("Successfully saved XML to " + fileName);
                } catch (IOException e) {
                    System.out.println("An error occurred while saving the XML: " + e.getMessage());
                }

                /* Parses XML file */
                try {
                    // Create a new DocumentBuilderFactory
                    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

                    // Use the factory to create a new DocumentBuilder
                    DocumentBuilder builder = factory.newDocumentBuilder();

                    // Parse the XML file
                    Document doc = builder.parse("Order.xml");

                    // Get a list of all elements in the document
                    NodeList nodeList = doc.getDocumentElement().getChildNodes();

                    System.out.println("Parsed to:");
                    // Print the text content of each element
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            System.out.println(node.getNodeName() + ": " + node.getTextContent());
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
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
