package com.zuykova.na.clientproducthunt;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientProductHuntActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "ClientActivity";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;

    TopicLab mTopicLab = null;
    List<Topic> mTopics = null;
    PostLab mPostLab = null;
    List<Post> mPosts = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_product_hunt);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestTopics();
        requestPostsForTopics("tech");
    }

    private void requestPostsForTopics(String slug) {
        App.getApi().getPostsForTopic(slug).enqueue(new Callback<PostLab>() {
            @Override
            public void onResponse(Call<PostLab> call, Response<PostLab> response) {
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mPostLab = response.body();
                        if (mPostLab != null) {
                            mPosts = mPostLab.getPosts();
                            updateUI();
                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<PostLab> call, Throwable t) {
                Log.i(TAG, t.toString());
            }
        });
    }

    private void requestTopics() {
        App.getApi().getTopics().enqueue(new Callback<TopicLab>() {
            @Override
            public void onResponse(Call<TopicLab> call, Response<TopicLab> response) {
                //Данные успешно пришли
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mTopicLab = response.body();
                        if (mTopicLab != null) {
                            mTopics = mTopicLab.getTopics();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<TopicLab> call, Throwable t) {
                //Произошла ошибка
                Log.i(TAG, t.toString());
            }
        });
    }

    private void updateUI() {
        mRecyclerView.setAdapter(new PostAdapter(ClientProductHuntActivity.this, mPostLab.getPosts()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, 0, Menu.NONE, R.string.Menu_not_loaded);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (mTopics != null) {
            menu.clear();
            for (int i = 0; i < mTopics.size(); i++) {
                menu.add(Menu.NONE, i, Menu.NONE, mTopics.get(i).getName());
            }
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        requestPostsForTopics(mTopics.get(item.getItemId()).getSlug());
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        updateUI();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
