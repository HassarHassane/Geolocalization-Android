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


public class AmiAdapter extends RecyclerView.Adapter<AmiAdapter.ViewHolder> {
    private List<AndroidContact> android_contacts;
    private final LayoutInflater mInflater;


    public AmiAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView id_contact2;
        private TextView nom_complet2;
        private TextView num_tel2;
        private Button bloquer;

        public ViewHolder(@NonNull View view) {
            super(view);
            id_contact2 = (TextView) view.findViewById(R.id.id_contact2);
            nom_complet2 = (TextView) view.findViewById(R.id.nom_complet2);
            num_tel2 = (TextView) view.findViewById(R.id.num_tel2);
            bloquer= (Button) view.findViewById(R.id.bloquer);
        }
    }

    @NonNull
    @Override
    public AmiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.ami_item, parent, false);
        return new AmiAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AmiAdapter.ViewHolder holder, int position) {
        AndroidContact current = android_contacts.get(position);
        holder.id_contact2.setText(position + "");
        holder.nom_complet2.setText(current.getAndroid_contact_nom_complet() + "");
        holder.num_tel2.setText(current.getAndroid_contact_num_tel() + "");
        holder.bloquer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String imei = Settings.Secure.getString(view.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                Call<User> request = service.findUserByImei(imei);
                request.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        User u1 = response.body();
                        DataService service = RetrofitInstance.getInstance().create(DataService.class);
                        Call<User> request = service.findUserByTelephone((String) holder.num_tel2.getText());
                        request.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User u2 = response.body();
                                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                String date = formatter.format(calendar.getTime());
                                Ami ami = new Ami(u1, u2, new HashMap<String, Integer>() {{
                                    put("user1Id", u1.getId());
                                    put("user2Id", u2.getId());
                                }}, date, 3);
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

    }
}
