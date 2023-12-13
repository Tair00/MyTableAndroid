package ru.mvlikhachev.mytablepr.Interface;




import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mvlikhachev.mytablepr.Adapter.ImgurResponse;

public interface ImgurApiService {
    @GET("image/{imageId}")
    Call<ImgurResponse> getImgurImage(@Path("imageId") String imageId);
    @GET("imgur/image")
    Call<ImgurResponse> getImgurImage(@Header("Authorization") String authorization, @Query("url") String imageUrl);

}