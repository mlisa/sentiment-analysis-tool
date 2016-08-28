package v1.endpoints;

import org.springframework.web.bind.annotation.*;
import v1.Model.ClassifierResult;
import v1.Model.Data;
import v1.Model.Report;
import v1.Model.TestText;
import v1.controller.ClassifierBayes;
import v1.controller.HibernateUtil;

import v1.controller.TwitterController;
import v1.exception.QueryException;

import java.util.*;

/**
 * Class that uses Spring framework create API for the client to pass the query params and get back the response
 */
@CrossOrigin(origins = api.CORS)
@RestController
public class addTweet {

    protected static final String CORS = "http://127.0.0.1:8089";


    /**Method that maps the address with '/report'*/
    @RequestMapping("/add")
    public Report getTweets(@RequestParam(value = "author", required = false) String author,
                            @RequestParam(value = "tag", required = false) String tag,
                            @RequestParam(value = "words", required = false) String words,
                            @RequestParam(value = "notwords", required = false) String notWords,
                            @RequestParam(value = "sinceDate", required = false) String sinceDate,
                            @RequestParam(value = "toDate", required = false) String to,
                            @RequestParam(value = "lang", required = false) String lang,
                            @RequestParam(value = "noURL", required = false) String noURL,
                            @RequestParam(value = "noMedia", required = false) String noMedia
                            ) {

        Map<String, String> params = new HashMap<>();
        params.put("author", author);
        params.put("tag", tag);
        params.put("words", words);
        params.put("notwords", notWords);
        params.put("sinceDate", sinceDate);
        params.put("toDate", to);
        params.put("lang", lang);
        params.put("noURL", noURL);
        params.put("noMedia", noMedia);

        TwitterController twitterController = new TwitterController(params);
        List<Data> data = null;
        try {
            data = twitterController.getDataList();
        } catch (QueryException e) {
            e.printStackTrace();
        }
        HibernateUtil h = new HibernateUtil();

        for (Data d: data ) {
            h.setTrainingText(d.getText(), "negativo", 5);
            //System.out.println(d.getText());
        }

        return null;
    }

}
