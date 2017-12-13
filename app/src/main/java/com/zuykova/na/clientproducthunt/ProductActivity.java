package com.zuykova.na.clientproducthunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ProductActivity extends AppCompatActivity {

    private TextView mTitleTextView;
    private TextView mDescTextView;
    private TextView mUpvotesTextView;
    private ImageView mScreenshotImageView;
    private Button mGetItButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTitleTextView = findViewById(R.id.title_text_view);
        mDescTextView = findViewById(R.id.description_text_view);
        mUpvotesTextView = findViewById(R.id.upvotes_text_view);
        mScreenshotImageView = findViewById(R.id.screenshot_image_view);
        mGetItButton = findViewById(R.id.get_it_button);

        mGetItButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
