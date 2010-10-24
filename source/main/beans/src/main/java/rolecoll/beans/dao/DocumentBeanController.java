package rolecoll.beans.dao;

import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import rolecoll.beans.DocumentBean;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 22, 2007
 * Time: 11:55:05 PM
 */

/**
 * This class is in charge of inserts, updates, and deletes on the Document table
 */
public class DocumentBeanController {
    /**
     * Commit document to the database
     * @param document - Document information
     * @return successful boolean
     */
    public static boolean addDocument(DocumentBean document) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.save(document);
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
     * Remove given document from the document table
     * @param document - Document information
     * @return successful boolean
     */
    public static boolean removeDocument(DocumentBean document) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.delete(document);
            tx.commit();
        } catch (HibernateException e) {
            // if fail rollback
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
     * Update given document
     * @param document - Document information
     * @return successful boolean
     */
    public static boolean updateDocument(DocumentBean document) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.update(document);
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
