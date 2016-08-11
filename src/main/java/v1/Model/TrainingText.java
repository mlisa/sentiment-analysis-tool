package v1.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lisamazzini on 01/08/16.
 */

@Entity
@Table(name = "training_text")
public class TrainingText {


    private int id;
    private String text;
    private String polarity;
    private int trainingSetId;

    public TrainingText() {
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

    @Column(name = "class")
    public String getPolarity() {
        return polarity;
    }

    public void setPolarity(String polarity) {
        this.polarity = polarity;
    }

    @Column(name = "training_set_id")
    public int getTrainingSetId() {
        return trainingSetId;
    }

    public void setTrainingSetId(int trainingSetId) {
        this.trainingSetId = trainingSetId;
    }

    @Override
    public String toString() {
        return "TrainingText{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", polarity='" + polarity + '\'' +
                ", trainingSetId='" + trainingSetId + '\'' +
                '}';
    }
}
