package v1.controller;

import v1.Model.Data;
import v1.Model.Report;
import v1.exception.QueryException;
import v1.utility.Source;
import v1.utility.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Primary controller that handles the communication between sources and classifiers.
 * It gets the params from the API and passes them to the SourceController. Once retrieved
 * the Data, it creates the Multiclassifier (build as described in the config file) and pass them
 * to it. Finally, it get the final Report and return it to the API.
 * */
public class MainController {

    /**List of MultiClassifiers that will classify the Data
     * @see MultiClassifier*/
    private MultiClassifier multiClassifier;
    /**List of Data to classify
     * @see Data*/
    private List<Data> dataList = new ArrayList<>();
    /**List of the SourceController from where get the Data
     * @see SourceController*/
    private List<SourceController> sourceControllers = new ArrayList<>();
    /***/
    private static MainController mainController;

    /** Public constructor*/
    private MainController() {
        //Prendo i multiclassifier già belli che pronti dalla configurazione
        this.multiClassifier = Configuration.getMulticlassifier();

    }

    public void setParams(Map<String, String> params) throws QueryException {

        sourceControllers.clear();
        //Creo i controller per ogni sorgente
        for(String source : Configuration.getSources()){
            if(source.equals(Source.TWITTER.getName())){
                sourceControllers.add(new TwitterController(params));
            }else{
                throw new UnsupportedOperationException("Source not supported yet");
            }
        }

        //Prendo tutti i dati dalle varie sorgenti
        for(SourceController sourceController : sourceControllers){
            this.dataList.addAll(sourceController.getDataList());
        }
    }

    public static MainController getInstance(){
        if(mainController == null){
            mainController = new MainController();
        }
        return mainController;
    }

    /**Getter for the final Report from the classifiers
     * @return final report
     * @see Report*/
    public Report getReport(){

        multiClassifier.computeOpinion(this.dataList);

        this.dataList.clear();
        return multiClassifier.getReport();

    }


}
