package ru.ikaso63.android.ikaso.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ScrollingView;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.adapter.ImagesAdapter;
import ru.ikaso63.android.ikaso.adapter.ImagesPagerAdapter;
import ru.ikaso63.android.ikaso.model.ImagesItem;
import ru.ikaso63.android.ikaso.utils.App;

import static ru.ikaso63.android.ikaso.activities.SingleFragmentActivity.imagesTopGone;

/**
 * Created by home on 06.05.2017.
 */

public class TopImagesFragment extends android.support.v4.app.Fragment implements ImagesPagerAdapter.CallbackImagesPagerAdapter{

    private ViewPager viewPager;
    private ImagesPagerAdapter mImagesAdapter;
    private LinearLayoutManager mImagesLayoutManager;
    private List<ImagesItem> mListImages;
    int currentPage = 0;
    private Timer timer;
    private RecyclerView mImagesRecyclerView;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.images_view_pager, container, false);

        mListImages = new ArrayList<>();
        viewPager = (ViewPager) view.findViewById(R.id.top_images_view_pager);
        mImagesAdapter = new ImagesPagerAdapter(getActivity(), mListImages);
        viewPager.setAdapter(mImagesAdapter);

        /*Реализация HorizontalScrollView
        mImagesRecyclerView = (RecyclerView) view.findViewById(R.id.top_imafes_view_pager);
        mImagesLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mImagesRecyclerView.setLayoutManager(mImagesLayoutManager);*/
/*        mImagesAdapter = new ImagesAdapter(mListImages, getContext());*/
/*        mImagesRecyclerView.setAdapter(mImagesAdapter);*/
 /*       mImagesAdapter.setOnShareClickedListener(this);*/


        //Получаем картинки от сервера
        App.getApi().getImages().enqueue(new Callback<List<ImagesItem>>() {
            @Override
            public void onResponse(Call<List<ImagesItem>> call, Response<List<ImagesItem>> response) {
                if (response != null){
                    mListImages.addAll(response.body());
                    viewPager.getAdapter().notifyDataSetChanged();
/*                    mImagesRecyclerView.getAdapter().notifyDataSetChanged();*/
                }
            }

            @Override
            public void onFailure(Call<List<ImagesItem>> call, Throwable t) {
                Log.e("errorGetImages", t.toString());
            }
        });
        mImagesAdapter.setOnShareClickedListener(this);
        autoScrolTopImages();

        viewPager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, final MotionEvent motionEvent) {
                    currentPage++;
                return false;
            }
        });
        return view;
    }



/*    @Override
    public void callbackImages(ImagesAdapter.ViewHolder holder) {
        ContentMoreFragment contentMoreFragment = new ContentMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", mListImages.get(holder.getAdapterPosition()).getTitle());
        bundle.putString("body", mListImages.get(holder.getAdapterPosition()).getBody());
        contentMoreFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, contentMoreFragment).addToBackStack("TopImagesFragment").commit();
        imagesTopGone();
    }*/

    private void autoScrolTopImages(){
            final Handler handler = new Handler();
            final Runnable Update = new Runnable() {
                public void run() {
                    if (mListImages.size() > 1){
                        viewPager.setCurrentItem(currentPage++, true);
                    }
                }
            };

            timer = new Timer(); // This will create a new Thread
            timer .schedule(new TimerTask() { // task to be scheduled
                @Override
                public void run() {
                    handler.post(Update);
                }
            }, 500, 5000);
    }

    @Override
    public void callbackImages(ImagesItem imagesItem) {
        ContentMoreFragment contentMoreFragment = new ContentMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", imagesItem.getTitle());
        bundle.putString("body", imagesItem.getBody());
        contentMoreFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, contentMoreFragment).addToBackStack("TopImagesFragment").commit();
        imagesTopGone();
    }

    @Override
    public void onDestroy() {
        timer.cancel();
        super.onDestroy();
    }
}
