package rolecoll.beans.dao;

import org.hibernate.SessionFactory;
import org.hibernate.Session;

import java.util.List;
import java.util.Collection;

/**
 * Created by IntelliJ IDEA.
 * User: znorcross
 * Date: Apr 22, 2007
 * Time: 11:54:48 PM
 */

/**
 * This class is incharge of retriving data from the Document table
 */
public class DocumentBeanFactory {
    /**
     * Get all documents from the table (Select *)
     * @return collection of documents
     * @throws Exception - database error
     */
    public static Collection getAllDocuments()throws Exception {
        // get a database connection
        SessionFactory sf = HibernateUtil.getSessionFactory();
        Session session = sf.openSession();
        try{
            String query = "from DocumentBean";
            return session.createQuery(query).list();
        } catch(Exception e){
            throw e;
        } finally {
            session.close();
        }
    }
}
