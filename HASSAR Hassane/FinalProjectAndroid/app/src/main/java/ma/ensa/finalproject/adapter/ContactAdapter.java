package ma.ensa.finalproject.adapter;


import android.content.Context;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ma.ensa.finalproject.R;
import ma.ensa.finalproject.models.AndroidContact;
import ma.ensa.finalproject.retrofit.models.Ami;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
    private List<AndroidContact> android_contacts;
    private final LayoutInflater mInflater;


    public ContactAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_contact;
        private TextView nom_complet;
        private TextView num_tel;
        private Button btn;

        public ViewHolder(@NonNull View view) {
            super(view);
            id_contact = (TextView) view.findViewById(R.id.id_contact);
            nom_complet = (TextView) view.findViewById(R.id.nom_complet);
            num_tel = (TextView) view.findViewById(R.id.num_tel);
            btn = (Button) view.findViewById(R.id.btn);
        }
    }

    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.contact_item, parent, false);
        return new ContactAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ViewHolder holder, int position) {
        AndroidContact current = android_contacts.get(position);
        Log.d("position:", position + "");
        holder.id_contact.setText(position + "");
        holder.nom_complet.setText(current.getAndroid_contact_nom_complet() + "");
        holder.num_tel.setText(current.getAndroid_contact_num_tel() + "");
        holder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imei = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                Call<User> request = service.findUserByImei(imei);
                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User user1 = response.body();
                        Log.d("user1:",user1.toString());
                        DataService service = RetrofitInstance.getInstance().create(DataService.class);
                        Call<User> request = service.findUserByTelephone(current.getAndroid_contact_num_tel());
                        request.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user2 = response.body();
                                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String date = formatter.format(calendar.getTime());
                                Ami ami = new Ami(user1, user2, new HashMap<String, Integer>() {{
                                    put("user1Id", user1.getId());
                                    put("user2Id", user2.getId());
                                }}, date, 1);
                                Call<Ami> request = service.saveAmi(ami);
                                request.enqueue(new Callback<Ami>() {
                                    @Override
                                    public void onResponse(Call<Ami> call, Response<Ami> response) {

                                    }

                                    @Override
                                    public void onFailure(Call<Ami> call, Throwable t) {
                                        android_contacts.remove(current);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position,android_contacts.size());
                                    }
                                });
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
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
    public int getItemCount() {
        return android_contacts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setAndroid_contacts(List<AndroidContact> android_contacts) {
        this.android_contacts = android_contacts;
        Log.d("Size:", android_contacts.size() + "");
    }
}
