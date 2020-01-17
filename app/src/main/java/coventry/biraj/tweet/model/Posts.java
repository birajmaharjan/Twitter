package coventry.biraj.tweet.model;

import java.util.ArrayList;
import java.util.List;

public class Posts {
    private String name, username, time, story, like, comment, retweet, image, uimage;
    static List<Posts> postsList = new ArrayList<>();

    public Posts(String name, String username, String time, String story, String like, String comment, String retweet, String image, String uimage) {
        this.name = name;
        this.username = username;
        this.time = time;
        this.story = story;
        this.like = like;
        this.comment = comment;
        this.retweet = retweet;
        this.image = image;
        this.uimage = uimage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getRetweet() {
        return retweet;
    }

    public void setRetweet(String retweet) {
        this.retweet = retweet;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUimage() {
        return uimage;
    }

    public void setUimage(String uimage) {
        this.uimage = uimage;
    }

    public static List<Posts> getPostsList() {
        return postsList;
    }

    public static void setPostsList(List<Posts> postsList) {
        Posts.postsList = postsList;
    }
}
