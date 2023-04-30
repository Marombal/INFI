package t.Logic;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPListener extends Thread{

    @Override
    public void run(){
        DatagramSocket socket = null;
        DatagramPacket packet = null;

        System.out.println("ERP here, waiting orders via UDP (port: 54321)... ");

        try {
            // Create a new DatagramSocket to receive UDP packets
            socket = new DatagramSocket(54321);

            // Create a new byte array to store incoming packet data
            byte[] buffer = new byte[1024 * 2];

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



                    NodeList orderNodes = doc.getElementsByTagName("Order");
                    int numOrders = orderNodes.getLength();
                    // Print the number of orders to the console
                    System.out.println("Number of orders: " + numOrders);

                    Order[] order = new Order[numOrders];

                    for (int i = 0; i < orderNodes.getLength(); i++) {
                        Node orderNode = orderNodes.item(i);

                        if (orderNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element orderElement = (Element) orderNode;
                            String number = orderElement.getAttribute("Number");
                            String workPiece = orderElement.getAttribute("WorkPiece");
                            String quantity = orderElement.getAttribute("Quantity");
                            String dueDate = orderElement.getAttribute("DueDate");
                            String latePen = orderElement.getAttribute("LatePen");
                            String earlyPen = orderElement.getAttribute("EarlyPen");

                            order[i] = new Order(number, workPiece, quantity, dueDate, latePen, earlyPen, "WAITING");

                            //order[i].InsertOrderInDB();

                            MPS.addOrder(order[i]);

                            //MPS.updateMPS3(order[i]);
                            MPS.updateMPS4(order[i]); MPS.print20daysMES();//MPS.print20days();

                        }
                        // order[i].printOrder();
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





