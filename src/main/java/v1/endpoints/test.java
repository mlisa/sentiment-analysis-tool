package v1.endpoints;

import org.springframework.web.bind.annotation.*;
import v1.Model.ClassifierResult;
import v1.Model.Report;
import v1.Model.TestText;
import v1.controller.*;

import java.util.*;

/**
 * Class that uses Spring framework create API for the client to pass the query params and get back the response.
 */
@CrossOrigin(origins = api.CORS)
@RestController
public class test {

    protected static final String CORS = "http://127.0.0.1:8089";


    /**Method that maps the address with '/test'*/
    @RequestMapping("/test")
    public Report test() {

        HibernateUtil h = new HibernateUtil();
        List<TestText> test = h.getTestSetList(2);
        test.addAll(h.getTestSetList(1));
        test.addAll(h.getTestSetList(0));

        double pesoNRC = 0.80;
        double pesoReali = 0.20;
        double pesoSuperlativi = 0.15;

        GenericClassifier classifierBayesNRC = new ClassifierBayes(1.0, 4, pesoNRC);
        GenericClassifier classifierBayesReal = new ClassifierBayes(1.0, 5, pesoReali);
        GenericClassifier classifierBayesSuper = new ClassifierBayes(1.0, 6, pesoSuperlativi);
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
        int countGiuste2 = 0;
        int tot = 0;
        for(TestText t : test){
            boolean nrcTrue = false;
            boolean realTrue = false;
            boolean superTrue = false;
            double denum = 0;
            double fin2 = 0;
            ClassifierResult resNRC =  classifierBayesNRC.classify(t.getText());
            ClassifierResult resReal =  classifierBayesReal.classify(t.getText());
            ClassifierResult resSuper =  classifierBayesSuper.classify(t.getText());

            if(resNRC.getPolarity().equals(t.getPolarity())) countN++;
            if(resReal.getPolarity().equals(t.getPolarity())) countR++;
            if(resSuper.getPolarity().equals(t.getPolarity())) countS++;

            if(resNRC.getScore() >= 0.65){
                totN++;
                nrcTrue = true;
                if(resNRC.getPolarity().equals(t.getPolarity())){
                    countN2++;
                }
            }

            if(resReal.getScore() >= 0.65){
                totR++;
                realTrue = true;
                if(resReal.getPolarity().equals(t.getPolarity())){
                    countR2++;
                }
            }

            if(resSuper.getScore() >= 0.65 && SemanticAnalyzer.getSuperlativeScore(t.getText())!= 0) {
                totS++;
                superTrue = true;
                if (resSuper.getPolarity().equals(t.getPolarity())) {
                    countS2++;
                }
            }
            double fin = (resNRC.getScore()*(resNRC.getPolarity().equals("negativo") ? -1 : 1) * pesoNRC + resReal.getScore()*(resReal.getPolarity().equals("negativo") ? -1 : 1)*pesoReali + resSuper.getScore()*(resSuper.getPolarity().equals("negativo") ? -1 : 1)*pesoSuperlativi);
            //System.out.println("RISULTATO FINALE PER " + t.getText() + " polarità : " + (fin>0 ? " positivo " : " negativo "));

            if(nrcTrue){
                fin2 += (resNRC.getScore()*(resNRC.getPolarity().equals("negativo") ? -1 : 1) * pesoNRC);
                denum += pesoNRC;
            }
            /*if(realTrue){
                fin2 += (resReal.getScore()*(resReal.getPolarity().equals("negativo") ? -1 : 1) * pesoReali) ;
                denum += pesoReali;
            }*/
            if(superTrue){
                fin2 += (resSuper.getScore()*(resSuper.getPolarity().equals("negativo") ? -1 : 1) * pesoSuperlativi);
                denum += pesoSuperlativi;
            }

            if (t.getPolarity().equals(fin > 0 ? "positivo" : "negativo")){
                countGiuste++;
            }

            if(denum!=0){
                tot++;
            }

            if (denum != 0 && t.getPolarity().equals((fin2/denum) > 0 ? "positivo" : "negativo")){
                countGiuste2++;
            }
            //System.out.println(t.getText() + " \n NRC --> " + resNRC.getPolarity() + " con score: " + resNRC.getScore() + "\n");
            //System.out.println("Frasi reali --> " + resReal.getPolarity() + " con score: " + resReal.getScore() + "\n");
            //System.out.println(" Superlativi --> " + resSuper.getPolarity() + " con score: " + resSuper.getScore() + "\n");
        }

        //System.out.println("NRC : giuste " + countN + " in percentuale: " + countN*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("NRC CON REGOLA : giuste " + countN2 + " su " + totN  + " in percentuale: " + countN2*100.0/totN + "\n");

        //System.out.println("Reali : giuste " + countR + " in percentuale: " + countR*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("Reali CON REGOLA : giuste " + countR2 + " su " + totR  + " in percentuale: " + countR2*100.0/totR +  "\n");

        //System.out.println("Superlativi : giuste " + countS + " in percentuale: " + countS*100.0/test.size() + " su un totale di" + test.size() +" \n");
        System.out.println("Superlativi CON REGOLA : giuste " + countS2 + " su " + totS  + " in percentuale: " + countS2*100.0/totS + "\n");


        System.out.println("\n SENZA REGOLA APPLICATA Quelle calcolate globalmente giuste sono " + countGiuste + " in percentuale " + countGiuste*100.0/161);
        System.out.println("\n CON REGOLA APPLICATA Quelle calcolate globalmente giuste sono " + countGiuste2 + " su " + tot + " in percentuale " + countGiuste2*100.0/tot);

        return null;
    }

}
