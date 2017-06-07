package ru.ikaso63.android.ikaso.utils;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by home on 12.05.2017.
 */

public class App extends Application {

    private static RequestData mRequestData;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                /*.baseUrl("https://translate.yandex.net/")*/
                .baseUrl("http://new.ikaso63.ru/")
                .build();

        mRequestData = retrofit.create(RequestData.class);
    }

    public static  RequestData getApi(){
        return  mRequestData;
    }
}
