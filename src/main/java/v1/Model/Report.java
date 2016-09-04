package v1.model;

/**
 * POJO class that represents the final report of the classification.
 */

public class Report {

    private String result;
    private int positive;
    private int negative;
    private int neutral;
    private int total;
    private String negExample;
    private String posExample;
    private String status;
    private String message;


    public Report(String result, int positive, int negative, int neutral, String posExample, String negExample) {
        this.result = result;
        this.positive = positive;
        this.negative = negative;
        this.neutral = neutral;
        this.negExample = negExample;
        this.posExample = posExample;
        this.total = positive+negative+neutral;
        if(this.total != 0){
            this.status = "OK";
        }else{
            this.status = "NOTOK";
            this.message = "Dati insufficienti per effettuare l'analisi";
        }
    }

    public Report(String result, int positive, int negative, int neutral, String posExample, String negExample, String msg) {
        this.result = result;
        this.positive = positive;
        this.negative = negative;
        this.neutral = neutral;
        this.negExample = negExample;
        this.posExample = posExample;
        this.total = positive+negative+neutral;
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

    public int getNeutral() {
        return neutral;
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


}
