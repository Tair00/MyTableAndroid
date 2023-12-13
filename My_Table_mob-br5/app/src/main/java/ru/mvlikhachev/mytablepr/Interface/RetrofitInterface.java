package ru.mvlikhachev.mytablepr.Interface;



import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Query;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import ru.mvlikhachev.mytablepr.Auth.LoginResult;
import ru.mvlikhachev.mytablepr.Domain.LoginRequest;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public interface RetrofitInterface {

//    @POST("/user")
//    Call<RegistrationResult> register(
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("password") String password
//    );
    @POST("/auth") // Замените на фактический путь к вашему эндпоинту аутентификации
    Call<ResponseBody> login(@Body LoginRequest loginRequest);
    @FormUrlEncoded
    @POST("/user")
    Call<Void> executeSignup(

                             @Field("email") String email,
                             @Field("userPassword") String userPassword,
                             @Field("confirmPassword") String confirmPassword);
    @POST("/auth")
    Call<LoginResult> executeLogin(@Body HashMap<String, String> map);

    @POST("/card")
    Call<Void> executeCard (@Body HashMap<String, String> map);

    @POST("/profile")
    Call<Void> executeProfile (@Body HashMap<String, String> map);

    @POST("/reservation")
    Call<Void> executeReservation (@Body HashMap<String, String> map);

    @POST("/restaurant")
    Call<Void> executeRestaurant(@Body HashMap<String, String> map);

    @POST("/favorite")
    Call<Void> executeFavorite (@Body HashMap<String, String> map);

    @POST("/table")
    Call<Void> executeTable (@Body HashMap<String, String> map);

    @POST("/upload")
    Call<Void> executeUpload (@Body HashMap<String, String> map);

    @GET("getData")
    Call<LoginResult> getData(
            @Query("name") String name,
            @Query("password") String password
    );
    public interface ApiService {
        @GET("/restaurant")
        Call<List<RestoranDomain>> getRestaurants();
    }
}


