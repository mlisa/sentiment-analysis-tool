package v1.controller;

import v1.Model.Data;
import v1.utility.C;

/**
 * Created by lisamazzini on 08/08/16.
 */
public class Logic {

    public static Double computeRelevance(Data data){
        switch (data.getSource()){
            case C.TWITTER_SOURCE:
                return 1.0;
            default:
                throw new UnsupportedOperationException("Not supported yet");
        }
    }


}
