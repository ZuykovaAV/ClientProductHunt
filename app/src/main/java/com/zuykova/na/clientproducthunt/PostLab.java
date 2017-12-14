package com.zuykova.na.clientproducthunt;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostLab {
        @SerializedName("posts")
        @Expose
        private List<Post> posts = null;

        public List<Post> getPosts() {
            return posts;
        }

        public void setPosts(List<Post> posts) {
            this.posts = posts;
        }
}
