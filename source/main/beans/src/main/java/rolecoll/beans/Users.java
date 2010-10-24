package rolecoll.beans;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import java.util.*;
import java.io.InputStream;
import java.io.IOException;

import rolecoll.beans.dao.UsersBeanFactory;
import rolecoll.beans.utility.XmlUtilities;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Nov 24, 2006
 * Time: 1:06:06 PM
 */
/**
 * This class loads the user tree in the main and admin frame
 */
public class Users {

    /**
     * Users Tree Node available to all classes
     * (root)
     */
    public static UserTreeNode userTree = new UserTreeNode();

    /**
     * User Maps Available to all classes
     * create maps to save db querys
     */
    public static HashMap userMap;
    public static HashMap userNameMap;
    public static HashMap userIDMap;

    // properties file
    private static final String PROP_FILE = "Users.xml";

    /**
     * This method loads the users and creates the user tree
     */
    public static void getUsers(){
        Collection userNodes = new ArrayList();
        userMap = new HashMap();
        try {
            // get users from DB
            userMap.putAll(UsersBeanFactory.getUserMap());
            Set keys = userMap.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                UserBean userBean = (UserBean) userMap.get(iterator.next());
                // create user tree nodes
                UserTreeNode userNode = new UserTreeNode(
                        userBean.getWholeName());
                userNode.setUserInfo(userBean);
                userNodes.add(userNode);
            }
        } catch(Exception e){
            try{
                // log error and continue
                e.printStackTrace();
                // load users file
                InputStream propFile = Users.class.getClassLoader().getResourceAsStream(PROP_FILE);
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                factory.setNamespaceAware(true);
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document propDoc = builder.parse(propFile);

                Collection users = XmlUtilities.getCollection(propDoc.getDocumentElement());
                Iterator iter = users.iterator();
                while(iter.hasNext()){
                    Node singleuser = (Node)iter.next();
                    Map singleUserMap = XmlUtilities.getMap(singleuser);
                    // set user info
                    UserBean user = new UserBean();
                    user.setUserFirstName((String)singleUserMap.get("firstName"));
                    user.setUserLastName((String)singleUserMap.get("lastName"));
                    user.setUserLogin((String)singleUserMap.get("login"));
                    if(singleUserMap.get("rolePriority") != null){
                        user.setUserID(Long.parseLong((String)singleUserMap.get("id")));
                    }
                    if(singleUserMap.get("rolePriority") != null){
                        user.setRolePriority((String)singleUserMap.get("rolePriority"));
                    }
                    user.setRoleName((String)singleUserMap.get("roleName"));
                    user.setPassword((String)singleUserMap.get("password"));
                    user.setSuperiorID((String)singleUserMap.get("superiorId"));
                    if(singleUserMap.get("isAdmin") != null){
                        user.setAdmin(Boolean.valueOf((String)singleUserMap.get("isAdmin")).booleanValue());
                    }

                    // create user tree nodes
                    UserTreeNode userNode = new UserTreeNode(
                            user.getWholeName());
                    userNode.setUserInfo(user);
                    userNodes.add(userNode);
                    // add to user collection
                    userMap.put(user.getUserLogin(), user);
                }
            } catch (SAXException e1) {
                e1.printStackTrace();
            } catch (ParserConfigurationException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        getUserNameMap();
        getUserIDMap();
        // get user tree
        createUserTree(userNodes);
    }

    /**
     * This method creates a tree given a collection of nodes
     * @param userNodes- nodes containing user information
     */
    private static void createUserTree(Collection userNodes){
        Iterator iter = userNodes.iterator();
        while(iter.hasNext()){
            UserTreeNode userNode = (UserTreeNode)iter.next();
            // find root
            if(!userNode.getUserInfo().isAdmin() && userNode.getUserInfo().getSuperiorID() == null){
                userTree = userNode;
                addChildren(userNodes, userTree);
            }
        }
    }

    /**
     * This method recursively finds and adds all children of a node
     * @param userNodes - a set of child nodes
     * @param parent - the parent node
     * @return UserTreeNode - the parent node with children added
     */
    private static UserTreeNode addChildren(Collection userNodes, UserTreeNode parent){
        Iterator iter = userNodes.iterator();
        while(iter.hasNext()){
            UserTreeNode userNode = (UserTreeNode)iter.next();
            if(userNode != null && userNode.getUserInfo().getSuperiorID() != null && !userNode.getUserInfo().isAdmin()){
                if(userNode.getUserInfo().getSuperiorID().equalsIgnoreCase(Long.toString(parent.getUserInfo().getUserID()))){
                    addChildren(userNodes, userNode);
                    parent.add(userNode);
                    userNode.setParent(parent);
                }
            }
        }
        return parent;
    }

    public static void getUserNameMap(){
        userNameMap = new HashMap();
        if(userMap != null){
            Set keys = userMap.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                UserBean user =  (UserBean)userMap.get(iterator.next());
                if(!user.isAdmin()){
                    userNameMap.put(user.getWholeName(), user);
                }
            }
        }
    }

    public static void getUserIDMap(){
        userIDMap = new HashMap();
        if(userMap != null){
            Set keys = userMap.keySet();
            for (Iterator iterator = keys.iterator(); iterator.hasNext();) {
                UserBean user =  (UserBean)userMap.get(iterator.next());
                if(!user.isAdmin()){
                    userIDMap.put(Long.toString(user.getUserID()), user);
                }
            }
        }
    }
}

