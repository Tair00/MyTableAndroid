package ru.mvlikhachev.mytablepr.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.material.behavior.SwipeDismissBehavior;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ru.mvlikhachev.mytablepr.Domain.BookingItem;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Interface.ChangeNumberItemsListener;
import ru.mvlikhachev.mytablepr.Helper.ManagementCart;
import ru.mvlikhachev.mytablepr.R;

public class BookingListAdapter extends RecyclerView.Adapter<BookingListAdapter.ViewHolder> {
    private Context context;
    private List<BookingItem> bookingList;

    public BookingListAdapter(Context context, List<BookingItem> bookingList) {
        this.context = context;
        this.bookingList = bookingList;
    }

    public void fetchData() {
        String serverUrl = "https://losermaru.pythonanywhere.com/reservation";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, serverUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Ошибка при получении данных: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("Ошибка при получении данных: " + error.getMessage());
                    }
                });
        queue.add(request);
    }

    private void parseResponse(JSONObject response) {
         try {
            JSONArray jsonArray = response.getJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String status = jsonObject.getString("status");
                if (status == "approved") {
                    String picture = jsonObject.getString("picture");
                    String date = jsonObject.getString("date");
                    String time = jsonObject.getString("time");
                    String name = jsonObject.getString("name");
                    String number = jsonObject.getString("number");
                    bookingList.add(new BookingItem(status,picture, date, time, name,number));
                }
            }
            notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(context, "Ошибка при разборе ответа", Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.booking_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        int count = 0;
        for (BookingItem item : bookingList) {
            String status = item.getStatus();
            if ("approved".equals(status)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int count = 0;
        for (BookingItem item : bookingList) {
            String status = item.getStatus();
            if ("approved".equals(status)) {
                if (count == position) {
                    Picasso.get().load(item.getPicture()).into(holder.imageView);
                    holder.dateTextView.setText(item.getDate());
                    holder.timeTextView.setText(item.getTime());
                    holder.nameTextView.setText(item.getName());
                    holder.number.setText(item.getNumber());
                    break;
                }
                count++;
            }
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Объявление элементов интерфейса booking_item
        // Например:
        public ImageView imageView;
        public TextView dateTextView;
        public TextView timeTextView;
        public TextView nameTextView;
        public TextView number;
        public ViewHolder(View itemView) {
            super(itemView);
            // Инициализация элементов интерфейса booking_item
            // Например:
            imageView = itemView.findViewById(R.id.pic);
            dateTextView = itemView.findViewById(R.id.date);
            timeTextView = itemView.findViewById(R.id.time);
            nameTextView = itemView.findViewById(R.id.title);
            number= itemView.findViewById(R.id.tableNum);
        }
    }
}
