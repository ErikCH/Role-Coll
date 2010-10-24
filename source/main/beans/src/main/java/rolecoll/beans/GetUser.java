/*
 * GetUser.java
 *
 * Created on December 10, 2006, 6:05 PM
 */

package rolecoll.beans;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class will retreive user data from the database
 */
public class GetUser {
    private static final String SELECT_SINGLE = "SELECT * FROM USERS" +            
            " WHERE id=%id%";
    private static final String SELECT_ALL = "SELECT * FROM USERS" +            
            " WHERE id=%id%";
    
    /** Creates a new instance of GetUser */
    public GetUser() {
    }
    
    /**
     * This method returns a collection of user beans populated with all 
     * of the user data in the database
     * @return Collection - collection of user beans
     */
    public static Collection selectAllUsers(){
        return new ArrayList();
    }
    
    /**
     * This method returns a user bean given a user id
     * @param id - users id number
     * @return UserBean - user data from the database
     */
    public static UserBean selectUser(String id){
        // todo    
        return new UserBean();
    }
}
