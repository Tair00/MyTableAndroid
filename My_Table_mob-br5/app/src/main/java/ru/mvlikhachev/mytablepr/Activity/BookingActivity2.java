package ru.mvlikhachev.mytablepr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import ru.mvlikhachev.mytablepr.Adapter.RestoranAdapter;
import ru.mvlikhachev.mytablepr.Adapter.TableAdapter;
import ru.mvlikhachev.mytablepr.Domain.DataHolder;
import ru.mvlikhachev.mytablepr.Domain.RestoranDomain;
import ru.mvlikhachev.mytablepr.Domain.TableDomain;
import ru.mvlikhachev.mytablepr.Fragment.DatePickerFragment;
import ru.mvlikhachev.mytablepr.Fragment.TimePickerFragment;
import ru.mvlikhachev.mytablepr.Fragment.UserNameFragment;
import ru.mvlikhachev.mytablepr.R;



public class BookingActivity2 extends AppCompatActivity implements UserNameFragment.OnUserNameSetListener {
    private RecyclerView tableRecycler;
    static TableAdapter tableAdapter;
    private String name;
    private String day;
    private String time;
    String token;
    String serverUrl = "https://losermaru.pythonanywhere.com/table";
    static ArrayList<TableDomain> tableList = new ArrayList<>();
    Float price;
    Integer restaurantId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking2);
        showTimePickerDialog();
        tableList.clear();
        setTableRecycler(tableList);
        showDatePickerDialog();
        showDialogFragment();

        token = getIntent().getStringExtra("access_token");
        String feeTxtValue = getIntent().getStringExtra("feeTxt");
        price = Float.parseFloat(feeTxtValue);
        restaurantId = getIntent().getIntExtra("restorantId", 0);

        executeGetRequest();
    }
    private void setTableRecycler(ArrayList<TableDomain> table) {
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 2);
        tableRecycler = findViewById(R.id.table_recycler);
        tableRecycler.setLayoutManager(layoutManager);
        tableAdapter = new TableAdapter(this, table);
        tableAdapter.setOnItemClickListener(new TableAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(TableDomain table) {
                String number = table.getTitle();
                executePostRequest(number, name);
            }
        });
        tableRecycler.setAdapter(tableAdapter);
        tableRecycler.smoothScrollToPosition(0);
        tableRecycler.setHasFixedSize(true);
    }
    public void showTimePickerDialog() {
        TimePickerFragment newFragment = new TimePickerFragment();
        newFragment.setOnTimeSetListener(new TimePickerFragment.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                updateTime(hourOfDay, minute);
            }
        });
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }
    public void showDatePickerDialog() {
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setOnDateSetListener(new DatePickerFragment.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                updateDate(year, month, dayOfMonth);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void executeGetRequest() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, serverUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        parseResponse(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(BookingActivity2.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                int statusCode = response.statusCode;
                if (statusCode == 422) {
                    String errorMessage = "Data validation error";
                    return Response.error(new VolleyError(errorMessage));
                } else {
                    return super.parseNetworkResponse(response);
                }
            }
        };
        queue.add(stringRequest);
    }

    private void parseResponse(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int id = jsonObject.getInt("id");
                String title = jsonObject.getString("number");
                String seat = jsonObject.getString("seat");
                String restId = jsonObject.getString("restaurant_id");
                if (restId.equals(String.valueOf(restaurantId))) {
                    tableList.add(new TableDomain(id, title, seat, restId));
                }
            }
            tableAdapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(BookingActivity2.this, "Error parsing response", Toast.LENGTH_SHORT).show();
        }
    }

    private void showDialogFragment() {
        UserNameFragment userNameFragment = UserNameFragment.newInstance();
        userNameFragment.setOnUserNameSetListener(this);
        userNameFragment.show(getSupportFragmentManager(), "userNameDialog");
    }

    @Override
    public void onUserNameSet(String userName) {
        name = userName;
    }

    private void executePostRequest(String number, String name) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String email = getIntent().getStringExtra("email");
        String restoranPic = getIntent().getStringExtra("restoranPic");
        String userUrl = "https://losermaru.pythonanywhere.com/user/" + email;
        StringRequest userRequest = new StringRequest(Request.Method.GET, userUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject userJson = new JSONObject(response);
                            int userId = userJson.getInt("id");
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("day", day);
                            jsonBody.put("time", time);
                            jsonBody.put("number", number);
                            jsonBody.put("name", name);
                            jsonBody.put("price", price);
                            jsonBody.put("restaurant_id", restaurantId);
                            jsonBody.put("user_id", userId);
                            jsonBody.put("status", "waiting");
                            jsonBody.put("picture", restoranPic);
                            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, "https://losermaru.pythonanywhere.com/reservation", jsonBody,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(BookingActivity2.this, "Reservation successful", Toast.LENGTH_SHORT).show();
                                            Intent intent1 = new Intent(BookingActivity2.this, PuyActivity.class);
                                            startActivity(intent1);
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            String errorMessage = "Error: " + error.getMessage();
                                            Toast.makeText(BookingActivity2.this, errorMessage, Toast.LENGTH_SHORT).show();
                                            if (error.networkResponse != null) {
                                                int statusCode = error.networkResponse.statusCode;
                                                String responseData = new String(error.networkResponse.data);
                                                Log.e("ErrorResponse", "Status Code: " + statusCode);
                                                Log.e("ErrorResponse", "Response Data: " + responseData);
                                            }
                                        }
                                    }) {
                                @Override
                                public Map<String, String> getHeaders() throws AuthFailureError {
                                    Map<String, String> headers = new HashMap<>();

                                    headers.put("Authorization", "Bearer " + token);
                                    return headers;
                                }
                            };

                            queue.add(request);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(BookingActivity2.this, "Error parsing user response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error: " + error.getMessage();
                        Toast.makeText(BookingActivity2.this, errorMessage, Toast.LENGTH_SHORT).show();
                        if (error.networkResponse != null) {
                            int statusCode = error.networkResponse.statusCode;
                            String responseData = new String(error.networkResponse.data);
                            Log.e("ErrorResponse", "Status Code: " + statusCode);
                            Log.e("ErrorResponse", "Response Data: " + responseData);
                        }
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);
                return headers;
            }
        };

        queue.add(userRequest);
    }
    private void updateDate(int year, int month, int dayOfMonth) {
        DatePickerFragment datePickerFragment = (DatePickerFragment) getSupportFragmentManager().findFragmentByTag("datePicker");
        if (datePickerFragment != null) {
            datePickerFragment.updateDate(year, month, dayOfMonth);

        }
        day = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month + 1, dayOfMonth);
    }

    private void updateTime(int hourOfDay, int minute) {
        TimePickerFragment timePickerFragment = (TimePickerFragment) getSupportFragmentManager().findFragmentByTag("timePicker");
        if (timePickerFragment != null) {
            timePickerFragment.updateTime(hourOfDay, minute);
        }
        time = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
    }
}
