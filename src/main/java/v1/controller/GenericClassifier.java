package v1.controller;

import v1.Model.ClassifierResult;

/**
 * Created by lisamazzini on 03/08/16.
 */
public interface GenericClassifier {

    ClassifierResult classify(String text);

    Double getWeight();

}
