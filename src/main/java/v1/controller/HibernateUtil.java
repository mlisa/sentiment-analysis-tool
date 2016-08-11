package v1.controller;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import v1.Model.TrainingText;

import javax.persistence.Query;

public class HibernateUtil {

    //XML based configuration
    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {
            // Create the SessionFactory from hibernate.cfg.xml
            Configuration cfg = new Configuration();
            sessionFactory = cfg.configure().buildSessionFactory();

            return sessionFactory;
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }


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