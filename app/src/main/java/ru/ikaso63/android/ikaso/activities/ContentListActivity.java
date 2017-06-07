package ru.ikaso63.android.ikaso.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.fragment.ContentListFragment;
import ru.ikaso63.android.ikaso.fragment.ContentMoreFragment;
import ru.ikaso63.android.ikaso.fragment.TopImagesFragment;

public class ContentListActivity extends SingleFragmentActivity implements ContentMoreFragment.onMoreContentFragment{

    @Override
    protected Fragment createFragment() {
        return ContentListFragment.newInstance();
    }

    @Override
    protected int getLayoutResId() {
        return super.getLayoutResId();
    }

    @Override
    public void moreContentFragment(Bundle bundle) {

    }

}
