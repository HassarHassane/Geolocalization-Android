package ma.ensa.finalproject.retrofit.models;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;

public class Ami {
    @SerializedName("user1")
    private User user1;
    @SerializedName("user2")
    private User user2;
    @SerializedName("id")
    private HashMap<String,Integer> id;
    @SerializedName("date")
    private String date;
    @SerializedName("etat")
    private Integer etat;

    public Ami(User user1, User user2, HashMap<String, Integer> id, String date, Integer etat) {
        this.user1 = user1;
        this.user2 = user2;
        this.id = id;
        this.date = date;
        this.etat = etat;
    }

    public User getUser1() {
        return user1;
    }

    public void setUser1(User user1) {
        this.user1 = user1;
    }

    public User getUser2() {
        return user2;
    }

    public void setUser2(User user2) {
        this.user2 = user2;
    }

    public HashMap<String, Integer> getId() {
        return id;
    }

    public void setId(HashMap<String, Integer> id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getEtat() {
        return etat;
    }

    public void setEtat(Integer etat) {
        this.etat = etat;
    }
}
