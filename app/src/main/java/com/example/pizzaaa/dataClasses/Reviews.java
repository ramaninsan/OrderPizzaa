package com.example.pizzaaa.dataClasses;

public class Reviews {

    String usernames;
    int userPic;
    String comments;
    String likes;
    String rating;

    public String getUsernames() {
        return usernames;
    }

    public void setUsernames(String usernames) {
        this.usernames = usernames;
    }

    public int getUserPic() {
        return userPic;
    }

    public void setUserPic(int userPic) {
        this.userPic = userPic;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPizzaName() {
        return pizzaName;
    }

    public void setPizzaName(String pizzaName) {
        this.pizzaName = pizzaName;
    }

    String pizzaName;

}
