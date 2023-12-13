package ru.mvlikhachev.mytablepr.Interface;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import ru.mvlikhachev.mytablepr.Domain.CategoryDomain;

public interface CategoryService {
    @GET("category")
    Call<List<CategoryDomain>> getCategories();
}