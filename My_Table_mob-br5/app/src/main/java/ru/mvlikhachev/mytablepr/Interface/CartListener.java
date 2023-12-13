package ru.mvlikhachev.mytablepr.Interface;

public interface CartListener {
    void onResume(String selectedRating);

    void onCartUpdated();
}