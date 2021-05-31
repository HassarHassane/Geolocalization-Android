package ma.hassar.demo.beans;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

 

@Entity
public class Configuration {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    private int ageMin;
    private int ageMax;
    private String sexe;
    private int rayon;
    
    @ManyToOne
    private User user;

 

    public Configuration() {
        super();
    }

 

    public int getId() {
        return id;
    }

 

    public void setId(int id) {
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
        return "Configuration [id=" + id + ", ageMin=" + ageMin + ", ageMax=" + ageMax + ", sexe=" + sexe + ", rayon="
                + rayon + ", user=" + user + "]";
    }

 

    
}
 