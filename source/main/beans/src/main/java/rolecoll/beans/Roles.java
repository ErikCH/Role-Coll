package rolecoll.beans;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.HashMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.io.InputStream;
import java.io.IOException;

import rolecoll.beans.utility.XmlUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: Apr 20, 2007
 * Time: 12:17:08 AM
 */

/**
 * Reads Roles from an xml
 */
public class Roles {
    private static HashMap rolesMap = new HashMap();

    // properties file
    private static final String PROP_FILE = "Roles.xml";

    public static HashMap getRolesMap() {
        return rolesMap;
    }

    public static void setRolesMap(HashMap rolesMap) {
        Roles.rolesMap.putAll(rolesMap);
    }

    /**
     * read roles into a map for user when creating users
     */
    public static void getRoles(){
        try{
            InputStream propFile = Users.class.getClassLoader().getResourceAsStream(PROP_FILE);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setNamespaceAware(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document propDoc = builder.parse(propFile);

            Collection roles = XmlUtilities.getCollection(propDoc.getDocumentElement());
            Iterator iter = roles.iterator();
            while(iter.hasNext()){
                Node singleRole = (Node)iter.next();
                Map singleRoleMap = XmlUtilities.getMap(singleRole);
                rolesMap.put(singleRoleMap.get("name"), singleRoleMap.get("priority"));
            }
        } catch (SAXException e1) {
            e1.printStackTrace();
        } catch (ParserConfigurationException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
