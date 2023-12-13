package ru.mvlikhachev.mytablepr.Helper;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class RestaurantApiClient {
    private static final String BASE_URL = "https://losermaru.pythonanywhere.com/restaurant/";
    private static RestaurantApiService restaurantApiService;

    public static RestaurantApiService getClient() {
        if (restaurantApiService == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            restaurantApiService = retrofit.create(RestaurantApiService.class);
        }
        return restaurantApiService;
    }

    public interface RestaurantApiService {
        @GET("picture")
        Call<RestaurantImageResponse> getRestaurantImage(@Path("picture") String imageId);
    }
}

// Ваш класс RestaurantImageResponse
//public class RestaurantImageResponse {
//    // Код и методы для обработки ответа сервера
//}