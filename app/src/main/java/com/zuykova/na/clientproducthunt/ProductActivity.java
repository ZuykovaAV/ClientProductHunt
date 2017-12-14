package com.zuykova.na.clientproducthunt;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ProductActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "com.zuykova.na.clientproducthunt.post_id";

    private int mId;

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

        mId = getIntent().getIntExtra(EXTRA_POST_ID, 0);

//        mGetItButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(this, "444", Toast.LENGTH_SHORT).show();
//
//            }
 //       });
    }

    public static Intent newIntent(Context context, int id ){
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRA_POST_ID, id);
        return intent;

    }
}
