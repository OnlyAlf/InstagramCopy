package com.parse.starter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;

public class UserFeedAdapter extends RecyclerView.Adapter<UserFeedAdapter.ViewHolder> {

    private List<Bitmap> feedImages;

    public UserFeedAdapter(List<Bitmap> feedImages) {
        this.feedImages = feedImages;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_feed_item, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Bitmap bitmap = feedImages.get(position);
        holder.feedImage.setImageBitmap(bitmap);

    }

    @Override
    public int getItemCount() {
        return feedImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView feedImage;

        public ViewHolder(View itemView) {
            super(itemView);
            feedImage = (ImageView) itemView.findViewById(R.id.feedImage);
        }
    }
}
