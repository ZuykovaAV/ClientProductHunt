package com.zuykova.na.clientproducthunt;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductHuntApi {

    @GET("v1/topics?search[trending]=true&access_token=591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
    Call<List<Topic>> getTopics();
}
