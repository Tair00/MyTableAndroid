package ru.mvlikhachev.mytablepr.Helper;

import com.google.gson.annotations.SerializedName;

import ru.mvlikhachev.mytablepr.Domain.ImgurData;

public class RestaurantImageResponse {
    @SerializedName("data")
    private RestaurantImageData data;

    public RestaurantImageData getData() {
        return data;
    }

    public void setData(RestaurantImageData data) {
        this.data = data;
    }
}

