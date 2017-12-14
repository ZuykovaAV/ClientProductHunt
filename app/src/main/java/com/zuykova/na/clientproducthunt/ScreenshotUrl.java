package com.zuykova.na.clientproducthunt;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ScreenshotUrl {
    @SerializedName("300px")
    @Expose
    private String screenshot;

    public String getScreenshot() {
        return screenshot;
    }

    public void setScreenshot(String screenshot) {
        this.screenshot = screenshot;
    }
}
