package v1.model;

/**
 * Created by lisamazzini on 14/09/16.
 */
public class ParamsMessage {

    private String author;
    private String tag;
    private String words;
    private String notwords;
    private String lang;
    private String sinceDate;
    private String toDate;
    private String noURL;
    private String noMedia;


    public ParamsMessage(String author, String tag, String words, String notwords, String lang, String sinceDate, String toDate, String noURL, String noMedia) {
        this.author = author;
        this.tag = tag;
        this.words = words;
        this.notwords = notwords;
        this.lang = lang;
        this.sinceDate = sinceDate;
        this.toDate = toDate;
        this.noURL = noURL;
        this.noMedia = noMedia;
    }

    public ParamsMessage() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getWords() {
        return words;
    }

    public void setWords(String words) {
        this.words = words;
    }

    public String getNotwords() {
        return notwords;
    }

    public void setNotwords(String notwords) {
        this.notwords = notwords;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getSinceDate() {
        return sinceDate;
    }

    public void setSinceDate(String sinceDate) {
        this.sinceDate = sinceDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getNoURL() {
        return noURL;
    }

    public void setNoURL(String noURL) {
        this.noURL = noURL;
    }

    public String getNoMedia() {
        return noMedia;
    }

    public void setNoMedia(String noMedia) {
        this.noMedia = noMedia;
    }
}
