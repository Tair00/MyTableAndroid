package ru.mvlikhachev.mytablepr.Helper;

import android.content.SharedPreferences;

import retrofit2.Call;
import ru.mvlikhachev.mytablepr.Interface.AuthApi;

public class AuthRepositoryImpl implements AuthRepository {
    private AuthApi api;
    private SharedPreferences prefs;

    public AuthRepositoryImpl(AuthApi api, SharedPreferences prefs) {
        this.api = api;
        this.prefs = prefs;
    }

    @Override
    public Call<Void> signIn(String username, String password) {
        return null;
    }

    @Override
    public Call<Void> authenticate() {
        return null;
    }
}
