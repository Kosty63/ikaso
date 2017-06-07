package ru.ikaso63.android.ikaso.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ru.ikaso63.android.ikaso.R;

/**
 * Created by home on 23.11.2016.
 */

public class ContentMoreFragment extends Fragment {

    private TextView titleNews;
    private TextView bodyNews;
    private String body;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_news_more, null);
        bodyNews = (TextView)v.findViewById(R.id.body_news);
        body = getArguments().getString("body").replaceAll("\\<.*?>", "");
/*        this.body.replaceAll("\\&.*?;", "");*/
        bodyNews.setText(body.replaceAll("\\&.*?;", ""));
        titleNews = (TextView)v.findViewById(R.id.title_news);
        titleNews.setText(getArguments().getString("title"));
        return v;
    }


    public interface onMoreContentFragment{
        void moreContentFragment(Bundle bundle);
    }

    onMoreContentFragment moreContentFragment;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        moreContentFragment = (onMoreContentFragment) context;
    }

}
