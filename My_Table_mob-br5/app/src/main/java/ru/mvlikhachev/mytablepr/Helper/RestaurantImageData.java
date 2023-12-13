package ru.mvlikhachev.mytablepr.Helper;

import com.google.gson.annotations.SerializedName;

public class RestaurantImageData {
    @SerializedName("link")
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
