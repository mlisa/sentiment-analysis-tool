package v1.controller;

import v1.Model.Data;
import v1.Model.Report;
import v1.utility.C;
import v1.utility.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by lisamazzini on 24/07/16.
 */
public class MainController {

    private List<MultiClassifier> multiClassifiers;
    private List<Data> dataList = new ArrayList<>();
    private List<SourceController> sourceControllers = new ArrayList<>();

    public MainController(Map<String, String> params) {

        //Prendo i multiclassifier già belli che pronti dalla configurazione
        this.multiClassifiers = Configuration.getMulticlassifiers();


        //Creo i controller per ogni sorgente
        for(String source : Configuration.getSources()){
            if(source.equals(C.TWITTER_SOURCE)){
                sourceControllers.add(new TwitterController(params));
            }else{
                throw new UnsupportedOperationException("Source not supported yet");
            }
        }

        //Prendo tutti i dati dalle varie sorgenti
        for(SourceController sourceController : sourceControllers){
            this.dataList.addAll(sourceController.getDataList());
        }

        //Passo i dati ottenuti a ciascun multiclassifier
        for(MultiClassifier multiClassifier : this.multiClassifiers){
            multiClassifier.computeOpinion(this.dataList);
        }

    }

    public Report getReport(){
        for(MultiClassifier m : this.multiClassifiers) {
            System.out.println("----------MULTICLASSIFICATORE----------");
            m.computeOpinion(this.dataList);
        }

        // TODO: gestione del report da più multiclassifier
        return multiClassifiers.get(1).getReport();

    }


}
