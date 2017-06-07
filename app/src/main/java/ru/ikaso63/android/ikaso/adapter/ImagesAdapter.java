package ru.ikaso63.android.ikaso.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.fragment.ContentMoreFragment;
import ru.ikaso63.android.ikaso.fragment.TopImagesFragment;
import ru.ikaso63.android.ikaso.model.ImagesItem;

/**
 * Created by home on 06.05.2017.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesAdapter.ViewHolder> {

    private List<ImagesItem> mListImages;
    private Context context;
    CallbackImagesAdapter mCallbackImagesAdapter;

    public void setOnShareClickedListener(CallbackImagesAdapter mCallbackImagesAdapter) {
        this.mCallbackImagesAdapter = mCallbackImagesAdapter;
    }


    public interface CallbackImagesAdapter{
        void callbackImages(ViewHolder holder);
    }

    public ImagesAdapter(List<ImagesItem> mListImages, Context context) {
        this.context = context;
        this.mListImages = mListImages;
    }

    @Override
    public ImagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items_images, parent, false);
        ImagesAdapter.ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ImagesAdapter.ViewHolder holder, final int position) {

        final ImagesItem imagesItem = mListImages.get(holder.getAdapterPosition());
        String url = imagesItem.getUrl() + imagesItem.getName_banners();
        Picasso.with(context).load(url).into(holder.mTopBanners);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackImagesAdapter.callbackImages(holder);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mListImages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView mTopBanners;
        private TextView mTestTextiew;
        private ImageView mTopImageView;

        public ViewHolder(View itemView) {
            super(itemView);
/*            mTestTextiew = (TextView) itemView.findViewById(R.id.text_test);*/
            mTopBanners = (ImageView)itemView.findViewById(R.id.top_images);
        }
    }
}
