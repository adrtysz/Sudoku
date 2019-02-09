package main.java.networking;
/**
 * Konfiguracja serwera z pliku XML
 */
import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ServerConfig {
    private static String hostName;


    static {
        try {
            loadFromXML();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadFromXML() throws Exception {
        File xmlFile = new File("src/serverConfig.xml");
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        NodeList nl = doc.getElementsByTagName("server");
        Node nNode = nl.item(0);
        if(nNode.getNodeType() == Node.ELEMENT_NODE) {
            Element e = (Element) nNode;
            hostName = e.getElementsByTagName("hostName").item(0).getTextContent();
        }

    }

    public static String getHostName() {
        return hostName;
    }
}