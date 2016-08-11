package v1.controller;

import v1.Model.Data;

import java.util.List;

/**
 * Interface that represents a generic Controller from a source.
 */
public interface SourceController {

    /**Returns the results from Twitter, under the form of generic Data
     * @return the list of Data from the query
     * @see Data*/
    List<Data> getDataList();

}
