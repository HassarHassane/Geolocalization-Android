package ma.ensa.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import ma.ensa.finalproject.adapter.ContactAdapter;
import ma.ensa.finalproject.models.AndroidContact;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import ma.ensa.finalproject.ui.amis.AmisFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {
        private List<AndroidContact> android_contactList;
        private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_contact);
        RecyclerView rv=findViewById(R.id.rv);
        final ContactAdapter adapter=new ContactAdapter(this);
        android_contactList = new ArrayList<AndroidContact>();
        btn=findViewById(R.id.ajouter);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), MenuNavigation.class);
                startActivity(intent);
                finish();
            }
        });
        ContentResolver contentResolver = getContentResolver();

        Cursor cursor_Android_Contacts = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);

        if (cursor_Android_Contacts.getCount() > 0) {
            while (cursor_Android_Contacts.moveToNext()) {
                AndroidContact android_contact = new AndroidContact();
                String contact_id = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts._ID));
                String contact_display_name = cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                android_contact.setAndroid_contact_nom_complet(contact_display_name);


                int hasPhoneNumber = Integer.parseInt(cursor_Android_Contacts.getString(cursor_Android_Contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if (hasPhoneNumber > 0) {
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI
                            , null
                            , ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?"
                            , new String[]{contact_id}
                            , null);
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        if (phoneNumber.contains("-")) {
                            if (phoneNumber.replaceAll("\\s+", "").contains("+212")) {
                                android_contact.setAndroid_contact_num_tel(phoneNumber.replaceAll("\\s+", "").split("-")[0] + phoneNumber.replaceAll("\\s+", "").split("-")[1]);
                            } else {
                                android_contact.setAndroid_contact_num_tel("+212" + (phoneNumber.replaceAll("\\s+", "").split("-")[0] + phoneNumber.replaceAll("\\s+", "").split("-")[1]).substring(1));
                            }
                        } else {
                            if (phoneNumber.replaceAll("\\s+", "").contains("+212")) {
                                android_contact.setAndroid_contact_num_tel(phoneNumber.replaceAll("\\s+", ""));
                            } else {
                                android_contact.setAndroid_contact_num_tel("+212" + phoneNumber.replaceAll("\\s+", "").substring(1));
                            }
                        }
                    }

                    phoneCursor.close();
                }

//                Log.d("num:",android_contact.getAndroid_contact_num_tel());
//                Log.d("size:",android_contactList.size()+"");
                String imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                Call<User> request = service.findUserByTelephone(android_contact.getAndroid_contact_num_tel());
                request.enqueue(new Callback<User>() {

                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        android_contactList.add(android_contact);
                        adapter.setAndroid_contacts(android_contactList);
                        rv.setAdapter(adapter);
                        rv.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        Log.d("response",android_contact.getAndroid_contact_num_tel()+"");
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.d("failure",android_contact.getAndroid_contact_num_tel()+"");
                    }
                });
            }
        }
    }
}
