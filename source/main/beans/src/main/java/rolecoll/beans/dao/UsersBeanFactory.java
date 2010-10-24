package rolecoll.beans.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;
import org.hibernate.cfg.Configuration;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import rolecoll.beans.UserBean;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 16, 2007
 * Time: 4:35:09 PM
 */

/**
 * This class manages retrival from the User table
 */
public class UsersBeanFactory {
    /**
     * Retrive user by ID (SELECT * FROM Users WHERE USER_ID=XXX)
     * @param Id - User key
     * @return List of users
     * @throws Exception - database error
     */
    public static List getUserByID(long Id)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        String query = "from UserBean";
        List userList = new ArrayList();
        List allUsers = session.createQuery(query).list();
        for (Iterator i = allUsers.iterator(); i.hasNext();) {
            UserBean tempUser = (UserBean) i.next();
            if(tempUser != null){
                if(tempUser.getUserID() == Id){
                    userList.add(tempUser);
                }
            }
        }
        session.close();

        return userList;
    }

    /**
     * Retrive map of users mapped by login (SELECT * FROM Users)
     * @return Map of users login->user
     * @throws Exception - database error
     */
    public static Map getUserMap() throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        String query = "from UserBean";
        Map userMap = new HashMap();
        List allUsers = session.createQuery(query).list();
        for (Iterator i = allUsers.iterator(); i.hasNext();) {
            UserBean tempUser = (UserBean) i.next();
            if(tempUser != null){
                userMap.put(tempUser.getUserLogin(), tempUser);                
            }
        }
        session.close();

        return userMap;
    }

    /**
     * Retrive all non-admin user IDs
     * @return Array of IDs
     * @throws Exception - database error
     */
    public static Object[] getUserIDs() throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        String query = "select userID from UserBean where admin=false";
        List userIDs = session.createQuery(query).list();
        session.close();

        return userIDs.toArray();
    }
}
