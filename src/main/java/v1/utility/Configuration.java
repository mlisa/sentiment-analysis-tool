package v1.utility;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import v1.controller.ClassifierBayes;
import v1.controller.GenericClassifier;
import v1.controller.MultiClassifier;
import v1.controller.MultiClassifierImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that reads from the configuration file in the resources folder. It is based on
 * TypeSafe Config library https://github.com/typesafehub/config.
 */
public class Configuration {

    /**Getter for the sources from where the app gets its data
     * @return list of String representing the sources*/
    public static List<String> getSources(){
        Config config = ConfigFactory.load();
        return config.getStringList("app.sources");
    }

    /**Getter for the OAuthConsumerKey for Twitter API
     * @return string representing OAuthConsumerKey*/
    public static String getTwitterOAuthConsumerKey(){
        Config config = ConfigFactory.load();
        return config.getString("app.twitter.OAuthConsumerKey");
    }

    public static String getTwitterOAuthConsumerSecret(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthConsumerSecret");
    }

    public static String getTwitterOAuthAccessToken(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthAccessToken");
    }

    public static String getTwitterOAuthAccessTokenSecret(){
            Config config = ConfigFactory.load();
            return config.getString("app.twitter.OAuthAccessTokenSecret");
    }

    public static List<MultiClassifier> getMulticlassifiers(){
        List<MultiClassifier> multiclassifiers = new ArrayList<>();
        Config c = ConfigFactory.load();
        List<? extends ConfigObject> list = c.getObjectList("app.multiclassifiers");
        for(ConfigObject co : list){
            if(co.get("type").unwrapped().toString().equals("type0")){
                try {
                    List<GenericClassifier> l = getSimpleClassifiers(co.get("name").unwrapped().toString());
                    multiclassifiers.add(new MultiClassifierImpl(l));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return multiclassifiers;
    }

    public static String getCors(){
        Config c = ConfigFactory.load();
        return c.getString("app.cors");
    }

    public static int getTwitterMaxQueryNumber(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.max_query");
    }

    public static int getTwitterMaxTweetsNumber(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.max_tweet_num");
    }

    public static int getTwitterTweetsPerQuery(){
        Config c = ConfigFactory.load();
        return c.getInt("app.twitter.tweets_per_query");
    }

    private static List<GenericClassifier> getSimpleClassifiers(String multiclassifierName){
        List<GenericClassifier> classifiers = new ArrayList<>();
        Config c = ConfigFactory.load();
        List<? extends ConfigObject> list = c.getObjectList("app.multiclassifiers");
        for (ConfigObject co: list) {
            if(co.unwrapped().containsValue(multiclassifierName)){
                List<? extends ConfigObject> value = co.toConfig().getObjectList("simpleclassifiers");
                for(ConfigObject si : value){
                    if(si.get("type").unwrapped().toString().equals("Bayes")){
                        classifiers.add(new ClassifierBayes(Double.parseDouble(si.get("weight").unwrapped().toString()), Integer.parseInt(si.get("training_set_id").unwrapped().toString()))) ;
                    }else{
                        throw new UnsupportedOperationException("Simple classifier type not supported yet");
                    }
                }
            }
        }

        return classifiers;
    }



}
