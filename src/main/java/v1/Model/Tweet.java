package v1.Model;

import org.hibernate.annotations.GenericGenerator;
import twitter4j.Status;
import v1.utility.C;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by lisamazzini on 23/07/16.
 */

@Entity
@Table(name = "tweet")
public class Tweet implements Data {

    private int id;
    private TwitterUser author;
    private Date date;
    private String text;
    private Integer retweets;
    private Integer favourites;
    private String source;

    public Tweet(Status status, String normalizedText) {
        this.author = new TwitterUser(status.getUser());
        this.date = status.getCreatedAt();
        this.text = normalizedText;
        this.retweets = status.getRetweetCount();
        this.favourites = status.getFavoriteCount();
        this.source = C.TWITTER_SOURCE;
    }

    public Tweet(){}

    @Id
    @Column(name = "id")
    @GenericGenerator(name = "increment", strategy = "increment")
    @GeneratedValue(generator = "increment")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "author_id")
    public TwitterUser getAuthor() {
        return author;
    }

    public void setAuthor(TwitterUser author) {
        this.author = author;
    }

    @Column(name = "created")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Column(name = "text")
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Column(name = "retweets")
    public Integer getRetweets() {
        return retweets;
    }

    public void setRetweets(Integer retweets) {
        this.retweets = retweets;
    }

    @Column(name = "favourites")
    public Integer getFavourites() {
        return favourites;
    }

    public void setFavourites(Integer favourites) {
        this.favourites = favourites;
    }

    public String getSource(){
        return this.source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Tweet{" +
                "author=" + author.toString() +
                ", date=" + date +
                ", text='" + text + '\'' +
                ", retweets=" + retweets +
                ", favourites=" + favourites +
                '}';
    }
}
