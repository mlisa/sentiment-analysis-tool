package v1.model;

import java.util.Date;

/**
 * Interface that represents generic Data to classify
 */
public interface Data {

    String getText();

    void setText(String text);

    Date getDate();

    void setDate(Date date);

    Source getSource();

}
