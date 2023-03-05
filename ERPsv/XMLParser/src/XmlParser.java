import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.io.FileWriter;
import java.io.IOException;

public class XmlParser {

    public static void main(String[] args) {

        /* Parse XML file*/
        try {
            // Create a new DocumentBuilderFactory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

            // Use the factory to create a new DocumentBuilder
            DocumentBuilder builder = factory.newDocumentBuilder();

            // Parse the XML file
            Document doc = builder.parse("OrderTest.xml");

            // Get a list of all elements in the document
            NodeList nodeList = doc.getDocumentElement().getChildNodes();

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

        /* Save String as XML FILE*/
        String xmlString = "<order><message>something...</message></order>";
        String fileName = "order.xml";

        try {
            FileWriter writer = new FileWriter(fileName);
            writer.write(xmlString);
            writer.close();
            System.out.println("Successfully saved XML to " + fileName);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the XML: " + e.getMessage());
        }




    }
}
