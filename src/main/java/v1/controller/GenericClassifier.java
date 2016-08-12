package v1.controller;

import v1.Model.ClassifierResult;

/**
 * Interface that represents a GenericClassifier, that will be used inside a MultiClassifier object to classify Data.
 */
public interface GenericClassifier {

    /**Method that classify a String.
     * @param text String to classify
     * @return ClassifierResult with the class assigned and the score given
     * @see ClassifierResult*/
    ClassifierResult classify(String text);

    /**Getter for the weight given to the GenericClassifier inside a particular MultiClassifier
     * @return weight */
    Double getWeight();

}
