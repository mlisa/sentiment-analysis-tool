package v1.classifiers;

import org.apache.lucene.classification.ClassificationResult;
import org.apache.lucene.classification.SimpleNaiveBayesClassifier;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReader;
import org.apache.lucene.index.SlowCompositeReaderWrapper;
import org.apache.lucene.util.BytesRef;
import v1.model.ClassifierResult;
import v1.model.TrainingSet;
import v1.controller.IndexController;

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
    /**Minimum score acceptable for a result to be considered classifiable*/
    protected Double minConfidence;
    /**Trainingset type*/
    protected TrainingSet trainingSet;

    /**Public constructor
     * @param weight weight of Classifier
     * @param trainingSetId id of the training set that will be used to train the classifier
     * @param minConfidence minimun score for a result in order to be considered classifiable*/
    public ClassifierBayes(Double weight, Integer trainingSetId, Double minConfidence) {
        this.weight = weight;
        this.indexController = new IndexController(trainingSetId);
        this.indexController.populateIndex();
        this.minConfidence = minConfidence;
        switch (trainingSetId){
            case 4:
                this.trainingSet = TrainingSet.NRC;
                break;
            case 5:
                this.trainingSet = TrainingSet.REAL_TEXTS;
                break;
            case 6:
                this.trainingSet = TrainingSet.SUPERLATIVES;
                break;
            case 7:
                this.trainingSet = TrainingSet.HASHTAGS;
                break;
            case 10:
                this.trainingSet = TrainingSet.ALL;
                break;
            default:
                throw new UnsupportedOperationException("Training set id not correct");
        }
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

            return new ClassifierResult(assigned, score, text);

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public Double getWeight() {
        return weight;
    }


    @Override
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getMinConfidence() {
        return minConfidence;
    }

    @Override
    public TrainingSet getTrainingSet() {
        return this.trainingSet;
    }


    @Override
    public String toString() {
        return "ClassifierBayes{" +
                "weight=" + weight +
                ", indexController=" + indexController +
                '}';
    }
}
