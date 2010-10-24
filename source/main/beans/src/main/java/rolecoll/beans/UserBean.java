/*
 * UserBean.java
 *
 * Created on November 20, 2006, 10:27 PM
 */

package rolecoll.beans;

/**
 * This class contains all of the user information in an object
 */

/**
 * POJO for rows in the Users table
 */
public class UserBean {
 
    private String userFirstName = null;
    private String userLastName = null;
    private String userLogin = null;
    private long userID;
    private String rolePriority = null;
    private String roleName = null;
    private String superiorID = null;
    private String password = null;
    private boolean admin = false;
    
    /** Creates a new instance of userBean */
    public UserBean() {
    }

    /** @return String password */
    public String getPassword() {
        return password;
    }

    /** @return String role name */
    public String getRoleName() {
        return roleName;
    }

    /** @return int role priority */
    public String getRolePriority() {
        return rolePriority;
    }

    /** @return String superior id */
    public String getSuperiorID() {
        return superiorID;
    }

    /** @return String user id */
    public long getUserID() {
        return userID;
    }

    /** @return String first name */
    public String getUserFirstName() {
        return userFirstName;
    }

    /** @return String last name */
    public String getUserLastName() {
        return userLastName;
    }

    /** @return String user login */
    public String getUserLogin() {
        return userLogin;
    }
  
    /** @param password */
    public void setPassword(String password) {
        this.password = password;
    }

    /** @param role name */
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    /** @param role priority */
    public void setRolePriority(String rolePriority) {
            this.rolePriority = rolePriority;        
    }

    /** @param superior id */
    public void setSuperiorID(String superiorID) {
        this.superiorID = superiorID;
    }

    /** @param user id */
    public void setUserID(long userID) {
        this.userID = userID;
    }

    /** @param first name */
    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    /** @param last name */
    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    /** @param login */
    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    /** @return boolean */
    public boolean isAdmin() {
        return admin;
    }

    /** @param admin */
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getWholeName(){
        return this.userFirstName + " " + userLastName;
    }

    public Object clone() throws CloneNotSupportedException {
        super.clone();
        UserBean user = new UserBean();
        user.userFirstName = this.userFirstName;
        user.userLastName = this.userLastName;
        user.userLogin = this.userLogin;
        user.userID = this.userID;
        user.rolePriority = this.rolePriority;
        user.roleName = this.roleName;
        user.superiorID = this.superiorID;
        user.password = this.password;
        user.admin = this.admin;
        return user;
    }
}
