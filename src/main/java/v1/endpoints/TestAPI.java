//package v1.endpoints;
//
//import org.springframework.web.bind.annotation.*;
//import v1.classifiers.ClassifierBayes;
//import v1.model.*;
//import v1.classifiers.MultiClassifier;
//import v1.utility.Configuration;
//import v1.utility.HibernateUtil;
//import v1.utility.Normalizer;
//
//import java.util.*;
//
///**
// * Class that uses Spring framework create API for the client to pass the query params and get back the response.
// */
//@CrossOrigin(origins = MainAPI.CORS)
//@RestController
//public class TestAPI {
//
//    protected static final String CORS = "http://127.0.0.1:8089";
//
//
//    /**Method that maps the address with '/test'
//     * @return final report
//     * @see Report*/
//    @RequestMapping("/test")
//    public Report test() {
//
//        List<Data> testData = new ArrayList<>();
//        List<Data> testData2 = new ArrayList<>();
//        List<Data> testData3 = new ArrayList<>();
//        List<Data> testData4 = new ArrayList<>();
//        HibernateUtil h = new HibernateUtil();
//
//        List<TestText> test = h.getTestSetList(8, "n");
//        test.addAll(h.getTestSetList(9, "n"));
//
//        List<TestText> test2 = h.getTestSetList(8, "p");
//        test2.addAll(h.getTestSetList(9, "p"));
//
//        List<TestText> test3 = h.getTestSetList(8, "ne");
//        test3.addAll(h.getTestSetList(9, "ne"));
//
//        List<TestText> test4 = h.getTestSetList(8);
//        test4.addAll(h.getTestSetList(9));
////        test.addAll(h.getTestSetList(4));
////        test.addAll(h.getTestSetList(5));
////        test.addAll(h.getTestSetList(6));
////        test.addAll(h.getTestSetList(7));
//        //int countGiuste = 0;
//
//        for(TestText t : test){
//            testData.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
//        }
//        for(TestText t : test2){
//            testData2.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
//        }
//        for(TestText t : test3){
//            testData3.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
//        }
//        for(TestText t : test4){
//            testData4.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
//        }
//
//        MultiClassifier multiClassifier = Configuration.getMulticlassifier();
//        //multiClassifier.computeOpinion(testData);
//        //multiClassifier.computeOpinion(testData2);
//        multiClassifier.computeOpinion(testData3);
//        //multiClassifier.computeOpinion(testData4);
//
//        return multiClassifier.getReport();
//    }
//
//}
