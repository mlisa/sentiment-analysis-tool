package v1.Model;

import v1.utility.Source;

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
