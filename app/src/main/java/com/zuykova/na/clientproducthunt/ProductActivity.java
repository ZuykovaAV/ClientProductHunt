package com.zuykova.na.clientproducthunt;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductActivity extends AppCompatActivity {
    private static final String EXTRA_POST_ID = "com.zuykova.na.clientproducthunt.post_id";
    private static final String TAG = "ProductActivity";

    private int mId;
    private Post mPost;

    private TextView mTitleTextView;
    private TextView mDescTextView;
    private TextView mUpvotesTextView;
    private ImageView mScreenshotImageView;
    private Button mGetItButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mTitleTextView = findViewById(R.id.title_text_view);
        mDescTextView = findViewById(R.id.description_text_view);
        mUpvotesTextView = findViewById(R.id.upvotes_text_view);
        mScreenshotImageView = findViewById(R.id.screenshot_image_view);
        mGetItButton = findViewById(R.id.get_it_button);

        mId = getIntent().getIntExtra(EXTRA_POST_ID, 0);
        requestPostFromId(mId);

        updateUI();
    }

    private void updateUI() {
        if (mPost != null) {
            mTitleTextView.setText(mPost.getTitle());
            mDescTextView.setText(mPost.getDescription());
            mUpvotesTextView.setText("upvotes: " + String.format("%d", mPost.getUpvotes()));
            Picasso.with(this)
                    .load(mPost.getScreenshotUrl().getScreenshot())
                    .placeholder(android.R.drawable.gallery_thumb)
                    .error(android.R.drawable.gallery_thumb)
                    .into(mScreenshotImageView);

            mGetItButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uri = Uri.parse(mPost.getRedirectUrl()).buildUpon().build();
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                }
            });
        }
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, ProductActivity.class);
        intent.putExtra(EXTRA_POST_ID, id);
        return intent;

    }

    private void requestPostFromId(int id) {
        App.getApi().getPostFromId(id).enqueue(new Callback<OnePost>() {
            @Override
            public void onResponse(Call<OnePost> call, Response<OnePost> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mPost = response.body().getPost();
                        updateUI();
                    }
                }
            }

            @Override
            public void onFailure(Call<OnePost> call, Throwable t) {
                Log.i(TAG, t.toString());
            }
        });
    }
}
