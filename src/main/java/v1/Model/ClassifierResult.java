package v1.Model;

/**
 * POJO class that represents a result from a Classifier (can be both MultiClassifier or GenericClassifier)
 */
public class ClassifierResult {

    private String polarity;
    private Double score;
    private Double weight;
    private Double relevance;

    public ClassifierResult(String polarity, Double score) {
        this.polarity = polarity;
        this.score = score;
    }

    public String getPolarity() {
        return polarity;
    }

    public Double getScore() {
        return score;
    }

    public Double getWeigth() {
        return weight;
    }

    public void setWeigth(Double weight) {
        this.weight = weight;
    }

    public Double getRelevance() {
        return relevance;
    }

    public void setRelevance(Double relevance) {
        this.relevance = relevance;
    }
}
