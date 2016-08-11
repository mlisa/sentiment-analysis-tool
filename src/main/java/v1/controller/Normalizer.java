package v1.controller;

import twitter4j.Status;
import v1.Model.Data;
import v1.Model.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lisamazzini on 23/07/16.
 */
public class Normalizer {

    public static List<Data> normalizeTweets(List<Status> statuses){

        List<Data> tweets = new ArrayList<>();

        for (Status s : statuses) {
            tweets.add(new Tweet(s, normalizeText(s.getText())));
        }

        return tweets;

    }

    public static String normalizeText(String norm){
        norm = norm.replaceAll("@\\p{L}+", "");
        if (containsPositiveEmoticon(norm)){
            norm = norm.concat(" posEmot");
        } else if (containsNegativeEmoticon(norm)){
            norm = norm.concat(" negEmot");
        }
        return norm;
    }

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
                text.contains("<3") ||
                text.contains("C:") );
    }

    private static boolean containsNegativeEmoticon(String text){
        return (text.contains(":(") ||
                text.contains(":((((") ||
                text.contains("D:") ||
                text.contains("D;") ||
                text.contains("D':") ||
                text.contains(":(") ||
                text.contains(":-(") ||
                text.contains("X(") ||
                text.contains(":C") ||
                text.contains(":-/") ||
                text.contains(":/") ||
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
