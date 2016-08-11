package v1.Model;

/**
 * Created by lisamazzini on 03/08/16.
 */

//Sarà il risultato da far restituire dalle API
public class Report {

    private String result;
    private Double confidence;

    public Report(String result, Double confidence) {
        this.result = result;
        this.confidence = confidence;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Double getConfidence() {
        return confidence;
    }

    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @Override
    public String toString() {
        return "Report{" +
                "result='" + result + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
