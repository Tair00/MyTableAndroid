package ru.mvlikhachev.mytablepr.Interface;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mvlikhachev.mytablepr.Domain.RestaurantResponse;

public interface RestaurantAPI {
    @GET("restaurant/{id}")
    Call<RestaurantResponse> getRestaurantById(@Path("id") String id);
}
