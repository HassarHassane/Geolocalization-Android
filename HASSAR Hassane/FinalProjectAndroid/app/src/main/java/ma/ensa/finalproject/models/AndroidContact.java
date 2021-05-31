package ma.ensa.finalproject.models;

public class AndroidContact {
    public String android_contact_nom_complet = "";
    public String android_contact_num_tel = "";
    public int android_contact_ID=0;

    public AndroidContact(String android_contact_nom_complet, String android_contact_num_tel, int android_contact_ID) {
        this.android_contact_nom_complet = android_contact_nom_complet;
        this.android_contact_num_tel = android_contact_num_tel;
        this.android_contact_ID = android_contact_ID;
    }

    public AndroidContact() {
    }

    public String getAndroid_contact_nom_complet() {
        return android_contact_nom_complet;
    }

    public void setAndroid_contact_nom_complet(String android_contact_nom_complet) {
        this.android_contact_nom_complet = android_contact_nom_complet;
    }

    public String getAndroid_contact_num_tel() {
        return android_contact_num_tel;
    }

    public void setAndroid_contact_num_tel(String android_contact_num_tel) {
        this.android_contact_num_tel = android_contact_num_tel;
    }

    public int getAndroid_contact_ID() {
        return android_contact_ID;
    }

    public void setAndroid_contact_ID(int android_contact_ID) {
        this.android_contact_ID = android_contact_ID;
    }
}
