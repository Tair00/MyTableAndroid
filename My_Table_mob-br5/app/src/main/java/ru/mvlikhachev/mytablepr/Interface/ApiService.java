package ru.mvlikhachev.mytablepr.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.mvlikhachev.mytablepr.Domain.CategoryDomain;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;

public interface ApiService {
    @GET("/restaurant/restaurants/category/{category_id}")
    Call<List<RestoranDomain>> getRestaurantsByCategory(@Path("category_id") int categoryId);


    @GET("/restaurant")
    Call<List<RestoranDomain>> getRestaurants();
    @GET("/category")
    Call<List<CategoryDomain>> getCategories();
    @GET("restaurant/{id}")
    Call<RestoranDomain> getRestaurantById(@Path("id") int id);

}
