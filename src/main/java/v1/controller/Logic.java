package v1.controller;

import v1.Model.Data;
import v1.utility.Source;

import static v1.utility.Source.TWITTER;

/**
 * Class that implements the logic with which some parameters are computed. Those parameters may depend on Data's source,
 * so it's preferred to keep this kind of computation out from the principal classes.
 */
public class Logic {

    /**Method that computes the relevance of Data, for example in Tweets, it depends on number of retweets, favourites, ecc
     * @param data Data to evaluate
     * @return value of relevance
     * @see Data*/
    public static Double computeRelevance(Data data){
        switch (data.getSource()){
            case TWITTER:
                //TODO: calcola la rilevanza! ! !
                return 1.0;
            default:
                throw new UnsupportedOperationException("Not supported yet");
        }
    }


}
