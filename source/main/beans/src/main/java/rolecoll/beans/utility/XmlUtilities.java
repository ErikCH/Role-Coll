package rolecoll.beans.utility;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.HashMap;
import java.util.Collection;
import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 20, 2007
 * Time: 12:10:19 AM
 */

/**
 * Common utilities for xml manipulation
 */
public class XmlUtilities {
    /**
     * This method creates a map of all of the childern of a node
     * @param root - Parent node
     * @return HashMap key value map of the children of the parent node
     */
    public static HashMap getMap(Node root){
        HashMap map = new HashMap();
        if(root != null){
            NodeList children = root.getChildNodes();
            for(int i = 0; i < children.getLength(); i++){
                Node child = children.item(i);
                String nodeName = child.getNodeName();
                if(!nodeName.equalsIgnoreCase("#comment") && !nodeName.equalsIgnoreCase("#text")){
                    if(child.getChildNodes().getLength()>1){
                        map.put(nodeName, child);
                    } else {
                        Node textChild = child.getFirstChild();
                        if(textChild == null){
                            map.put(nodeName, null);
                        } else{
                            map.put(nodeName, child.getFirstChild().getNodeValue());
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * This method creates a collection of child nodes from a parent node
     * @param root the parent node
     * @return Collection - childern nodes
     */
    public static Collection getCollection(Node root){
        Collection collection = new ArrayList();
        if(root != null){
            NodeList children = root.getChildNodes();
            for(int i = 0; i < children.getLength(); i++){
                Node child = children.item(i);
                String nodeName = child.getNodeName();
                if(!nodeName.equalsIgnoreCase("#comment") && !nodeName.equalsIgnoreCase("#text")){
                    if(child.getChildNodes().getLength()>1){
                        collection.add(child);
                    } else {
                        Node textChild = child.getFirstChild();
                        if(textChild == null){
                            // don't accept nulls
                        } else{
                            collection.add(child.getFirstChild().getNodeValue());
                        }
                    }
                }
            }
        }
        return collection;
    }
}
