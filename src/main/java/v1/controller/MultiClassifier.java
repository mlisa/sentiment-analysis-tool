package v1.controller;


import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class representing a Multiclassifier. It uses more GenericClassifier in order to get a single classification.
 * Methods that implement the main logic for the final result are left abstract, so that there can be more implementations
 * with different algorithms.
 */

public abstract class MultiClassifier {

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

    //Report finale
    /**Final Report
     * @see Report*/
    protected Report report;

    /**Public constructor
     * @param classifiers list of GenericClassifiers used to compute the result*/
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
                //Aggiungo il peso che deve avere (in base al classificatore che l'ha prodotto)
                result.setWeigth(c.getWeight());
                System.out.println("Testo: " + d.getText() + " polarità: " + result.getPolarity() + " con score: " + result.getScore() + " e ha peso: " + result.getWeigth());

                //Lo aggiungo alla lista
                finalResultForClassifier.add(result);
            }
            this.computeFinalResultForData(d);
        }

        this.computeFinalResult();

    }

    /**Method that computes the relevance of the Data, based of some parameters of visibility
     * @param data Data to evaluate
     * @return a numeric value that represents the relevance
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
