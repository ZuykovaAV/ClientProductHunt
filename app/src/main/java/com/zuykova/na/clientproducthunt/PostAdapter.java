package com.zuykova.na.clientproducthunt;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private Context mContext;
    private List<Post> mPosts;

    public PostAdapter(Context context, List<Post> posts) {
        mContext = context;
        mPosts = posts;
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.list_item_product, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PostViewHolder holder, int position) {
        Post post = mPosts.get(position);
        holder.mTitleTextView.setText(post.getTitle());
        holder.mDescTextView.setText(post.getDescription());
        holder.mUpvotesTextView.setText("upvotes: " + String.format("%d", post.getUpvotes()));
        Picasso.with(mContext)
                .load(post.getThumbnail().getThumbnailUrl())
                .placeholder(android.R.drawable.gallery_thumb)
                .error(android.R.drawable.gallery_thumb)
                .into(holder.mThumbnailImageView);

    }

    @Override
    public int getItemCount() {
        if (mPosts != null) {
            return mPosts.size();
        }
        return 0;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {
        //private Post mPost;

        private TextView mTitleTextView;
        private TextView mDescTextView;
        private TextView mUpvotesTextView;
        private ImageView mThumbnailImageView;

        public PostViewHolder(View itemView) {
            super(itemView);

            mTitleTextView = itemView.findViewById(R.id.title_text_view);
            mDescTextView = itemView.findViewById(R.id.description_text_view);
            mUpvotesTextView = itemView.findViewById(R.id.upvotes_text_view);
            mThumbnailImageView = itemView.findViewById(R.id.thumbnail_image_view);
        }
    }
}
