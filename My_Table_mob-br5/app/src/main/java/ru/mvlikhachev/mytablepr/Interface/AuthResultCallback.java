package ru.mvlikhachev.mytablepr.Interface;

public interface AuthResultCallback<T> {
    void onSuccess(T data);

    void onFailure(AuthError error);
}