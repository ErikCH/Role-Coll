package rolecoll.beans.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 22, 2007
 * Time: 11:55:35 PM
 */

/**
 * This class is in charge of retriving all of the data from the Notes table
 */
public class NoteBeanFactory {
    /**
     * Retrive all notes for a document (SELECT * FROM Notes WHERE DOC_ID=XXX)
     * @param docID - document key
     * @return Collection of notes
     * @throws Exception - database error
     */
     public static Collection getAllNotesForDocument(long docID)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from NoteBean where documentID=" + docID;
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Retrive all notes for a document and a user (SELECT * FROM Notes WHERE DOC_ID=XXX AND USER_ID=XXX)
     * @param docID - document key
     * @param userID - user key
     * @return Collection of notes
     * @throws Exception - database error
     */
    public static Collection getAllNotesForDocument(long docID, long userID)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from NoteBean where documentID=" + docID + " and userID=" + userID;
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Retrive all notes for a document and a role (SELECT * FROM Notes WHERE DOC_ID=XXX AND ROLE=XXX)
     * @param docID - document key
     * @param priority - role number
     * @return Collection of notes
     * @throws Exception - database error
     */
    public static Collection getAllNotesWithPriorityEqual(long docID, String priority)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from NoteBean where documentID=" + docID + " and userPriority='" + priority + "'";
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Retrive all notes for a document and all roles higher (SELECT * FROM Notes WHERE DOC_ID=XXX AND Role=>XXX)
     * @param docID - document key
     * @param priority - role number
     * @return Collection of notes
     * @throws Exception - database error
     */
    public static Collection getAllNotesWithPriorityGreaterThan(long docID, String priority)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from NoteBean where documentID=" + docID + " and userPriority<=" + priority;
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Retrive all notes for a document and all roles lower (SELECT * FROM Notes WHERE DOC_ID=XXX AND Role=<XXX)
     * @param docID - document key
     * @param priority - role number
     * @return Collection of notes
     * @throws Exception - database error
     */
    public static Collection getAllNotesWithPriorityLessThan(long docID, String priority)throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from NoteBean where documentID=" + docID + " and userPriority>=" + priority;
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }
}
