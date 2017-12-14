package com.zuykova.na.clientproducthunt;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ProductHuntApi {
    //Получить категории
    @GET("v1/topics?search[trending]=true&access_token=591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
    Call<TopicLab> getTopics();

    //Topics
    @GET("v1/categories/{slug}/posts?access_token=591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
    Call<PostLab> getPostsForTopic(@Path("slug") String slug);

    //Topics
    @GET("v1/posts/{id}?access_token=591f99547f569b05ba7d8777e2e0824eea16c440292cce1f8dfb3952cc9937ff")
    Call<OnePost> getPostFromId(@Path("id") int id);
}
