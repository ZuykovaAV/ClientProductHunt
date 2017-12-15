package com.zuykova.na.clientproducthunt;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientProductHuntActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    public static final String TAG = "ClientActivity";
    private static final String KEY_TOPIC_SLUG = "key_topic_slug";
    private static final String KEY_TOPIC_NAME = "key_topic_name";

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private TextView mListIsEmptyTextView;

    TopicLab mTopicLab = null;
    List<Topic> mTopics = null;
    PostLab mPostLab = null;
    List<Post> mPosts = null;
    private String mCurrentTopicSlug;
    private String mCurrentTopicName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_product_hunt);

        mSwipeRefreshLayout = findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mProgressBar = findViewById(R.id.progress_bar);
        mProgressBar.setVisibility(View.VISIBLE);

        mListIsEmptyTextView = findViewById(R.id.list_is_empty);



        requestTopics();
        if (savedInstanceState != null) {
            mCurrentTopicSlug = savedInstanceState.getString(KEY_TOPIC_SLUG, "tech");
            mCurrentTopicName = savedInstanceState.getString(KEY_TOPIC_NAME, "Tech");
            setTitleToolBar(mCurrentTopicName);
            requestPostsForTopics(mCurrentTopicSlug);
        }
        else {
            setTitleToolBar(getString(R.string.tech_title));
            requestPostsForTopics("tech");
        }

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
        if (mPostLab != null) {
            mRecyclerView.setAdapter(new PostAdapter(ClientProductHuntActivity.this, mPostLab.getPosts()));
            mProgressBar.setVisibility(View.INVISIBLE);
            if (mPosts.size() == 0) {
                mListIsEmptyTextView.setVisibility(View.VISIBLE);
            } else {
                mListIsEmptyTextView.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_TOPIC_NAME, mCurrentTopicName);
        outState.putString(KEY_TOPIC_SLUG, mCurrentTopicSlug);
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
        mListIsEmptyTextView.setVisibility(View.INVISIBLE);
        mCurrentTopicSlug = mTopics.get(item.getItemId()).getSlug();
        mCurrentTopicName = mTopics.get(item.getItemId()).getName();
        requestPostsForTopics(mTopics.get(item.getItemId()).getSlug());
        String title = mTopics.get(item.getItemId()).getName();
        setTitleToolBar(title);
        return super.onOptionsItemSelected(item);
    }

    private void setTitleToolBar(String title) {
        this.getSupportActionBar().setTitle(title);
    }

    @Override
    public void onRefresh() {
        updateUI();
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
