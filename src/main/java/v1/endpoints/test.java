package v1.endpoints;

import org.springframework.web.bind.annotation.*;
import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;
import v1.Model.TestText;
import v1.controller.*;

import java.util.*;

/**
 * Class that uses Spring framework create API for the client to pass the query params and get back the response
 */
@CrossOrigin(origins = api.CORS)
@RestController
public class test {

    protected static final String CORS = "http://127.0.0.1:8089";


    /**Method that maps the address with '/test'*/
    @RequestMapping("/test")
    public Report test() {

        HibernateUtil h = new HibernateUtil();
        List<TestText> test = h.getTestSetList(0);
        test.addAll(h.getTestSetList(1));
        GenericClassifier classifierBayesNRC = new ClassifierBayes(1.0, 4, 0.7);
        GenericClassifier classifierBayesReal = new ClassifierBayes(1.0, 5, 0.65);
        GenericClassifier classifierBayesSuper = new ClassifierBayes(1.0, 6, 0.65);
        int countN = 0;
        int countR = 0;
        int countS = 0;
        int countN2 = 0;
        int countR2 = 0;
        int countS2 = 0;
        int totN = 0;
        int totR = 0;
        int totS = 0;
        int countGiuste = 0;
        for(TestText t : test){
            ClassifierResult resNRC =  classifierBayesNRC.classify(t.getText());
            ClassifierResult resReal =  classifierBayesReal.classify(t.getText());
            ClassifierResult resSuper =  classifierBayesSuper.classify(t.getText());

            if(resNRC.getPolarity().equals(t.getPolarity())) countN++;
            if(resReal.getPolarity().equals(t.getPolarity())) countR++;
            if(resSuper.getPolarity().equals(t.getPolarity())) countS++;

            if(resNRC.getScore() > 0.60){
                totN++;
                if(resNRC.getPolarity().equals(t.getPolarity())){
                countN2++;
                }
            }

            if(resReal.getScore() > 0.60){
                totR++;
                if(resReal.getPolarity().equals(t.getPolarity())){
                    countR2++;
                }
            }

            if(resSuper.getScore() > 0.60) {
                totS++;
                if (resSuper.getPolarity().equals(t.getPolarity())) {
                    countS2++;
                }
            }
            double fin = (resNRC.getScore()*(resNRC.getPolarity().equals("negativo") ? -1 : 1)*0.5 + resReal.getScore()*(resReal.getPolarity().equals("negativo") ? -1 : 1)*0.25 + resSuper.getScore()*(resSuper.getPolarity().equals("negativo") ? -1 : 1)*0.25);
            //System.out.println("RISULTATO FINALE PER " + t.getText() + " polarità : " + (fin>0 ? " positivo " : " negativo "));

            if (t.getPolarity().equals(fin>0 ? "positivo" : "negativo")){
                countGiuste++;
            }
            //System.out.println(t.getText() + " \n NRC --> " + resNRC.getPolarity() + " con score: " + resNRC.getScore() + "\n");
            //System.out.println("Frasi reali --> " + resReal.getPolarity() + " con score: " + resReal.getScore() + "\n");
            //System.out.println(" Superlativi --> " + resSuper.getPolarity() + " con score: " + resSuper.getScore() + "\n");
        }

        System.out.println("NRC : giuste " + countN + " in percentuale: " + countN*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("NRC CON REGOLA : giuste " + countN2 + " in percentuale: " + countN2*100.0/totN + " su un tot di " + totN + "\n");

        System.out.println("Quelle giuste sono " + countGiuste);
        System.out.println("Reali : giuste " + countR + " in percentuale: " + countR*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("Reali CON REGOLA : giuste " + countR2 + " in percentuale: " + countR2*100.0/totR + " su un tot di " + totR + "\n");

        System.out.println("Superlativi : giuste " + countS + " in percentuale: " + countS*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("Superlativi CON REGOLA : giuste " + countS2 + " in percentuale: " + countS2*100.0/totS + " su un tot di " + totS + "\n");


        return null;
    }

}
