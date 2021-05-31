package ma.ensa.finalproject.ui.map;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import ma.ensa.finalproject.R;
import ma.ensa.finalproject.retrofit.models.Configuration;
import ma.ensa.finalproject.retrofit.models.Position;
import ma.ensa.finalproject.retrofit.models.User;
import ma.ensa.finalproject.retrofit.network.RetrofitInstance;
import ma.ensa.finalproject.retrofit.service.DataService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback {
    final static int REQUEST_LOCATION = 199;
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private Configuration config;
    private Spinner ami_spinner;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_map, container, false);
        notify(new View(root.getContext()));
        ami_spinner = root.findViewById(R.id.amis_spinner);
        ArrayList<String> amis = new ArrayList<>();
        amis.add("Tout mes amis");
        String imei = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        DataService service = RetrofitInstance.getInstance().create(DataService.class);
        Call<Configuration> request = service.findConfigByImei(imei);
        request.enqueue(new Callback<Configuration>() {
            @Override
            public void onResponse(Call<Configuration> call, Response<Configuration> response) {
                config = response.body();
                String imei = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                Call<List<User>> request = service.findAmis(imei);
                request.enqueue(new Callback<List<User>>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        List<User> users = response.body();

                        for (int i = 0; i < users.size(); i++) {
                            if (users.get(i).getSexe().equals(config.getSexe()) || config.getSexe().equals("Tout")) {
                                int age = 30;
                                String s = users.get(i).getDateNaissance();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                Date d = null;
                                try {
                                    d = sdf.parse(s);
                                    Calendar c = Calendar.getInstance();
                                    c.setTime(d);
                                    int year = c.get(Calendar.YEAR);
                                    int month = c.get(Calendar.MONTH) + 1;
                                    int date = c.get(Calendar.DATE);
                                    LocalDate l1 = LocalDate.of(year, month, date);
                                    LocalDate now1 = LocalDate.now();
                                    Period diff1 = Period.between(l1, now1);
                                    age = diff1.getYears();
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                if (age >= config.getAgeMin() && age < config.getAgeMax()) {
                                    amis.add(users.get(i).getNom() + " " + users.get(i).getPrenom() + "/" + users.get(i).getTelephone());
                                }
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                    }
                });
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(root.getContext(), android.R.layout.simple_spinner_item, amis);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                ami_spinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onFailure(Call<Configuration> call, Throwable t) {
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        enableLoc();
    }

    private void enableLoc() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(getContext())
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                        }

                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(ConnectionResult connectionResult) {
                            Log.d("Location error", "Location error " + connectionResult.getErrorCode());
                        }
                    }).build();
            googleApiClient.connect();
            LocationRequest locationRequest = LocationRequest.create();
            locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(getActivity(), REQUEST_LOCATION);
//                                finish();
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                    }
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
        ami_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //mMap.clear();
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 300000, 10, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        String imei = Settings.Secure.getString(getContext().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
                        DataService service = RetrofitInstance.getInstance().create(DataService.class);
                        Call<User> request = service.findUserByImei(imei);
                        request.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                User user = response.body();

                                DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                Calendar calendar = Calendar.getInstance();
                                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String date = formatter.format(calendar.getTime());
                                Position position = new Position(latitude, longitude, date, user);
                                Call<Position> request = service.savePosition(position);
                                request.enqueue(new Callback<Position>() {
                                    @Override
                                    public void onResponse(Call<Position> call, Response<Position> response) {
                                    }

                                    @Override
                                    public void onFailure(Call<Position> call, Throwable t) {
                                        DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                        Call<List<User>> request = service.findFriends(user.getId());
                                        request.enqueue(new Callback<List<User>>() {
                                            @Override
                                            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                                                List<User> users = response.body();
                                                mMap.clear();
                                                for (int i = 0; i < users.size(); i++) {
                                                    DataService service = RetrofitInstance.getInstance().create(DataService.class);
                                                    Call<Position> request = service.findLastPositionById(users.get(i).getId());
                                                    request.enqueue(new Callback<Position>() {
                                                        @RequiresApi(api = Build.VERSION_CODES.O)
                                                        @Override
                                                        public void onResponse(Call<Position> call, Response<Position> response) {
                                                            Position p = response.body();
                                                            LatLng myPosition = new LatLng(p.getLatitude(), p.getLongitude());
                                                            if (p.getUser().getSexe().equals(config.getSexe()) || config.getSexe().equals("Tout")) {
                                                                int age = 0;
                                                                String s = p.getUser().getDateNaissance();
                                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                                Date d = null;
                                                                try {
                                                                    d = sdf.parse(s);
                                                                    Calendar c = Calendar.getInstance();
                                                                    c.setTime(d);
                                                                    int year = c.get(Calendar.YEAR);
                                                                    int month = c.get(Calendar.MONTH) + 1;
                                                                    int date = c.get(Calendar.DATE);
                                                                    LocalDate l1 = LocalDate.of(year, month, date);
                                                                    LocalDate now1 = LocalDate.now();
                                                                    Period diff1 = Period.between(l1, now1);
                                                                    age = diff1.getYears();
                                                                } catch (ParseException e) {
                                                                    e.printStackTrace();
                                                                }
                                                                if (age >= config.getAgeMin() && age < config.getAgeMax()) {
                                                                    if (!ami_spinner.getSelectedItem().toString().equals("Tout mes amis")) {
                                                                        if (ami_spinner.getSelectedItem().toString().split("/")[1].equals(p.getUser().getTelephone())) {
                                                                            mMap.addMarker(new MarkerOptions().position(myPosition).title(p.getUser().getNom() + " " + p.getUser().getPrenom()));
                                                                            float[] results = new float[1];
                                                                            Location.distanceBetween(position.getLatitude(), position.getLongitude(), p.getLatitude(), p.getLongitude(), results);
                                                                            if (results[0] <= config.getRayon()) {
                                                                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
//                                                                                Intent notificationIntent = new Intent(getContext(), ChatActivity.class);
//                                                                                notificationIntent.putExtra("phone_number", p.getUser().getTelephone());
//                                                                                notificationIntent.putExtra("name", p.getUser().getNom() + " " + p.getUser().getPrenom());
//                                                                                notificationIntent.putExtra("uid", p.getUser().getFirebaseUID());
//                                                                                PendingIntent notificationPendingIntent = PendingIntent.getActivity(getContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                                                Notification notification = new NotificationCompat.Builder(getContext(), "id1")
                                                                                        .setSmallIcon(R.drawable.notif)
                                                                                        .setContentTitle("Ami proche de toi")
                                                                                        .setContentText("Votre ami " + p.getUser().getNom() + " " + p.getUser().getPrenom() + " est à " + (int) Math.ceil(results[0]) + "m de vous!! ")
                                                                                        .setNumber(1)
//                                                                                        .addAction(R.mipmap.logo, "Envoyer un message", notificationPendingIntent)
                                                                                        .build();
                                                                                NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                                                                notificationManager.notify(1, notification);
                                                                            }
                                                                        }
                                                                    } else {
                                                                        mMap.addMarker(new MarkerOptions().position(myPosition).title(p.getUser().getNom() + " " + p.getUser().getPrenom()));
                                                                        float[] results = new float[1];
                                                                        Location.distanceBetween(position.getLatitude(), position.getLongitude(), p.getLatitude(), p.getLongitude(), results);
                                                                        if (results[0] <= config.getRayon()) {
                                                                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition, 15));
//                                                                            Intent notificationIntent = new Intent(getContext(), ChatActivity.class);
//                                                                            notificationIntent.putExtra("phone_number", p.getUser().getTelephone());
//                                                                            notificationIntent.putExtra("name", p.getUser().getNom() + " " + p.getUser().getPrenom());
//
//                                                                            PendingIntent notificationPendingIntent = PendingIntent.getActivity(getContext(), 1, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                                                                            Notification notification = new NotificationCompat.Builder(getContext(), "id1")
                                                                                    .setSmallIcon(R.drawable.notif)
                                                                                    .setContentTitle("Ami proche de toi")
                                                                                    .setContentText("Votre ami " + p.getUser().getNom() + " " + p.getUser().getPrenom() + " est à " + (int) Math.ceil(results[0]) + "m de vous!! ")
                                                                                    .setNumber(1)
//                                                                                    .addAction(R.mipmap.logo, "Envoyer un message", notificationPendingIntent)
                                                                                    .build();
                                                                            NotificationManager notificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
                                                                            notificationManager.notify(1, notification);
                                                                        }
                                                                    }

                                                                }
                                                            }
                                                        }

                                                        @Override
                                                        public void onFailure(Call<Position> call, Throwable t) {
                                                        }
                                                    });
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<List<User>> call, Throwable t) {
                                            }
                                        });

                                    }
                                });

                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {
                            }
                        });
                        //LatLng myPosition = new LatLng(latitude, longitude);
                        //mMap.addMarker(new MarkerOptions().position(myPosition).title("My Position"));
                        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                /*mMap.addCircle(new CircleOptions()
                        .center(myPosition)
                        .radius(100)
                        .strokeWidth(0f)
                        .fillColor(0x5500002A));*/

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        String newStatus = "";
                        switch (status) {
                            case LocationProvider.OUT_OF_SERVICE:
                                newStatus = "OUT_OF_SERVICE";
                                break;
                            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                                newStatus = "TEMPORARILY_UNAVAILABLE";
                                break;
                            case LocationProvider.AVAILABLE:
                                newStatus = "AVAILABLE";
                                break;
                        }
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    public void notify(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel("id1", "Notification", NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("Description");
            notificationChannel.setShowBadge(true);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{500, 1000, 500, 1000, 500});
            NotificationManager nm = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            nm.createNotificationChannel(notificationChannel);
        }
    }
}