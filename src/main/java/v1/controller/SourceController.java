package v1.controller;

import v1.model.Data;
import v1.exception.QueryException;

import java.util.List;

/**
 * Interface that represents a generic Controller from a source.
 */
public interface SourceController {

    /**Returns the results from Twitter, under the form of generic Data
     * @return the list of Data from the query
     * @throws QueryException if the Query is not correct
     * @see Data*/
    List<Data> getDataList() throws QueryException;

}
