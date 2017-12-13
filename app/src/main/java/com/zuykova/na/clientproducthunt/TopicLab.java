package com.zuykova.na.clientproducthunt;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopicLab {
    @SerializedName("topics")
    @Expose
    private List<Topic> topics = null;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

}

