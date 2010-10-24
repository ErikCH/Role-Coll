/*
 * UserGroupBean.java
 */

package rolecoll.beans;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * Extention of a the DefaultMutableTreeNode class
 * A UserBean was added containing the users information
 */
public class UserTreeNode extends DefaultMutableTreeNode {

    /** User information */
    protected UserBean userInfo;

     /**
     * Creates a tree node that has no parent and no children, but which
     * allows children.
     */
    public UserTreeNode() {
	this(null);
    }

    /**
     * Creates a tree node with no parent, no children, but which allows 
     * children, and initializes it with the specified user object.
     * 
     * @param userObject an Object provided by the user that constitutes
     *                   the node's data
     */
    public UserTreeNode(Object userObject) {
	this(userObject, true);
    }

    /**
     * Creates a tree node with no parent, no children, initialized with
     * the specified user object, and that allows children only if
     * specified.
     * 
     * @param userObject an Object provided by the user that constitutes
     *        the node's data
     * @param allowsChildren if true, the node is allowed to have child
     *        nodes -- otherwise, it is always a leaf node
     */
    public UserTreeNode(Object userObject, boolean allowsChildren) {
	super();
	parent = null;
	this.allowsChildren = allowsChildren;
	this.userObject = userObject;
    }   
  
    /**
     * @return UserBean - Nodes user information
     */
    public UserBean getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo - UserBean containing all of the user information
     */
    public void setUserInfo(UserBean userInfo) {
        this.userInfo = userInfo;
    }
}
