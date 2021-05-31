package ma.ensa.finalproject.ui.amis;

import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.finalproject.R;
import ma.ensa.finalproject.adapter.AmiAdapter;
import ma.ensa.finalproject.models.AndroidContact;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmisFragment extends Fragment {

    private List<AndroidContact> android_contactList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_amis, container, false);

        RecyclerView recyclerView = root.findViewById(R.id.rvami);
        final AmiAdapter adapter = new AmiAdapter(root.getContext());

        android_contactList = new ArrayList<AndroidContact>();
        String imei = Settings.Secure.getString(root.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        DataService service = RetrofitInstance.getInstance().create(DataService.class);
        Call<List<User>> request = service.findAmis(imei);
        request.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                List<User> amis = response.body();

                for (int i = 0; i < amis.size(); i++) {
                    AndroidContact android_contact = new AndroidContact();
                    android_contact.setAndroid_contact_ID(amis.get(i).getId());
                    android_contact.setAndroid_contact_nom_complet(amis.get(i).getNom() + " " + amis.get(i).getPrenom());
                    android_contact.setAndroid_contact_num_tel(amis.get(i).getTelephone());

                    android_contactList.add(android_contact);
                    adapter.setAndroid_contacts(android_contactList);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
            }
        });


        return root;
    }
}
