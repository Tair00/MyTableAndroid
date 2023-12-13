package ru.mvlikhachev.mytablepr.Interface;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import ru.mvlikhachev.mytablepr.Domain.AuthRequest;
import ru.mvlikhachev.mytablepr.Domain.TokenResponse;

public interface AuthApi {
    @POST("user")
    Call<TokenResponse> signIn(
            @Body AuthRequest request
    );

    @GET("auth")
    Call<Void> authenticate(
            @Header("Authorize") String token
    );
}