package v1.model;

/**
 * Created by lisamazzini on 26/08/16.
 */
public enum TrainingSet {

    NRC(4),
    REAL_TEXTS(5),
    SUPERLATIVES(6),
    HASHTAGS(7),
    ALL(10);

    private int id;

    TrainingSet(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
