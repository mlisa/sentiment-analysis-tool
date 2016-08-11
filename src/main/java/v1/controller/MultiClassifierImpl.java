package v1.controller;

import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;

import java.util.List;

/**
 * Created by lisamazzini on 05/08/16.
 */
public class MultiClassifierImpl extends MultiClassifier{

    public MultiClassifierImpl(List<GenericClassifier> classifiers) throws Exception {
        super(classifiers);
    }

    @Override
    protected void computeFinalResultForData(Data t){

        Double numerator = 0.0;
        Double denominator = 0.0;
        //Rilevanza del dato passato (nel caso di Twitter quanto è visibile, ecc)
        Double relevance = null;
        try {
            relevance = this.evaluateRelevance(t);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ClassifierResult res : finalResultForClassifier){
            numerator += res.getScore() * (res.getPolarity().equals("positivo") ? 1 : -1) * res.getWeigth();
            denominator += res.getWeigth();
        }

        Double score = numerator/denominator;

        System.out.println("Testo: " + t.getText() + "punteggio finale: " + numerator + " diviso " + denominator + " = " + score);
        ClassifierResult finalResult = new ClassifierResult((score > 0 ? "positivo" : "negativo"), score);
        finalResult.setRelevance(relevance);
        finalResultsForData.add(finalResult);
        finalResultForClassifier.clear();
    }

    @Override
    protected void computeFinalResult() {

        // Formula per calcolare il risultato finale con la confidence (?)
        // Media pesata in base alla relevance dei dati
        Double num = 0.0;
        Double denum = 0.0;

        for (ClassifierResult cl : finalResultsForData) {
            num += cl.getRelevance() * cl.getScore();
            denum += cl.getRelevance();
        }

        Double result = num/denum;

        this.report = new Report(result > 0 ? "Nel complesso Positivo" : "Nel complesso Negativo", result);


    }
}
