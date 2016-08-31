package v1.classifiers;


import v1.model.ClassifierResult;
import v1.model.Data;
import v1.model.Report;
import v1.model.TrainingSet;
import v1.utility.Logic;
import v1.utility.SemanticAnalyzer;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a Multiclassifier. It uses more GenericClassifier in order to get a single classification.
 * Methods that implement the main logic for the final result are left abstract, so that there can be more implementations
 * with different algorithms.
 */

public abstract class MultiClassifier implements GenericMultiClassifier{

    private static final String POSITIVE = "positivo";
    private static final String NEGATIVE = "negativo";

    /**List of the single GenericClassifiers used in the MultiClassifier
     * @see GenericClassifier*/
    private List<GenericClassifier> simpleClassifiers = new ArrayList<>();

    /**List with the results for the same Data computed by all the GenericClassifiers
     * @see ClassifierResult*/
    //Lista in cui ci sono i risultati di tutti i classifier relativi a un singolo tweet
    protected List<ClassifierResult> finalResultForClassifier = new ArrayList<>();

    /**List with the results for all the Data passed in input
     * @see ClassifierResult*/
    //Lista in cui ci sono i risultati di tutti i tweet passati in input
    protected List<ClassifierResult> finalResultsForData = new ArrayList<>();

    protected int countGiuste = 0;

    //Report finale
    /**Final Report
     * @see Report*/
    protected Report report;

    /**Public constructor
     * @param classifiers list of GenericClassifiers used to compute the result
     * @throws Exception if in the configuration are specified unsupported type of classifier*/
    public MultiClassifier(List<GenericClassifier> classifiers) throws Exception {
        this.simpleClassifiers = classifiers;
    }

    /**Getter for the Report
     * @return final report
     * @see Report*/
    public Report getReport() {
        return report;
    }

    /** Main method that classifies the Data. It passes each Data to each GenericClassifiers, in order to compute
     * one final classification.
     * @param data list of Data object to classify
     * @see Data*/
    public void computeOpinion(List<Data> data){

        //Ciascun testo in input
        for(Data d : data){

            //lo passo a ciascun simpleClassifier
            for(GenericClassifier c : simpleClassifiers){
                //Classificazione del testo puro
                ClassifierResult result = c.classify(d.getText());
                //Se il punteggio supera la soglia stabilita
                if(result.getScore() >= c.getMinConfidence()) {
                    //Aggiungo il peso che deve avere (in base al classificatore che l'ha prodotto)

                    result.setWeigth(c.getWeight());
                    result.setWeigth(result.getWeigth()+ SemanticAnalyzer.getAdverbScore(d));



                    if(result.getPolarity().equals(POSITIVE)){
                        result.setWeigth(result.getWeigth() + SemanticAnalyzer.getPositiveEmoticonScore(d) - SemanticAnalyzer.getNegativeEmoticonScore(d));
                    }else{
                        result.setWeigth(result.getWeigth() - SemanticAnalyzer.getPositiveEmoticonScore(d) + SemanticAnalyzer.getNegativeEmoticonScore(d));
                    }

                    if (!(c.getTrainingSet().getId() == TrainingSet.SUPERLATIVES.getId() && c.getWeight() * SemanticAnalyzer.getSuperlativeScore(d) == 0)) {
                        finalResultForClassifier.add(result);
                    }

                }
            }
            this.computeFinalResultForData(d);
        }
        System.out.println("Giuste: " + countGiuste + " su totale " + finalResultsForData.size());
        this.computeFinalResult();

    }

    /**Method that computes the relevance of the Data, based of some parameters of visibility
     * @param data Data to evaluate
     * @return a numeric value that represents the relevance
     * @throws Exception if Data is from an unsupported Source
     * @see Data*/
    protected Double evaluateRelevance(Data data) throws Exception {
        return Logic.computeRelevance(data);
    }

    /**Method that merge all the results for a single Data computed by the GenericClassifiers
     * @param data to evaluate
     * @see Data*/
    protected abstract void computeFinalResultForData(Data data);

    /**Method that create the final report of the Multiclassifier*/
    protected abstract void computeFinalResult();
}
