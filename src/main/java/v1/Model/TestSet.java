package v1.Model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by lisamazzini on 01/08/16.
 */

@Entity
@Table(name = "test_set")
public class TestSet {

    private int id;
    private String name;

    public TestSet() {
    }

    @Id
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}