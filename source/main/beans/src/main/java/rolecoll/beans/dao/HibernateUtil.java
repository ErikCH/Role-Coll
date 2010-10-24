package rolecoll.beans.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 17, 2007
 * Time: 8:42:08 AM
 */

/**
 * This class manages the hibernate connection pool via the session factory
 */
public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try{
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch(Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }

    public static void shutdown(){      
        getSessionFactory().close();
    }
}
