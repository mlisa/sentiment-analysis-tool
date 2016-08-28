package v1.utility;

import twitter4j.Status;
import v1.Model.Data;
import v1.Model.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that normalize the results from different sources in one type of Data, but also substitute emoticons and similar content
 * to keyword in order to make them recognizable for the Classifiers.
 */
public class Normalizer {

    /**Method that normalizes the list of tweets that are under the Status (twitter4j) form into a list of generic Data
     * @param statuses List of Status to normalize
     * @return list of generic Data normalized
     * @see Data */
    public static List<Data> normalizeTweets(List<Status> statuses){

        List<Data> tweets = new ArrayList<>();

        for (Status s : statuses) {
            tweets.add(new Tweet(s, normalizeText(s.getText())));
        }

        return tweets;

    }

    /**Method that normalizes a given text, removing the @ tag and substituting the emoticons
     * @param norm string to normalize
     * @return the string normalized*/
    public static String normalizeText(String norm){
        norm = norm.replaceAll("@[A-Za-z_]+", "");
        String[] string = norm.split(" ");

        for (String s :string) {
            if (containsPositiveEmoticon(s)) {
                norm = norm.concat(" posEmot");
            } else if (containsNegativeEmoticon(s)) {
                norm = norm.concat(" negEmot");
            }
        }

        return norm;
    }

    /**Method that check if a string contains a positive emoticon
     * @param text string to check
     * @return boolean*/
    private static boolean containsPositiveEmoticon(String text){
        return (text.contains(":)") ||
                text.contains(":D") ||
                text.contains(":))))") ||
                text.contains(":-)") ||
                text.contains("x)") ||
                text.contains(":-D") ||
                text.contains("XD") ||
                text.contains(";D") ||
                text.contains(";)") ||
                text.contains(":]") ||
                text.contains(":-]") ||
                text.contains("(:") ||
                text.contains("(-:") ||
                text.contains("(;") ||
                text.contains("(x") ||
                text.contains("^^") ||
                text.contains("^_^") ||
                text.contains("^.^") ||
                text.contains("<3"));
    }

    /**Method that check if a string contains a negative emoticon
     * @param text string to check
     * @return boolean*/
    private static boolean containsNegativeEmoticon(String text){
        return (text.contains(":(") ||
                text.contains(":((((") ||
                text.contains("D:") ||
                text.contains("D;") ||
                text.contains("D':") ||
                text.contains(":(") ||
                text.contains(":-(") ||
                text.contains("X(") ||
                text.contains(":-/") ||
                text.contains(">:(") ||
                text.contains("D:<") ||
                text.contains(":'(") ||
                text.contains(":''''(") ||
                text.contains(":[") ||
                text.contains(":-[") ||
                text.contains(":'[") ||
                text.contains(":S") ||
                text.contains("</3") ||
                text.contains("-.-"));
    }

}
