package v1.endpoints;

import org.springframework.web.bind.annotation.*;
import v1.Model.Data;
import v1.Model.Report;
import v1.Model.TestData;
import v1.Model.TestText;
import v1.controller.*;
import v1.utility.Configuration;

import java.util.*;

/**
 * Class that uses Spring framework create API for the client to pass the query params and get back the response.
 */
@CrossOrigin(origins = api.CORS)
@RestController
public class test {

    protected static final String CORS = "http://127.0.0.1:8089";


    /**Method that maps the address with '/test'*/
    @RequestMapping("/test")
    public Report test() {

        List<Data> testData = new ArrayList<>();
        HibernateUtil h = new HibernateUtil();
        List<TestText> test = h.getTestSetList(2);
        test.addAll(h.getTestSetList(1));
        test.addAll(h.getTestSetList(0));

        for(TestText t : test){
            testData.add(new TestData(t.getText()));
        }

        MultiClassifier multiClassifier = Configuration.getMulticlassifier();
        multiClassifier.computeOpinion(testData);

        return multiClassifier.getReport();
    }

}
