package rolecoll.beans.dao;

import rolecoll.beans.UserBean;
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 17, 2007
 * Time: 7:04:13 PM
 */

/**
 * This class manages inserts, updates, and deletes to the User table
 */
public class UserBeanController {
    /**
     * Insert user into table
     * @param user - user information
     * @return successful boolean
     */
    public static boolean addUser(UserBean user) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null && tx.isActive())
                tx.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    /**
     * Delete user from table
     * @param user - user information
     * @return successful boolean
     */
    public static boolean removeUser(UserBean user) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null && tx.isActive())
                tx.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }

    /**
     * Update user in table 
     * @param user - user information
     * @return successful boolean
     */
    public static boolean updateUser(UserBean user) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (tx != null && tx.isActive())
                tx.rollback();
            return false;
        } finally {
            session.close();
        }
        return true;
    }
}
