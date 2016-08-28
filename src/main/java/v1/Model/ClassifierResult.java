package v1.Model;

/**
 * POJO class that represents a result from a Classifier (can be both MultiClassifier or GenericClassifier)
 */
public class ClassifierResult {

    private String polarity;
    private Double score;
    private Double weight;
    private Double relevance;
    private String text;

    public ClassifierResult(String polarity, Double score, String text) {
        this.polarity = polarity;
        this.score = score;
        this.text = text;
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
        if(this.weight == null || (this.weight < 1 && weight < 1 && this.weight >= 0.5 && weight >= 0.5)) {
            this.weight = weight;
        }
    }

    public Double getRelevance() {
        return relevance;
    }

    public void setRelevance(Double relevance) {
        this.relevance = relevance;
    }

    public String getText() {
        return text;
    }
}
