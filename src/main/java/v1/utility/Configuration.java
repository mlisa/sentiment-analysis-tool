package v1.utility;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import v1.classifiers.*;
import v1.controller.SourceController;
import v1.controller.TwitterController;
import v1.model.Source;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Class that reads from the configuration file in the resources folder. It is based on
 * TypeSafe Config library https://github.com/typesafehub/config.
 */
public class Configuration {

    /**Getter for the sources from where the app gets its data
     * @return list of String representing the sources*/
    public static List<SourceController> getSources(Map<String, String> params){

        Config config = ConfigFactory.load();
        List<String> sources = config.getStringList("app.sources");
        List<SourceController> sourceControllers = new ArrayList<>();

        for(String source : sources){
            if(source.equals(Source.TWITTER.getName())){
                sourceControllers.add(new TwitterController(params));
            }else{
                throw new UnsupportedOperationException("Source not supported yet");
            }
        }
        return sourceControllers;
    }

    /**Getter for the OAuthConsumerKey for Twitter API
     * @return string representing OAuthConsumerKey*/
    public static String getTwitterOAuthConsumerKey(){
        Config config = ConfigFactory.load();
        return config.getString("app.twitter.OAuthConsumerKey");
    }

    /**Getter for the OAuthConsumerSecret for Twitter API
     * @return string representing OAuthConsumerSecret*/
    public static String getTwitterOAuthConsumerSecret(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthConsumerSecret");
    }

    /**Getter for the OAuthAccessToken for Twitter API
     * @return string representing OAuthAccessToken*/
    public static String getTwitterOAuthAccessToken(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthAccessToken");
    }

    /**Getter for the OAuthAccessTokenSecret for Twitter API
     * @return string representing OAuthAccessTokenSecret*/
    public static String getTwitterOAuthAccessTokenSecret(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthAccessTokenSecret");
    }

    /**Getter for the list of MultiClassifier that will be implemented inside the application.
     * This method reads from configuration and creates the corresponding type of MultiClassifier
     * @return list of MultiClassifier
     * @see MultiClassifier */
    public static MultiClassifier getMulticlassifier(){
        Config c = ConfigFactory.load();
        MultiClassifier m = null;
        ConfigObject co = c.getObject("app.multiclassifier");
            if(co.get("type").unwrapped().toString().equals("type0")){
                try {
                    List<GenericClassifier> l = getSimpleClassifiers(co.get("name").unwrapped().toString());
                    m = new MultiClassifierImpl(l);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                throw new UnsupportedOperationException("Multiclassifier not supported yet");
            }
        return m;
    }

    /**Getter for the maximum number of query that can be performed
     * @return max query number*/
    public static int getTwitterMaxQueryNumber(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.max_query");
    }

    /**Getter for the maximum number of tweets that will be retrieved from Twitter API
     * @return max number of tweets*/
    public static int getTwitterMaxTweetsNumber(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.max_tweet_num");
    }

    /**Getter for the number of tweets retrieved per query
     * @return number of tweets*/
    public static int getTwitterTweetsPerQuery(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.tweets_per_query");
    }

    public static boolean isTest(){
        Config c = ConfigFactory.load();
        return c.getBoolean("app.test");
    }

    /**Getter for GenericClassifiers that will be used inside a given Multiclassifier.
     * @param multiclassifierName name of the MultiClassifier from which the configuration will be read
     * @return list of GenericClassifier
     * @see MultiClassifier
     * @see GenericClassifier*/
    private static List<GenericClassifier> getSimpleClassifiers(String multiclassifierName){
        List<GenericClassifier> classifiers = new ArrayList<>();
        Config c = ConfigFactory.load();
        ConfigObject co = c.getObject("app.multiclassifier");

        if(co.unwrapped().containsValue(multiclassifierName)){
            List<? extends ConfigObject> value = co.toConfig().getObjectList("simpleclassifiers");
            for(ConfigObject si : value) {
                if (si.get("type").unwrapped().toString().equals("Bayes")) {
                    classifiers.add(new ClassifierBayes(Double.parseDouble(si.get("weight").unwrapped().toString()), Integer.parseInt(si.get("training_set_id").unwrapped().toString()), Double.parseDouble(si.get("minConfidence").unwrapped().toString())));
                } else if (si.get("type").unwrapped().toString().equals("BayesWNegation")) {
                    classifiers.add(new ClassifierBayesWNegation(Double.parseDouble(si.get("weight").unwrapped().toString()), Integer.parseInt(si.get("training_set_id").unwrapped().toString()), Double.parseDouble(si.get("minConfidence").unwrapped().toString())));
                }else{
                    throw new UnsupportedOperationException("Simple classifier type not supported yet");
                }
            }
        }


        return classifiers;
    }




}
