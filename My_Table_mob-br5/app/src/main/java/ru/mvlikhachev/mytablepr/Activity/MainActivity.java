package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import ru.mvlikhachev.mytablepr.Adapter.CategoryAdapter;
import ru.mvlikhachev.mytablepr.Adapter.IventAdapter;
import ru.mvlikhachev.mytablepr.Adapter.RestoranAdapter;
import ru.mvlikhachev.mytablepr.Domain.CategoryDomain;
import ru.mvlikhachev.mytablepr.Domain.IventDomain;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ApiService;
import ru.mvlikhachev.mytablepr.R;
import java.lang.Throwable;

public class MainActivity extends AppCompatActivity {
    private RecyclerView.Adapter adapter, adapter2;
    private RecyclerView recyclerViewCategoryList, recyclerViewPopularList, productRecycler;
    static ArrayList<IventDomain> orderlist = new ArrayList<>();
    static ArrayList<RestoranDomain> orderlist1 = new ArrayList<>();
    static ArrayList<CategoryDomain> categoryList = new ArrayList<>();
    static ArrayList<RestoranDomain> fullOrderlist = new ArrayList<>();
    public static IventAdapter iventAdapter;
    static RestoranAdapter priceAdapter;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerViewCategory();
        recyclerViewPopular();
        bottomNavigation();
        fullOrderlist.clear();
        setProductRecycler(orderlist1);


        token = getIntent().getStringExtra("access_token");
        fetchCategoriesFromServer();
        fetchRestaurantsFromServer();
    }
    @Override
    protected void onResume() {
        super.onResume();
        fetchRestaurantsFromServer();
    }
    private void setProductRecycler(ArrayList<RestoranDomain> restorans) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        String email = getIntent().getStringExtra("email");
        System.out.println("====================================" + token);
        productRecycler = findViewById(R.id.restoranRecycler);
        productRecycler.setLayoutManager(layoutManager);
        priceAdapter = new RestoranAdapter(this, email,token);
        productRecycler.setAdapter(priceAdapter);
        productRecycler.smoothScrollToPosition(100000);
        productRecycler.setHasFixedSize(true);
        priceAdapter.updateProducts(restorans);
    }
    private void bottomNavigation() {
        LinearLayout profileBtn = findViewById(R.id.profileBtn);
        LinearLayout homeBtn = findViewById(R.id.homeBtn);
        LinearLayout cartBtn = findViewById(R.id.cartBtn);
        LinearLayout setting = findViewById(R.id.setting);
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(MainActivity.this, BookingListActivity.class);
                String  token = getIntent().getStringExtra("access_token");
                String email = getIntent().getStringExtra("email");
                intent1.putExtra("email", email);
                intent1.putExtra("access_token", token);
                startActivity(intent1);
            }
        });
        homeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        });
        cartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CartActivity.class);
                String  token = getIntent().getStringExtra("access_token");
                String email = getIntent().getStringExtra("email");
                intent.putExtra("email", email);
                intent.putExtra("access_token", token);
                startActivity(intent);
            }
        });
    }
    private void recyclerViewPopular() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPopularList = findViewById(R.id.view2);
        recyclerViewPopularList.setLayoutManager(linearLayoutManager);
        orderlist.clear();
        orderlist.add(new IventDomain("Акция, английский полдник", "ivent", "Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        orderlist.add(new IventDomain("Акция, английский полдник", "ivent", "Закажи столик в ресторане ....\n" + "и получи чашку чая в подарок "));
        adapter2 = new IventAdapter(orderlist);
        recyclerViewPopularList.setAdapter(adapter2);
    }
    private void recyclerViewCategory() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewCategoryList = findViewById(R.id.view1);
        recyclerViewCategoryList.setLayoutManager(linearLayoutManager);
        fetchCategoriesFromServer();


        categoryList.clear(); // Очистите список категорий перед добавлением новых
        // Нет необходимости вручную создавать статические категории, теперь они будут получены с сервера.
        adapter = new CategoryAdapter(categoryList, new CategoryAdapter.OnCategoryClickListener() {
            @Override
            public void onCategoryClick(CategoryDomain category) {
                fetchRestaurantsByCategory(category.getId());
            }
        });


        recyclerViewCategoryList.setAdapter(adapter);

    }

    private void fetchRestaurantsByCategory(int categoryId) {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                // Создание нового запроса с добавленным заголовком авторизации
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();
                System.out.println("------------------Bearer "  + token);
                return chain.proceed(newRequest);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);

        Call<List<RestoranDomain>> call = apiService.getRestaurantsByCategory(categoryId);

        call.enqueue(new Callback<List<RestoranDomain>>() {
            @Override
            public void onResponse(Call<List<RestoranDomain>> call, Response<List<RestoranDomain>> response) {
                if (response.isSuccessful()) {
                    List<RestoranDomain> restaurants = response.body();
                    if (restaurants != null && !restaurants.isEmpty()) {
                        orderlist1.clear();
                        orderlist1.addAll(restaurants);
                        priceAdapter.updateProducts(orderlist1);
                    } else {
                        System.out.println("Нет ресторанов для выбранной категории.");
                    }
                } else {
                    String errorMessage = "Ошибка при получении ресторанов: " + response.code();
                    System.out.println("Ошибка: " + errorMessage);
                }
            }

            @Override
            public void onFailure(Call<List<RestoranDomain>> call, Throwable t) {
                System.out.println("Ошибка: " + t.getMessage());
            }
        });
    }


    private void fetchCategoriesFromServer() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                // Создание нового запроса с добавленным заголовком авторизации
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();

                return chain.proceed(newRequest);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<CategoryDomain>> call = apiService.getCategories();
        call.enqueue(new Callback<List<CategoryDomain>>() {

            @Override
            public void onResponse(Call<List<CategoryDomain>> call, Response<List<CategoryDomain>> response) {

                if (response.isSuccessful()) {
                    List<CategoryDomain> categories = response.body();
                    if (categories != null) {
                        categoryList.clear();

                        categoryList.addAll(categories);
                        adapter.notifyDataSetChanged();
                    }
                } else {

                    String errorMessage = "Ошибка при получении категорий: " + response.code();

                }
            }
            @Override
            public void onFailure(Call<List<CategoryDomain>> call, Throwable t) {
            }
        });
    }

    private void fetchRestaurantsFromServer() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                // Создание нового запроса с добавленным заголовком авторизации
                Request newRequest = originalRequest.newBuilder()
                        .header("Authorization", "Bearer " + token)
                        .build();

                return chain.proceed(newRequest);
            }
        });

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://losermaru.pythonanywhere.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<RestoranDomain>> call = apiService.getRestaurants();
        call.enqueue(new Callback<List<RestoranDomain>>() {
            @Override
            public void onResponse(Call<List<RestoranDomain>> call, Response<List<RestoranDomain>> response) {
                if (response.isSuccessful()) {
                    List<RestoranDomain> restaurants = response.body();
                    if (restaurants != null) {
                        Collections.sort(restaurants, new Comparator<RestoranDomain>() {
                            @Override
                            public int compare(RestoranDomain o1, RestoranDomain o2) {
                                // Сравниваем по убыванию рейтинга
                                return Double.compare(o2.getStar(), o1.getStar());
                            }
                        });

                        // Устанавливаем данные в RecyclerView после сортировки
                        orderlist1.clear();
                        orderlist1.addAll(restaurants);
                        setProductRecycler(orderlist1);
                        priceAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Обработка ошибки
                }
            }

            @Override
            public void onFailure(Call<List<RestoranDomain>> call, Throwable t) {

            }
        });
    }
}
