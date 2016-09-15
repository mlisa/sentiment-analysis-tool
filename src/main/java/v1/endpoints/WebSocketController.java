package v1.endpoints;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import v1.classifiers.MultiClassifier;
import v1.controller.MainController;
import v1.exception.QueryException;
import v1.model.*;
import v1.utility.Configuration;
import v1.utility.HibernateUtil;
import v1.utility.Normalizer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lisamazzini on 14/09/16.
 */
@CrossOrigin(origins = "http://127.0.0.1:8089")
@Controller
public class WebSocketController {

    @CrossOrigin(origins = "http://127.0.0.1:8089")
    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public Report greeting(ParamsMessage params) throws Exception {

        System.out.println("ciao");
        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("author", params.getAuthor());
        paramsMap.put("tag", params.getTag());
        paramsMap.put("words", params.getWords());
        paramsMap.put("notwords", params.getNotwords());
        paramsMap.put("sinceDate", params.getSinceDate());
        paramsMap.put("toDate", params.getToDate());
        paramsMap.put("lang", params.getLang());
        paramsMap.put("noURL", params.getNoURL());
        paramsMap.put("noMedia", params.getNoMedia());

        MainController mainController = MainController.getInstance();
        try {
            mainController.setParams(paramsMap);
        } catch (QueryException e) {
            return new Report("", 0,0, 0,"","", e.getMessage());
        }

        return mainController.getReport();

    }

    @CrossOrigin(origins = "http://127.0.0.1:8089")
    @MessageMapping("/ciao")
    @SendTo("/topic/test")
    public Report test() throws Exception {

        System.out.println("test");
        List<Data> testData = new ArrayList<>();
        List<Data> testData2 = new ArrayList<>();
        List<Data> testData3 = new ArrayList<>();
        List<Data> testData4 = new ArrayList<>();
        HibernateUtil h = new HibernateUtil();
        List<TestText> test = h.getTestSetList(8, "n");
        test.addAll(h.getTestSetList(9, "n"));

        List<TestText> test2 = h.getTestSetList(8, "p");
        test2.addAll(h.getTestSetList(9, "p"));
        List<TestText> test3 = h.getTestSetList(8, "ne");
        test3.addAll(h.getTestSetList(9, "ne"));
        List<TestText> test4 = h.getTestSetList(8);
        test4.addAll(h.getTestSetList(9));
        test.addAll(h.getTestSetList(4));
        test.addAll(h.getTestSetList(5));
        test.addAll(h.getTestSetList(6));
        test.addAll(h.getTestSetList(7));
//int countGiuste = 0;
        for(TestText t : test){
            testData.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
        }
        for(TestText t : test2){
            testData2.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
        }
        for(TestText t : test3){
            testData3.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
        }
        for(TestText t : test4){
            testData4.add(new TestData(Normalizer.normalizeText(t.getText()), t.getPolarity()));
        }
        MultiClassifier multiClassifier = Configuration.getMulticlassifier();
//multiClassifier.computeOpinion(testData);
//multiClassifier.computeOpinion(testData2);
        multiClassifier.computeOpinion(testData3);
//multiClassifier.computeOpinion(testData4);
        return multiClassifier.getReport();

    }




}
