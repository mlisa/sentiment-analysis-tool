package v1.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * POJO class that represents a training text.
 */

@Entity
@Table(name = "test_text")
public class TestText {


    private int id;
    private String text;
    private String polarity;
    private int testSet;

    public TestText() {
    }

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "clas")
    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    @Column(name = "test_set")
    public int getTestSet() {
        return testSet;
    }

    public void setTestSet(int testSet) {
        this.testSet = testSet;
    }

    @Override
    public String toString() {
        return "TrainingText{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", polarity='" + polarity + '\'' +
                ", testSet='" + testSet + '\'' +
                '}';
    }
}
