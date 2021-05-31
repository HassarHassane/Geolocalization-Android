package ma.ensa.finalproject.ui.config;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import ma.ensa.finalproject.R;
import ma.ensa.finalproject.retrofit.models.Configuration;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfigurationFragment extends Fragment {

    private EditText ageMinInput;
    private EditText ageMaxInput;
    private EditText rayonInput;
    private RadioButton maleInput;
    private RadioButton femaleInput;
    private RadioButton tout;
    private Button save;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.configuration_fragment, container, false);
        ageMinInput = (EditText) root.findViewById(R.id.ageMin);
        ageMaxInput = (EditText) root.findViewById(R.id.ageMax);
        rayonInput = (EditText) root.findViewById(R.id.rayon);
        maleInput = (RadioButton) root.findViewById(R.id.M);
        femaleInput = (RadioButton) root.findViewById(R.id.F);
        tout = (RadioButton) root.findViewById(R.id.tout);
        save=(Button)  root.findViewById(R.id.add);

        String imei = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        DataService service = RetrofitInstance.getInstance().create(DataService.class);
        Call<Configuration> request = service.findConfigByImei(imei);
        request.enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
               Configuration configuration=response.body();
               ageMinInput.setText(configuration.getAgeMin()+"");
               ageMaxInput.setText(configuration.getAgeMax()+"");
               rayonInput.setText(configuration.getRayon()+"");
               if(configuration.getSexe().equals("M")){
                   maleInput.toggle();
               }else if(configuration.getSexe().equals("F")){
                   femaleInput.toggle();
               }else{
                   tout.toggle();
               }
               save.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       String sexe = "";
                       if (maleInput.isChecked()) {
                           sexe = "M";
                       } else if(femaleInput.isChecked()) {
                           sexe = "F";
                       }else{
                           sexe="Tout";
                       }
                       String ageMin = ageMaxInput.getText().toString();
                       String ageMax = ageMinInput.getText().toString();
                       String rayon = rayonInput.getText().toString();
                       String imei = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                       DataService service = RetrofitInstance.getInstance().create(DataService.class);
                       Call<User> request = service.findUserByImei(imei);
                       String finalSexe = sexe;
                       request.enqueue(new Callback<User>() {
                           @Override
                           public void onResponse(Call<User> call, Response<User> response) {
                               DataService service = RetrofitInstance.getInstance().create(DataService.class);
                               Call<Configuration> request = service.saveConfiguration(new Configuration(Integer.parseInt(ageMin),Integer.parseInt(ageMax), finalSexe,Integer.parseInt(rayon),response.body()));
                               request.enqueue(new Callback<Configuration>() {
                                   @Override
                                   public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                                   }
                                   @Override
                                   public void onFailure(Call<Configuration> call, Throwable t) {
                                       Toast.makeText(getContext(),"Votre configuration a ete bien enregistre",Toast.LENGTH_LONG).show();
                                   }
                               });
                           }
                           @Override
                           public void onFailure(Call<User> call, Throwable t) {

                           }
                       });
                   }

               });

            }
            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {

            }
        });
        return root;
    }

}
