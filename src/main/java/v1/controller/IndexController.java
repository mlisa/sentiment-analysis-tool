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
import v1.Model.TrainingText;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by lisamazzini on 25/06/16.
 * Controller che gestisce la creazione e scrittura dell'Index necessario al Classifier di Lucene.
 */
public class IndexController {

    private final String FIELD_TEXT = "tweet", FIELD_CLASS = "polarity";
    private Directory directory;
    private Analyzer analyzer = new ItalianAnalyzer();
    private IndexWriter writer;

    /**Costruttore pubblico; al suo interno viene creato e popolato l'indice*/
    public IndexController(int trainingSetId){
        try {
            IndexWriterConfig config = new IndexWriterConfig(this.analyzer);
            this.directory = FSDirectory.open(new File("src/main/resources/" + trainingSetId).toPath());
            this.writer =  new IndexWriter(directory, config);
            populateIndex(trainingSetId);
            closeWriter();
        }catch(Exception e){
            System.out.println(e.getMessage() + e.getClass().toString());
        }
    }

    /**Chiusura dell'IndexWriter*/
    public void closeWriter(){
        try {
            writer.commit();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }

    /**Getter per la directory dove è salvato l'index*/
    public Directory getDirectory(){
        return this.directory;
    }

    /**Getter per l'Analyzer utilizzato*/
    public Analyzer getAnalyzer(){
        return this.analyzer;
    }

    /**Getter del nome del campo text*/
    public String getFieldText(){
        return FIELD_TEXT;
    }

    /**Getter del nome del campo che identifica la classe*/
    public String getFieldClass(){
        return FIELD_CLASS;
    }

    /**Metodo per popolare l'indice*/
    private void populateIndex(int trainingSetId){
        HibernateUtil hb = new HibernateUtil();
        List<TrainingText> trainingTextList = hb.getTrainingSetText(trainingSetId);

        for(TrainingText trainingText : trainingTextList){

            Document doc = new Document();
            addTweetAndClass(doc, trainingText.getText(), trainingText.getPolarity());

        }

    }

    /**Metodo per aggiungere a un documento testo e classe */
    private void addTweetAndClass(Document doc, String tweet, String label){
        doc.add(new TextField(FIELD_TEXT, Normalizer.normalizeText(tweet), Field.Store.NO));
        doc.add(new StringField(FIELD_CLASS, label, Field.Store.YES));
        try {
            this.writer.addDocument(doc);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
