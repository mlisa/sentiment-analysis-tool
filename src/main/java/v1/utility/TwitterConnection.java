package v1.utility;

import twitter4j.*;
import twitter4j.api.SearchResource;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Class that handles the connection to Twitter, using Twitter4J lib (http://twitter4j.org).
 */
public class TwitterConnection {

    private Map<String, String> params;

    public void setParams(Map<String, String> params) {
        this.params = params;
    }

    /**This method computes the List of Status from Twitter that matches the query params.
     * @param maxId biggest id to retrieve, used to get more than 100 results, querying repeatedly from API
     * @throws TwitterException if the Query is not correct
     * @return the list of Status*/
    public List<Status> getStatusList(Long maxId) throws TwitterException {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey(Configuration.getTwitterOAuthConsumerKey())
                .setOAuthConsumerSecret(Configuration.getTwitterOAuthConsumerSecret())
                .setOAuthAccessToken(Configuration.getTwitterOAuthAccessToken())
                .setOAuthAccessTokenSecret(Configuration.getTwitterOAuthAccessTokenSecret());

        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter tw = tf.getInstance();
        SearchResource searchResource = tw.search();
        List<Status> finalResult = new ArrayList<>();


        List<Status> list = searchResource.search(this.buildQuery(maxId)).getTweets();
        for (Status result : list) {
            //Adding only original tweets, no retweets, and checking if they contain media o urls, if the parameters are true in the query map
            if(!((params.get("noURL").equals("true") && result.getURLEntities().length != 0) ||  (params.get("noMedia").equals("true") && result.getMediaEntities().length != 0)) && !result.isRetweet())
                finalResult.add(result);
        }


        List<Status> finalRes = finalResult;

        return finalRes;
    }

    /** Private method that builds the Query object, in accord to the params given.
     * @param maxID  biggest id to retrieve
     * @return Query object*/
    private Query buildQuery(Long maxID ) {
        String queryParams = "";
        Query query = new Query();
        Iterator it = this.params.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            if(pair.getValue() != null && pair.getValue() != "") {
                if (pair.getKey() == "sinceDate") {
                    query.setSince(pair.getValue().toString());
                } else if (pair.getKey() == "toDate") {
                    query.setUntil(pair.getValue().toString());
                } else if (pair.getKey() == "lang") {
                    query.setLang(pair.getValue().toString());
                } else if(pair.getKey() == "author") {
                    queryParams = queryParams.concat(" from:" + pair.getValue().toString() + " ");
                }else if(pair.getKey() == "notwords") {
                    queryParams = queryParams.concat(" -" + pair.getValue().toString() + " ");
                } else if(pair.getKey() != "noURL" && pair.getKey() != "noMedia")  {
                    queryParams = queryParams.concat(" " + pair.getValue().toString());
                }
            }
        }

        query.setQuery(queryParams);
        query.setCount(100);
        if( maxID != null && maxID != Long.MAX_VALUE - 1 )
            query.setMaxId(maxID);
        System.out.println(query.toString());
        return query;
    }
}
