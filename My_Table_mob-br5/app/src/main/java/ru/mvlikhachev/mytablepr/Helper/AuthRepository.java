package ru.mvlikhachev.mytablepr.Helper;

import retrofit2.Call;

public interface AuthRepository {
    Call<Void> signIn(String username, String password);

    Call<Void> authenticate();
}