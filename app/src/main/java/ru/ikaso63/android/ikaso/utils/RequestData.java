package ru.ikaso63.android.ikaso.utils;

import com.google.gson.JsonArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.Query;
import ru.ikaso63.android.ikaso.model.ImagesItem;

/**
 * Created by home on 12.05.2017.
 */

public interface RequestData {


    @POST("contentApi")
    Call<JsonArray> getContent(@Query("typeContent") String type_content);

    @POST("imagesApi")
    Call<List<ImagesItem>> getImages();
}
