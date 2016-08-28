package v1.classifiers;
import v1.Model.ClassifierResult;

/**
 * Created by lisamazzini on 22/08/16.
 */
public class ClassifierBayesWNegation extends ClassifierBayes {

    public ClassifierBayesWNegation(Double weight, int trainingSetId, Double minConfidence) {
        super(weight, trainingSetId, minConfidence);
    }

    @Override
    public ClassifierResult classify(String text){
        ClassifierResult result = super.classify(text);
        if(text.contains("non") || text.contains("NON") || text.contains("Non")){
            return new ClassifierResult(this.invertPolarity(result), result.getScore(), text);
        }
        return result;
    }

    private String invertPolarity(ClassifierResult result){
        if(result.getPolarity().equals("positivo")){
            return "negativo";
        }
        return "positivo";
    }

}
