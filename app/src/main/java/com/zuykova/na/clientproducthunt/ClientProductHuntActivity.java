package com.zuykova.na.clientproducthunt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClientProductHuntActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ProductAdapter mAdapter;

    TopicLab mTopicLab = null;
    List<Topic> mTopics = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_product_hunt);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        requestTopics();
        updateUI();
    }

    private void requestTopics() {
        App.getApi().getTopics().enqueue(new Callback<TopicLab>() {
            @Override
            public void onResponse(Call<TopicLab> call, Response<TopicLab> response) {
                //Данные успешно пришли
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        mTopicLab = response.body();
                        if (mTopicLab !=null) {
                            mTopics = mTopicLab.getTopics();
                            Toast.makeText(ClientProductHuntActivity.this, "Все ок" +  mTopics.get(0).getName(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<TopicLab> call, Throwable t) {
                //Произошла ошибка
                Log.i("dfdf", t.toString());
            }
        });
    }

    private void updateUI() {
        ProductLab productLab = ProductLab.get(this);
        List<Product> products = productLab.getProducts();
        mAdapter = new ProductAdapter(products);
        mRecyclerView.setAdapter(mAdapter);
    }


    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private Product mProduct;

        private TextView mTitleTextView;
        private TextView mDescTextView;
        private TextView mUpvotesTextView;
        private ImageView mThumbnailImageView;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_product, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDescTextView = itemView.findViewById(R.id.description_text_view);
            mUpvotesTextView = itemView.findViewById(R.id.upvotes_text_view);
            mThumbnailImageView = itemView.findViewById(R.id.thumbnail_image_view);
        }

        public void bind(Product product) {
            mProduct = product;
            mTitleTextView.setText(mProduct.getTitle());
            mDescTextView.setText(mProduct.getDescription());
            mUpvotesTextView.setText(mProduct.getUpvotes());
            //TODO:добавить картинку
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(v.getContext(), mProduct.getTitle(), Toast.LENGTH_SHORT).show();
        }
    }

    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {
        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            return new ProductHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bind(product);
        }

        @Override
        public int getItemCount() {
            return mProducts.size();
        }
    }
}
