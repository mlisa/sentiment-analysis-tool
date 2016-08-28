package v1.Model;

/**
 * POJO class that represents the final report of the classification.
 */

//Sarà il risultato da far restituire dalle API
public class Report {

    private String result;
    private Double confidence;
    private int positive;
    private int negative;
    private int total;
    private String negExample;
    private String posExample;
    private String status;
    private String message;


    public Report(String result, int positive, int negative, String posExample, String negExample) {
        this.result = result;
        this.positive = positive;
        this.negative = negative;
        this.negExample = negExample;
        this.posExample = posExample;
        this.total = positive+negative;
        if(this.total != 0){
            this.status = "OK";
        }else{
            this.status = "NOTOK";
            this.message = "Dati insufficienti per effettuare l'analisi";
        }
    }

    public Report(String result, int positive, int negative, String posExample, String negExample, String msg) {
        this.result = result;
        this.positive = positive;
        this.negative = negative;
        this.negExample = negExample;
        this.posExample = posExample;
        this.total = positive+negative;
        if(this.total != 0){
            this.status = "OK";
        }else{
            this.status = "NOTOK";
        }
        this.message = msg;
    }

    public String getResult() {
        return result;
    }


    public int getPositive() {
        return positive;
    }

    public int getNegative() {
        return negative;
    }

    public String getNegExample() {
        return negExample;
    }

    public String getPosExample() {
        return posExample;
    }

    public int getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "Report{" +
                "result='" + result + '\'' +
                ", confidence=" + confidence +
                '}';
    }
}
