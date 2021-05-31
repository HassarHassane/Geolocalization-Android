package ma.ensa.finalproject.retrofit.models;

import com.google.gson.annotations.SerializedName;

public class Configuration {
    @SerializedName("id")
    private Integer id;
    @SerializedName("ageMin")
    private int ageMin;
    @SerializedName("ageMax")
    private int ageMax;
    @SerializedName("sexe")
    private String sexe;
    @SerializedName("rayon")
    private int rayon;
    @SerializedName("user")
    private User user;

    public Configuration(int ageMin, int ageMax, String sexe, int rayon, User user) {
        this.ageMin = ageMin;
        this.ageMax = ageMax;
        this.sexe = sexe;
        this.rayon = rayon;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAgeMin() {
        return ageMin;
    }

    public void setAgeMin(int ageMin) {
        this.ageMin = ageMin;
    }

    public int getAgeMax() {
        return ageMax;
    }

    public void setAgeMax(int ageMax) {
        this.ageMax = ageMax;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public int getRayon() {
        return rayon;
    }

    public void setRayon(int rayon) {
        this.rayon = rayon;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Configuration{" +
                "id=" + id +
                ", ageMin=" + ageMin +
                ", ageMax=" + ageMax +
                ", sexe='" + sexe + '\'' +
                ", rayon=" + rayon +
                ", user=" + user +
                '}';
    }
}
