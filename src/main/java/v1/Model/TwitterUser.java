package v1.model;

import twitter4j.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * POJO Class that represent a Twitter user.
 */

@Entity
@Table(name = "author")
public class TwitterUser implements Author{


    private String name;
    private Integer followers;
    private Boolean isVerified;
    private int id;

    public TwitterUser(User user) {
        this.name = user.getName();
        this.followers = user.getFollowersCount();
        this.isVerified = user.isVerified();
    }

    public TwitterUser() {
    }

    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "username")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "followers")
    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @Column(name = "is_verified")
    public Boolean getVerified() {
        return isVerified;
    }

    public void setVerified(Boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "TwitterUser{" +
                "name='" + name + '\'' +
                ", followers=" + followers +
                ", isVerified=" + isVerified +
                '}';
    }

}
