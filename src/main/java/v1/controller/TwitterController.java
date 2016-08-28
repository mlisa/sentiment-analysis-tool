package v1.controller;

import twitter4j.*;
import v1.Model.Data;
import v1.exception.QueryException;
import v1.utility.Configuration;
import v1.utility.TwitterConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class implements SourceController interface; it gets the parameters from MainController, and pass them to
 * the TwitterConnection class. It iterates the connection until there are results or the number of results is
 * correct. Then it normalizes the result in order to give back a generic List of Data, not Status (model from
 * Twitter4J library).
 */
public class TwitterController implements SourceController{

    private TwitterConnection twitterConnection;

    /**Public constructor
     * @param map the map containing all the params from the query */
    public TwitterController(Map<String, String> map){
        this.twitterConnection = new TwitterConnection();
        this.twitterConnection.setParams(map);
    }

    /**Returns the results from Twitter, under the form of generic Data
     * @return the list of Data from the query
     * @see Data*/
    public List<Data> getDataList() throws QueryException {

        //LastID needed for querying more than 100 elements
        long lastID = Long.MAX_VALUE;
        int nQuery = 0;
        List<Status> list = new ArrayList<>();

        while ( list.size() < Configuration.getTwitterMaxTweetsNumber() && nQuery < Configuration.getTwitterMaxQueryNumber()) {
            try {
                list = this.twitterConnection.getStatusList(lastID);
            } catch (TwitterException e) {
                throw new QueryException("Parametri della query non sufficienti!");
            }

            //Search for the last id
            for (Status t : list) {
                if (t.getId() < lastID){
                    lastID = t.getId();
                }
            }

            //If the list is empty or the query returns less than 100 results, stop the search
            if(list.isEmpty() || list.size() < Configuration.getTwitterTweetsPerQuery()){
                break;
            }
            nQuery++;
        }

        List<Data> datas = Normalizer.normalizeTweets(list);
        list.clear();
        return datas;
    }

}
