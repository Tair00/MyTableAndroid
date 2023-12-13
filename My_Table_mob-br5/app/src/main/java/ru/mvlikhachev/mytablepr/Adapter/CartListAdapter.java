package ru.mvlikhachev.mytablepr.Adapter;


import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ru.mvlikhachev.mytablepr.Activity.CartActivity;
import ru.mvlikhachev.mytablepr.Activity.ShowDetailActivity;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;


public class CartListAdapter extends RecyclerView.Adapter<CartListViewHolder> {
    private Context context;

    private ArrayList<RestoranDomain> products;
    private String email, token;
    private Integer restorantId;

    public CartListAdapter(Context context, String email, String token) {
        this.token = token;
        this.context = context;
        this.products = new ArrayList<>();
        this.email = email;
    }

    public void updateProducts(ArrayList<RestoranDomain> newProducts) {
        products.clear();
        products.addAll(newProducts);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CartListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.restoran_item, parent, false);
        return new CartListViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListViewHolder holder, int position) {
        ArrayList<RestoranDomain> sortedProducts = new ArrayList<>(products);
        Collections.sort(sortedProducts, new Comparator<RestoranDomain>() {
            @Override
            public int compare(RestoranDomain o1, RestoranDomain o2) {
                // Сравниваем по убыванию рейтинга
                return Double.compare(o2.getStar(), o1.getStar());
            }
        });

        RestoranDomain product = products.get(position);


        holder.productTitles.setText(product.getName());
        holder.productPrice.setText(String.valueOf(product.getPrice()));

        Picasso.get().load(product.getPicture()).into(holder.productImage);

        holder.grade.setText(String.valueOf(product.getStar()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity) context,
                        new Pair<View, String>(holder.productImage, "productImage"));
                restorantId = product.getId();
                Intent intent = new Intent(holder.itemView.getContext(), ShowDetailActivity.class);
                intent.putExtra("email", email);
                intent.putExtra("access_token", token);
                intent.putExtra("object", product);
                intent.putExtra("restorantId", restorantId);
                holder.itemView.getContext().startActivity(intent, options.toBundle());
            }
        });

        Animation animation = AnimationUtils.loadAnimation(context, R.anim.bounce_animation);
        holder.itemView.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setTouchHelper(RecyclerView recyclerView) {
        ItemTouchHelper.SimpleCallback touchHelperCallback = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT
        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int position = viewHolder.getAdapterPosition();
                RestoranDomain deletedItem = products.get(position); // Получаем удаляемый элемент
                int restaurantId = deletedItem.getId(); // Получаем ID удаляемого ресторана

                // Выполняем DELETE запрос для удаления элемента по ID
                executeDeleteRequest(restaurantId);

                // Удаление элемента из списка и уведомление адаптера
                products.remove(position);
                notifyItemRemoved(position);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(touchHelperCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void executeDeleteRequest(int favId) {
        String url = "https://losermaru.pythonanywhere.com/favorite/" + favId;

        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("УДАЛИЛ");
                        // Обработка успешного ответа DELETE запроса
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Обработка ошибки DELETE запроса
                        System.out.println(" НЕ УДАЛИЛ");
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(stringRequest);

    }
}

