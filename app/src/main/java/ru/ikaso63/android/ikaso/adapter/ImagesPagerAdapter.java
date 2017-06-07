package ru.ikaso63.android.ikaso.adapter;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.model.ImagesItem;

/**
 * Created by home on 26.05.2017.
 */

public class ImagesPagerAdapter extends PagerAdapter {

    private List<ImagesItem> mListImages;
    private Context context;
    private ImageView mTopBanners;
    private LayoutInflater inflater;
    private View itemView;
    CallbackImagesPagerAdapter mCallbackImagesPagerAdapter;

    public void setOnShareClickedListener(CallbackImagesPagerAdapter callbackImagesPagerAdapter) {
        this.mCallbackImagesPagerAdapter = callbackImagesPagerAdapter;
    }

    public interface CallbackImagesPagerAdapter{
        void callbackImages(ImagesItem imagesItem);
    }

    public ImagesPagerAdapter(Context context, List<ImagesItem> mListImages){
        this.context = context;
        this.mListImages = mListImages;
    }

    @Override
    public int getCount() {
        if (getRealCount() > 1) {
            return Integer.MAX_VALUE;
        }
        // warning: scrolling to very high values (1,000,000+) results in
        // strange drawing behaviour
        return mListImages.size();
    }

    public int getRealCount() {
        return mListImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int virtualPosition = position % getRealCount();
        inflater = (LayoutInflater) context.getApplicationContext().getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.items_images, container, false);
        ImageView topImageView = (ImageView) itemView.findViewById(R.id.top_images);
        final ImagesItem imagesItem = mListImages.get(virtualPosition);
        String url = imagesItem.getUrl() + imagesItem.getName_banners();
        Picasso.with(context).load(url).into(topImageView);
        container.addView(itemView);

        topImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackImagesPagerAdapter.callbackImages(imagesItem);
            }
        });
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int virtualPosition = position % getRealCount();
        // only expose virtual position to the inner adapter
        container.removeView((View) object);

    }

}