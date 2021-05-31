package ma.ensa.finalproject.retrofit.service;

import java.util.List;


import ma.ensa.finalproject.retrofit.models.Ami;
import ma.ensa.finalproject.retrofit.models.Configuration;
import ma.ensa.finalproject.retrofit.models.Position;
import ma.ensa.finalproject.retrofit.models.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DataService {
    @POST("/users/save")
    Call<User> saveUser(@Body User user);

    @GET("/users/findUserByImei/{imei}")
    Call<User> findUserByImei(@Path("imei") String imei);

    @GET("/users/findUserByTelephone/{telephone}")
    Call<User> findUserByTelephone(@Path("telephone") String telephone);

    @GET("/users/findAmis/{id}")
    Call<List<User>> findFriends(@Path("id") int id);

    @POST("/amis/save")
    Call<Ami> saveAmi(@Body Ami ami);

    @GET("/amis/findAmis/{imei}")
    Call<List<User>> findAmis(@Path("imei") String imei);

    @GET("/amis/findInvitations/{imei}")
    Call<List<User>> findInvitations(@Path("imei") String imei);

    @GET("/amis/findAmi/{id1}/{id2}")
    Call<Ami> findAmi(@Path("id1") int id1, @Path("id2") int id2);

    @POST("/positions/save")
    Call<Position> savePosition(@Body Position position);

    @GET("/positions/findLastPositionById/{id}")
    Call<Position> findLastPositionById(@Path("id") int id);

    @POST("/configurations/save")
    Call<Configuration> saveConfiguration(@Body Configuration configuration);

    @GET("/configurations/findConfigByImei/{imei}")
    Call<Configuration> findConfigByImei(@Path("imei") String imei);
}
