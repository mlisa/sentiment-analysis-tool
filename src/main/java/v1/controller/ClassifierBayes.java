package v1.controller;

import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.util.BytesRef;
import v1.Model.ClassifierResult;

import java.io.IOException;

/**
 * GenericClassifier that implement Naive Bayes to classify Data; it uses in particular the tools given by Apache Lucene lib (https://lucene.apache.org/core/5_3_1/)
 *
 */
public class ClassifierBayes implements GenericClassifier{

    /**Weight of the Classifier inside a particular MultiClassifier*/
    protected Double weight;
    /**IndexController to handle the Index required by Lucene Classifier*/
    protected IndexController indexController;
    /**TODO:doc */
    protected Double minConfidence;

    /**Public constructor
     * @param weight weight of Classifier
     * @param trainingSetId id of the training set that will be used to train the classifier*/
    public ClassifierBayes(Double weight, Integer trainingSetId, Double minConfidence) {
        this.weight = weight;
        this.indexController = new IndexController(trainingSetId);
        this.indexController.populateIndex();
        this.minConfidence = minConfidence;
    }

    public ClassifierResult classify(String text){

        SimpleNaiveBayesClassifier classifier;
        try {
            IndexReader reader = DirectoryReader.open(indexController.getDirectory());
            LeafReader leafReader = SlowCompositeReaderWrapper.wrap(reader);
            classifier = new SimpleNaiveBayesClassifier();
            classifier.train(leafReader, indexController.getFieldText(), indexController.getFieldClass(), indexController.getAnalyzer());

            ClassificationResult<BytesRef> result = classifier.assignClass(text);
            String assigned = result.getAssignedClass().utf8ToString();
            double score = result.getScore();

            return new ClassifierResult(assigned, score);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public String toString() {
        return "ClassifierBayes{" +
                "weight=" + weight +
                ", indexController=" + indexController +
                '}';
    }

    public Double getWeight() {
        return weight;
    }

    public Double getMinConfidence() {
        return minConfidence;
    }
}
