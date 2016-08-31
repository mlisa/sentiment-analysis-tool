package v1.classifiers;

import v1.model.Data;
import v1.model.Report;

import java.util.List;

/**
 * Created by lisamazzini on 29/08/16.
 */
public interface GenericMultiClassifier {

    /**Getter for the Report
     * @return final report
     * @see Report*/
    Report getReport();


    /** Main method that classifies the Data. It passes each Data to each GenericClassifiers, in order to compute
     * one final classification.
     * @param data list of Data object to classify
     * @see Data*/
    void computeOpinion(List<Data> data);

}
