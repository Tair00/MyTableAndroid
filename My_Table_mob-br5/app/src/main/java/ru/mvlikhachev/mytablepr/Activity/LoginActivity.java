package ru.mvlikhachev.mytablepr.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import retrofit2.Retrofit;
import ru.mvlikhachev.mytablepr.R;
import ru.mvlikhachev.mytablepr.Interface.RetrofitInterface;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private RetrofitInterface retrofitInterface;
    String serverUrl = "https://losermaru.pythonanywhere.com/auth";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView signUp = findViewById(R.id.signUp);
        ConstraintLayout button = findViewById(R.id.login);
        EditText mail = findViewById(R.id.nameEdit);
        EditText password = findViewById(R.id.passwordEdit);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = mail.getText().toString();
                String userPassword = password.getText().toString();

                new LoginAsyncTask().execute(email, userPassword);
            }
        });
    }

    private class LoginAsyncTask extends AsyncTask<String, Void, Boolean> {
        private String email; // Объявляем поле email
        private String token;

        @Override
        protected Boolean doInBackground(String... params) {
            String email = params[0];
            String userPassword = params[1];

            try {
                URL url = new URL(serverUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setDoOutput(true);

                JSONObject jsonParams = new JSONObject();
                jsonParams.put("email", email);
                jsonParams.put("password", userPassword);

                DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
                outputStream.writeBytes(jsonParams.toString());
                outputStream.close();

                int responseCode = connection.getResponseCode();

                if (responseCode == 200) {
                    // Чтение ответа сервера
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    // Извлечение access_token из ответа JSON
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    token = jsonResponse.getString("access_token");

                    // Проверка успешности аутентификации
                    this.email = email; // Устанавливаем значение email
                    return true;
                } else {
                    return false;
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                // Аутентификация прошла успешно, отправляем данные email и переходим на MainActivity
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("access_token", token);
                System.out.println("===================================="+token);
                intent.putExtra("email", email); // Используем поле email
                startActivity(intent);
            } else {
                // Ошибка аутентификации, выполните соответствующие действия
                Toast.makeText(LoginActivity.this,
                        "Неправильный логин или пароль", Toast.LENGTH_LONG).show();
            }
        }
    }
}

