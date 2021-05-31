package ma.ensa.finalproject;


import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {


    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        logo = findViewById(R.id.logo);
        //to hide the actionbar/title from starting activity
//        this.getSupportActionBar().hide();

        //Appliquer une animation de rotation sur le logo
        //res->anim->anim.xml

        Animation aniRotate = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim);
        logo.startAnimation(aniRotate);

        Thread t1 = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);

                    String imei = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                    DataService service = RetrofitInstance.getInstance().create(DataService.class);
                    Call<User> request = service.findUserByImei(imei);
                    request.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Intent intent = new Intent(SplashActivity.this, MenuNavigation.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                            startActivity(intent);
                            SplashActivity.this.finish();
                        }
                    });
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        t1.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finish();
    }
}


