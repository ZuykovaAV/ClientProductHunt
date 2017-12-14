package com.zuykova.na.clientproducthunt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class OnePost {
    @SerializedName("post")
    @Expose
    private Post post;

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }
}
