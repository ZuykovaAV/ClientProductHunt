package com.zuykova.na.clientproducthunt;


import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class App extends Application{
    private static final String BASE_URL = "https://api.producthunt.com/";

    private static ProductHuntApi sProductHuntApi;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL) //Базовая часть адреса
                .addConverterFactory(GsonConverterFactory.create()) //Конвертер для преобразования JSON'а в объекты
                .build();
        sProductHuntApi = retrofit.create(ProductHuntApi.class);
    }

    public static ProductHuntApi getApi() {
        return sProductHuntApi;
    }
}

