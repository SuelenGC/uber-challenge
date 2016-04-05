package com.suelengc.flickrgrid.view.photos;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.suelengc.flickrgrid.R;
import com.suelengc.flickrgrid.model.Photo;

import java.util.List;

public class PhotosAdapter extends RecyclerView.Adapter<PhotosAdapter.PhotoViewHolder> {

    private final Context context;
    private final List<Photo> images;

    public PhotosAdapter(Context context, List<Photo> images) {
        this.context = context;
        this.images = images;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = (CardView) LayoutInflater.from(context).inflate(R.layout.photo_item, parent, false);
        PhotoViewHolder viewHolder = new PhotoViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Picasso.with(context).load(images.get(position).getUrl()).placeholder(R.drawable.nophoto).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;

        public PhotoViewHolder(View view) {
            super(view);
            this.image = (ImageView) view.findViewById(R.id.photo);
        }
    }
}
