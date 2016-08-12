package v1.controller;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import v1.Model.TrainingText;

import javax.persistence.Query;

/**
 * Class that handles the communication with the database, using Hibernate ORM framework (http://hibernate.org/orm/)
 * It's used to get and set training sets.
 * */
public class HibernateUtil {

    /**Session factory from where obtain new Session*/
    private static SessionFactory sessionFactory;

    /**Method that builds a new SessionFactory*/
    private static SessionFactory buildSessionFactory() {
        try {
            Configuration cfg = new Configuration();
            sessionFactory = cfg.configure().buildSessionFactory();

            return sessionFactory;
        }
        catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**Method that gives back a list of training text belonging to the given training set
     * @param trainingSetId id of the requested training set
     * @return list of training texts
     * @see TrainingText */
    public List<TrainingText> getTrainingSetText(int trainingSetId){
        buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("FROM TrainingText WHERE trainingSetId = " + trainingSetId);
        List<TrainingText> list = query.getResultList();
        t.commit();
        session.close();
        return list;
    }

    /**Method that allows to add a new Training Text to the database.
     * @param text text to add
     * @param polarity class of the given text
     * @param trainingSetId id of the set where the text will be added
     */
    public void setTrainingText(String text, String polarity, int trainingSetId){
        buildSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction t = session.beginTransaction();
        TrainingText trainingText = new TrainingText();
        trainingText.setText(text);
        trainingText.setPolarity(polarity);
        trainingText.setTrainingSetId(trainingSetId);
        session.save(trainingText);
        t.commit();
        session.close();
    }

}