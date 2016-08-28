package v1.classifiers;

import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;

import java.util.List;

/**
 * One implementation of MultiClassifier that uses weighted average to compute the final results for the classification.
 */
public class MultiClassifierImpl extends MultiClassifier{

    public MultiClassifierImpl(List<GenericClassifier> classifiers) throws Exception {
        super(classifiers);
    }

    @Override
    protected void computeFinalResultForData(Data data){

        Double numerator = 0.0;
        Double denominator = 0.0;
        //Rilevanza del dato passato (nel caso di Twitter quanto è visibile, ecc)
        Double relevance = null;
        try {
            relevance = this.evaluateRelevance(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ClassifierResult res : finalResultForClassifier){
            System.out.println("score: " + res.getScore() + ", polarità: " + (res.getPolarity().equals("positivo") ? 1 : -1) + ", peso: "  + res.getWeigth());
            numerator += res.getScore() * (res.getPolarity().equals("positivo") ? 1 : -1) * res.getWeigth();
            denominator += res.getWeigth();
        }

        if (denominator != 0) {

            Double score = numerator/denominator;

            System.out.println("Testo: " + data.getText() + " punteggio finale:" + score + " ( " + numerator + " diviso " + denominator +"\n\n");
            ClassifierResult finalResult = new ClassifierResult((score > 0 ? "positivo" : "negativo"), score, data.getText());
            finalResult.setRelevance(relevance);
            finalResultsForData.add(finalResult);
        }

        finalResultForClassifier.clear();
    }

    @Override
    protected void computeFinalResult() {

        // Formula per calcolare il risultato finale con la confidence (?)
        // Media pesata in base alla relevance dei dati
        Double num = 0.0;
        Double denum = 0.0;
        int totPositive = 0;
        int totNegative = 0;
        double maxNeg = 0;
        double maxPos = 0;
        String posExample = "";
        String negExample = "";

        for (ClassifierResult cl : finalResultsForData) {
            if(cl.getPolarity().equals("positivo")){
                totPositive++;
                if(maxPos < cl.getScore()){
                    maxPos = cl.getScore();
                    posExample = cl.getText();
                }
            } else {
                totNegative++;
                if(maxNeg > cl.getScore()){
                    maxNeg = cl.getScore();
                    negExample = cl.getText();
                }            }
            num += cl.getRelevance() * cl.getScore();
            denum += cl.getRelevance();
        }



        Double result = num/denum;

        this.report = new Report(result > 0 ? "Nel complesso Positivo" : "Nel complesso Negativo", totPositive, totNegative, posExample, negExample );

        finalResultsForData.clear();

    }
}
