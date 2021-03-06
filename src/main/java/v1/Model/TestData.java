package v1.model;

import java.time.Instant;
import java.util.Date;

/**
 * Created by lisamazzini on 28/08/16.
 */
public class TestData implements Data {

    private Date date;
    private String text;
    private Source source;
    private String polarity;

    public TestData(String text, String polarity) {
        this.text = text;
        this.date = Date.from(Instant.now());
        this.source = Source.TEST;
        this.polarity = polarity;
    }

    @Override
    public Date getDate() {
        return date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public void setText(String text) {
        this.text = text;
    }

    @Override
    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }
}
