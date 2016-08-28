package v1.classifiers;

import v1.Model.ClassifierResult;
import v1.Model.TrainingSet;

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

    /**Setter for the weight
     * @param weight new weight to set*/
    void setWeight(Double weight);

    /**Getter for the minimum confidence established
     * @return minimum confidence */
    Double getMinConfidence();

    /**Getter for the TrainingSet value of the classifier
     * @return training set
     * @see TrainingSet */
    TrainingSet getTrainingSet();


    }
