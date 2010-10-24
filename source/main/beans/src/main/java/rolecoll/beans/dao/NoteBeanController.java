package rolecoll.beans.dao;

import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.Session;
import org.hibernate.HibernateException;
import rolecoll.beans.NoteBean;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 22, 2007
 * Time: 11:55:47 PM
 */

/**
 * This classe manages all inserts, updates, and deletes to the Note table
 */
public class NoteBeanController {
    /**
     * insert note into the table
     * @param note - note information
     * @return successful boolean
     */
    public static boolean addNote(NoteBean note) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.save(note);
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
     * Delete note from table
     * @param note - note information
     * @return successful boolean
     */
    public static boolean removeNote(NoteBean note) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.delete(note);
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
     * Update note in table
     * @param note - note information
     * @return successful boolean
     */
    public static boolean updateNote(NoteBean note) {
        Transaction tx = null;
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try {
            tx = session.beginTransaction();
            session.update(note);
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
