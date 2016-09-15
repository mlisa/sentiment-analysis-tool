//package v1.endpoints;
//
//import org.springframework.web.bind.annotation.*;
//import v1.model.Data;
//import v1.model.Report;
//import v1.utility.HibernateUtil;
//
//import v1.controller.TwitterController;
//import v1.exception.QueryException;
//
//import java.util.*;
//
///**
// * Class that uses Spring framework create API for the client to pass the query params and get back the response
// */
//@CrossOrigin(origins = MainAPI.CORS)
//@RestController
//public class AddTweetAPI {
//
//    protected static final String CORS = "http://127.0.0.1:8089";
//
//
//    /**Method that maps the address with '/add'
//     * @param author author of the data
//     * @param tag tag inside the data (for example '@Someone')
//     * @param words words that must be inside data
//     * @param notWords word that must not be inside data
//     * @param sinceDate date since when retrieve data
//     * @param toDate last date of the data to retrieve
//     * @param lang language of data
//     * @param noURL flag that indicates if data must o must not contain URLs
//     * @param noMedia flag that indicates if data must o must not contain media
//     * */
//    @RequestMapping("/add")
//    public void getTweets(@RequestParam(value = "author", required = false) String author,
//                            @RequestParam(value = "tag", required = false) String tag,
//                            @RequestParam(value = "words", required = false) String words,
//                            @RequestParam(value = "notwords", required = false) String notWords,
//                            @RequestParam(value = "sinceDate", required = false) String sinceDate,
//                            @RequestParam(value = "toDate", required = false) String toDate,
//                            @RequestParam(value = "lang", required = false) String lang,
//                            @RequestParam(value = "noURL", required = false) String noURL,
//                            @RequestParam(value = "noMedia", required = false) String noMedia
//                            ) {
//
//        Map<String, String> params = new HashMap<>();
//        params.put("author", author);
//        params.put("tag", tag);
//        params.put("words", words);
//        params.put("notwords", notWords);
//        params.put("sinceDate", sinceDate);
//        params.put("toDate", toDate);
//        params.put("lang", lang);
//        params.put("noURL", noURL);
//        params.put("noMedia", noMedia);
//
//        TwitterController twitterController = new TwitterController(params);
//        List<Data> data = null;
//        try {
//            data = twitterController.getDataList();
//        } catch (QueryException e) {
//            e.printStackTrace();
//        }
//        HibernateUtil h = new HibernateUtil();
//
//        for (Data d: data ) {
//            h.setTrainingText(d.getText(), "positivo", 5);
//            //System.out.println(d.getText());
//        }
//
//    }
//
//}
