package ru.ikaso63.android.ikaso.activities;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.fragment.ContentMoreFragment;

/**
 * Created by home on 25.11.2016.
 */

public class ContentMoreActivity extends SingleFragmentActivity implements ContentMoreFragment.onMoreContentFragment {

    TextView titleNews;
    TextView bodyNews;

    @Override
    protected Fragment createFragment() {
        return new ContentMoreFragment();
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_more);

        Intent intent = getIntent();
        titleNews = (TextView)findViewById(R.id.title_news);
        titleNews.setText(intent.getStringExtra("newsTitle"));
        bodyNews = (TextView)findViewById(R.id.body_news);
        bodyNews.setText(intent.getStringExtra("newsBody"));

    }

    @Override
    public void moreContentFragment(Bundle bundle) {
/*        super.onCreate(bundle);*/
    }
}
