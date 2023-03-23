import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.*;

public class XMLParser {
    public static void XMLparser(String[] args) {
        try {
            // Parse the XML file and get the root element
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse("example.xml");
            Element root = doc.getDocumentElement();

            // Get the client name from the root element
            String clientName = root.getAttribute("NameId");
            System.out.println("Client name: " + clientName);

            // Get the order elements from the root element
            NodeList orderNodes = root.getElementsByTagName("Order");
            for (int i = 0; i < orderNodes.getLength(); i++) {
                // Get the attributes of each order element
                Element orderElement = (Element) orderNodes.item(i);
                int orderNumber = Integer.parseInt(orderElement.getAttribute("Number"));
                String workPiece = orderElement.getAttribute("WorkPiece");
                int quantity = Integer.parseInt(orderElement.getAttribute("Quantity"));
                int dueDate = Integer.parseInt(orderElement.getAttribute("DueDate"));
                int latePen = Integer.parseInt(orderElement.getAttribute("LatePen"));
                int earlyPen = Integer.parseInt(orderElement.getAttribute("EarlyPen"));

                // Create a new Order object with the parsed attributes

                //Order order = new Order(orderNumber, workPiece, quantity, dueDate, latePen, earlyPen, "WAITING");
                //System.out.println("Order: " + order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

