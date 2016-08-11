package v1.controller;


import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe astratta che rappresenta un nuovo multiclassificatore; Essa prende in input un array di pesi che indicano
 * quanto "valore" hanno i singoli classificatori (devono essere tanti quanti i tipi di classificatori che
 * verranno creati (ovvero a quanti training set diversi sono istanziati).
 * I metodi astratti sono da implementare in base alle necessità del Multiclassificatore (ovvero in base al
 * tipo di dati in input e al modo in cui si vuole calcolare il risultato finale.
 *
 */

public abstract class MultiClassifier {

    private List<GenericClassifier> simpleClassifiers = new ArrayList<>();

    //Lista in cui ci sono i risultati di tutti i classifier relativi a un singolo tweet
    protected List<ClassifierResult> finalResultForClassifier = new ArrayList<>();

    //Lista in cui ci sono i risultati di tutti i tweet passati in input
    protected List<ClassifierResult> finalResultsForData = new ArrayList<>();

    //Report finale
    protected Report report;

    public MultiClassifier(List<GenericClassifier> classifiers) throws Exception {
        this.simpleClassifiers = classifiers;
    }

    public Report getReport() {
        return report;
    }


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

    protected Double evaluateRelevance(Data data) throws Exception {
        return Logic.computeRelevance(data);
    }

    protected abstract void computeFinalResultForData(Data t);

    protected abstract void computeFinalResult();
}
