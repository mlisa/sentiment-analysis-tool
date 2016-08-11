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
 * Created by lisamazzini on 26/07/16
 */
public class ClassifierBayes implements GenericClassifier{

    private Double weight;
    private IndexController indexController;

    private int trainingSetId;

    public ClassifierBayes(double weight, int trainingSetId) {
        this.weight = weight;
        this.indexController = new IndexController(trainingSetId);
        this.trainingSetId = trainingSetId;
    }

    public ClassifierResult classify(String tweet){

        SimpleNaiveBayesClassifier classifier;
        try {
            IndexReader reader = DirectoryReader.open(indexController.getDirectory());
            LeafReader leafReader = SlowCompositeReaderWrapper.wrap(reader);
            classifier = new SimpleNaiveBayesClassifier();
            classifier.train(leafReader, indexController.getFieldText(), indexController.getFieldClass(), indexController.getAnalyzer());

            ClassificationResult<BytesRef> result = classifier.assignClass(tweet);
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
                ", trainingSetId=" + trainingSetId +
                '}';
    }

    public Double getWeight() {
        return weight;
    }

}
