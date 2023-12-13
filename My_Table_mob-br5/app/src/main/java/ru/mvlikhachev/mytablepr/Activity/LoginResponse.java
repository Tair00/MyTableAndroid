package ru.mvlikhachev.mytablepr.Activity;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("token")
    private String token;

    // Дополнительные поля, если есть
    // ...

    public String getToken() {
        return token;
    }

    // Дополнительные методы доступа к полям, если есть
    // ...
}
