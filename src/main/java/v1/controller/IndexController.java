package v1.controller;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.it.ItalianAnalyzer;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.document.Document;
import org.apache.lucene.store.FSDirectory;
import v1.model.TrainingText;
import v1.utility.HibernateUtil;
import v1.utility.Normalizer;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Controller that handles the Index from Apache Lucene lib. It creates and populates it, using data from database.
 */
public class IndexController {

    /**Constants used inside the index*/
    private final String FIELD_TEXT = "tweet", FIELD_CLASS = "polarity";
    /**Directory where the Index is stored*/
    private Directory directory;
    /**Analyzer used to parse data inside the Index*/
    private Analyzer analyzer = new ItalianAnalyzer();
    /**Object used to write the Index*/
    private IndexWriter writer;
    /**Training set id*/
    private int trainingSetId;

    /**Public constructor
     * @param trainingSetId training-set id in the database */
    public IndexController(int trainingSetId){
        try {
            IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
            config.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            this.directory = FSDirectory.open(new File("src/main/resources/" + trainingSetId).toPath());
            this.writer =  new IndexWriter(directory, config);
            this.trainingSetId = trainingSetId;
        }catch(Exception e){
            System.out.println(e.getMessage() + e.getClass().toString());
        }
    }

    /**Method that closes the IndexWriter*/
    private void closeWriter(){
        try {
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**Getter for the Directory
     * @return Directory*/
    public Directory getDirectory(){
        return this.directory;
    }

    /**Getter for the Analyzer
     * @return Analyzer*/
    public Analyzer getAnalyzer(){
        return this.analyzer;
    }

    /**Getter for the field text
     * @return field text*/
    public String getFieldText(){
        return FIELD_TEXT;
    }

    /**Getter fot the field class
     * @return field class*/
    public String getFieldClass(){
        return FIELD_CLASS;
    }

    /**Method that populates the Index, getting training text from the database*/
    public void populateIndex(){
        HibernateUtil hb = new HibernateUtil();
        List<TrainingText> trainingTextList = hb.getTrainingSetText(this.trainingSetId);

        for(TrainingText trainingText : trainingTextList){
            addTextAndClass(trainingText.getText(), trainingText.getPolarity());

        }
        this.closeWriter();

    }

    /**Method to add to the index a text and a class for it
     * @param text text to add in the index
     * @param label class to give at the corresponding text*/
    private void addTextAndClass( String text, String label){
        Document doc = new Document();
        doc.add(new TextField(FIELD_TEXT, Normalizer.normalizeText(text), Field.Store.NO));
        doc.add(new StringField(FIELD_CLASS, label, Field.Store.YES));
        try {
            this.writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
