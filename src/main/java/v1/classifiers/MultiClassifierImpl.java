package v1.classifiers;

import v1.model.ClassifierResult;
import v1.model.Data;
import v1.model.Report;
import v1.model.TestData;
import v1.utility.Configuration;

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
        Double neutral = 0.0;
        Double denominator = 0.0;
        Double relevance = null;
        ClassifierResult finalResult;
        try {
            relevance = this.evaluateRelevance(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (ClassifierResult res : finalResultForClassifier){
            if(!res.getPolarity().equals("neutro")) {
                numerator += res.getScore() * (res.getPolarity().equals("positivo") ? 1 : -1) * res.getWeigth();
            }
            denominator += res.getWeigth();
        }

        if (denominator != 0) {

            //Se ci son stati dei neutri, li sommo o sottraggo al risultato in base al segno finale, per non sbilanciare i risultati
            Double score = numerator/denominator;

            //Se il risultato finale supera la soglia minima di certezza (sia in positivo che in negativo)
            if (Math.abs(score) >= 0.50){
                finalResult = new ClassifierResult((score > 0 ? "positivo" : "negativo"), score, data.getText());
            } else {
                finalResult = new ClassifierResult("neutro" , 0.0 , data.getText());
            }
            //System.out.println("Testo: " + data.getText() + " punteggio finale:" + score + " ( " + numerator + " diviso " + denominator +"");

            finalResult.setRelevance(relevance);

            finalResultsForData.add(finalResult);

            if(Configuration.isTest()){
                TestData testData = (TestData)data;
                //System.out.println("Calcolato :" + finalResult.getPolarity() + " polarità reale : " + testData.getPolarity() + "\n");
                if(testData.getPolarity().equals(finalResult.getPolarity())){
                    countGiuste++;
                }
            }


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
        int totNeutral = 0;
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
            } else if(cl.getPolarity().equals("negativo")) {
                totNegative++;
                if(maxNeg > cl.getScore()){
                    maxNeg = cl.getScore();
                    negExample = cl.getText();
                }
            } else {
                totNeutral++;
            }
            num += cl.getRelevance() * cl.getScore();
            denum += cl.getRelevance();
        }

        Double result = num/denum;

        if(Math.abs(result) > 0.4 ) {
            this.report = new Report(result > 0 ? "Complessivamente l'opinione è positiva" : "Complessivamente l'opinione è negativa", totPositive, totNegative, totNeutral, posExample, negExample);
        } else {
            this.report = new Report("Complessivamente l'opinione è neutrale", totPositive, totNegative, totNeutral, posExample, negExample);
        }
        finalResultsForData.clear();

    }
}
