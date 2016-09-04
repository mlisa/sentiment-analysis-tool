package v1.utility;

import v1.model.Data;
import org.apache.commons.lang3.StringUtils;
/**
 * TODO : doc
 */
public class SemanticAnalyzer {

    public static Double getAdverbScore(Data data) {
        Double score = 0.0;
        for(String s : data.getText().split(" ")) {
            if (s.matches("(.*)(mente)") || s.equals("davvero") || s.equals("molto") || s.equals("moltissimo") || s.equals("poco") || s.equals("troppo")) {
                score += 0.05;
            }
        }
        return score;
    }

    public static Double getAdverbScore(String data) {
        Double score = 0.0;
        for(String s : data.split(" ")) {
            if (s.matches("(.*)(mente)") || s.equals("davvero") || s.equals("molto") || s.equals("moltissimo") || s.equals("poco") || s.equals("troppo")) {
                score += 0.05;
            }
        }
        return score;
    }

    public static Double getSuperlativeScore(Data data) {

        double score = 0;

        for(String s : data.getText().split(" ")) {
            if (s.matches("(.*)(issim)(.*)")) {
                score += 2;
            }
        }
        return score;
    }

    public static Double getSuperlativeScore(String s) {

        double score = 0;
        for(String se : s.split(" ")) {
            if (se.matches("(.*)(issim)(.*)")) {
                score += 2;
            }
        }

        return score;
    }

    public static int getPositiveEmoticonScore(Data data){

        return StringUtils.countMatches(data.getText(), "posEmot");

    }

    public static int getNegativeEmoticonScore(Data data){

        return StringUtils.countMatches(data.getText(), "negEmot");

    }




}
