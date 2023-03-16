import java.io.FileWriter;
import java.io.IOException;
import java.net.*;
import javax.xml.parsers.*;
import org.xml.sax.*;
import java.io.ByteArrayInputStream;
import java.net.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.time.LocalTime;


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
                // System.out.println("Received data: " + message); // DEBUG

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

                    // System.out.println("Parsed to:"); // DEBUG

                    Order order = new Order();
                    //System.out.println(doc.getDocumentElement().getAttributes());

                    // Print the text content of each element
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node node = nodeList.item(i);
                        if (node.getNodeType() == Node.ELEMENT_NODE) {
                            // System.out.println(node.getNodeName() + ": " + node.getTextContent()); // DEBUG

                            int NumberOfAttributes = node.getAttributes().getLength();

                            for(int j = 0; j < NumberOfAttributes; j++){
                                // System.out.println("Attributes ->  " + node.getAttributes().item(j)); // DEBUG
                                // System.out.println(extractStringFromQuotes(node.getAttributes().item(j).toString())); // DEBUG


                                fillOrder(node.getAttributes().item(j).toString(), order);

                                DataBase.readOrder();
                            }

                        }
                    }

                    order.printOrder();
                    LocalTime currentTime = LocalTime.now();
                    System.out.println("The current time is: " + currentTime);


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


    public static int extractIntFromQuotes(String str) {
        int startIndex = str.indexOf('"') + 1;
        int endIndex = str.lastIndexOf('"');
        String numString = str.substring(startIndex, endIndex);
        return Integer.parseInt(numString);
    }

    public static String extractStringFromQuotes(String str) {
        int startIndex = str.indexOf('"') + 1;
        int endIndex = str.lastIndexOf('"');
        String extractedString = str.substring(startIndex, endIndex);
        return extractedString;
    }

    /*
    * fillOrder
    * find what type of attributes had in the "str"
    * adds the respective attribute to the class order
    * */
    public static void fillOrder(String str, Order order){
        //str can be from all types of attributes, find what type it is and add to that type

        /* checks if it is the order Number */
        if(hasSubstring(str, "Number")){
            order.setOrderNumber(extractStringFromQuotes(str));
        }

        /* checks if it is the order WorkPiece */
        else if(hasSubstring(str, "WorkPiece")){
            order.setWorkPiece(extractStringFromQuotes(str));
        }

        /* checks if it is the order Quantity */
        else if(hasSubstring(str, "Quantity")){
            order.setQuantity(extractStringFromQuotes(str));
        }

        /* checks if it is the order DueDate */
        else if(hasSubstring(str, "DueDate")){
            order.setDueDate(extractStringFromQuotes(str));
        }

        /* checks if it is the order LatePen */
        else if(hasSubstring(str, "LatePen")){
            order.setLatePen(extractStringFromQuotes(str));
        }

        /* checks if it is the order EarlyPen */
        else if(hasSubstring(str, "EarlyPen")){
            order.setEarlyPen(extractStringFromQuotes(str));
        }
    }
    public static boolean hasSubstring(String str, String subStr) {
        return str.contains(subStr);
    }

}
