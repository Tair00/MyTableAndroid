package ru.mvlikhachev.mytablepr.Activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ru.mvlikhachev.mytablepr.R;

public class PuyActivity extends AppCompatActivity {
    private TextView feeTxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puy);
        feeTxt = findViewById(R.id.priceTxt);

        // Получение значения из SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String title = sharedPreferences.getString("title", "");

        // Используйте полученное значение по своему усмотрению
        feeTxt.setText(title);

    }
}
