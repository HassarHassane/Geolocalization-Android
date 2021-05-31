package ma.ensa.finalproject;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import ma.ensa.finalproject.retrofit.models.Configuration;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText nomInput;
    private EditText prenomInput;
    private EditText telInput;
    private EditText emailInput;
    private EditText dateN;
    private RadioButton maleInput;
    private RadioButton femaleInput;
    private Button add;
    int permission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nomInput = (EditText) findViewById(R.id.nom);
        prenomInput = (EditText) findViewById(R.id.prenom);
        telInput = (EditText) findViewById(R.id.tel);
        emailInput = (EditText) findViewById(R.id.email);
        dateN = (EditText) findViewById(R.id.dateN);
        maleInput = (RadioButton) findViewById(R.id.M);
        femaleInput = (RadioButton) findViewById(R.id.F);
        add = (Button) findViewById(R.id.add);
        permission = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_PHONE_STATE);
        //initialiser la validation
        AwesomeValidation awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //Ajouter validation pour le nom
        awesomeValidation.addValidation(this, R.id.nom, RegexTemplate.NOT_EMPTY, R.string.invalid_name);
        //Ajouter validation pour le prenom
        awesomeValidation.addValidation(this, R.id.prenom, RegexTemplate.NOT_EMPTY, R.string.invalid_name2);
        //Ajouter validation pour la date de naissance
        awesomeValidation.addValidation(this, R.id.dateN, "([12]\\d{3}-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01]))", R.string.invalid_name);
        //Ajouter validation pour le telephone
//        awesomeValidation.addValidation(this, R.id.tel, "^0[5-7]{1}([ _/-]?)[0-9]{2}\\1[0-9]{2}\\1[0-9]{2}\\1[0-9]{2}$", R.string.invalid_tel);
        //Ajouter validation pour l'email
        awesomeValidation.addValidation(this, R.id.email, Patterns.EMAIL_ADDRESS, R.string.invalid_email);


        add.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (awesomeValidation.validate()) {
                    String nom = nomInput.getText().toString();
                    String prenom = prenomInput.getText().toString();
                    String email = emailInput.getText().toString();
                    String telephone = telInput.getText().toString();
                    String date = dateN.getText().toString();

                    String sexe = "";
                    if (maleInput.isChecked()) {
                        sexe = "M";
                    } else {
                        sexe = "F";
                    }
                    String imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    DataService service = RetrofitInstance.getInstance().create(DataService.class);
                    Call<User> request = service.saveUser(new User(nom, prenom, email, telephone, imei, date, sexe));
                    request.enqueue(new Callback<User>() {

                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {

                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            String imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                            DataService service = RetrofitInstance.getInstance().create(DataService.class);
                            Call<User> request = service.findUserByImei(imei);
                            request.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                    Call<Configuration> request = service.saveConfiguration(new Configuration(15,80,"Tout",100,response.body()));
                                    request.enqueue(new Callback<Configuration>() {
                                        @Override
                                        public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                                        }
                                        @Override
                                        public void onFailure(Call<Configuration> call, Throwable t) {
                                        }
                                    });
                                }
                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                }
                            });
                            Intent intent=new Intent(getApplicationContext(),SendOTPActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }

            };


        });

    }
}
