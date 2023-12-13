package ru.mvlikhachev.mytablepr.Adapter;
import com.google.gson.annotations.SerializedName;

import ru.mvlikhachev.mytablepr.Domain.ImgurData;

public class ImgurResponse {
    @SerializedName("data")
    private ImgurData data;

    public ImgurData getData() {
        return data;
    }

    public void setData(ImgurData data) {
        this.data = data;
    }
}
