package v1.controller;

/**
 * Created by lisamazzini on 26/08/16.
 */
public enum TrainingSets {

    NRC(4),
    REAL_TEXTS(5),
    SUPERLATIVES(6),
    HASHTAGS(7);

    private int id;

    TrainingSets(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}