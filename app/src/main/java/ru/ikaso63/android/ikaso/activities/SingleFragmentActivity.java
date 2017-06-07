package ru.ikaso63.android.ikaso.activities;
/**
 * Created by Константин on 14.11.2016.
 */
import android.app.FragmentTransaction;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.db.ContentBaseHelper;
import ru.ikaso63.android.ikaso.fragment.ContactsFragment;
import ru.ikaso63.android.ikaso.fragment.ContentLikedListFragment;
import ru.ikaso63.android.ikaso.fragment.ContentListFragment;
import ru.ikaso63.android.ikaso.fragment.TopImagesFragment;

import static android.view.View.GONE;

/**
 * Created by Константин on 25.10.2016.
 */

public abstract class SingleFragmentActivity extends AppCompatActivity {
    private static FrameLayout mBannersImageView;
    private SQLiteDatabase mDataBase;
    ContentListFragment contentListFragment = new ContentListFragment();
    Bundle bundleList = new Bundle();

    protected abstract Fragment createFragment();

    @LayoutRes
    protected int getLayoutResId(){
        return R.layout.activity_fragment;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());

        mBannersImageView = (FrameLayout) findViewById(R.id.top_images);
        mDataBase = new ContentBaseHelper(this).getWritableDatabase();
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        //Добавляет фрагмент в activity
        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragmentContainer);

        TopImagesFragment mTopImagesFragment = new TopImagesFragment();
        fm.beginTransaction().add(R.id.top_images, mTopImagesFragment).commit();

        //Проверка фрагмента
        if(fragment == null){
            fragment = createFragment();
            fm.beginTransaction().add(R.id.fragmentContainer, fragment).commit();
        }

        //Нижнее меню
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {

            FragmentManager fragmentManager = getSupportFragmentManager();

            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.button_events:
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                                contentListFragment.newInstance("events")).commit();
                        imagesTopVisible();
                        break;
                    case R.id.button_news:
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                                contentListFragment.newInstance("news")).commit();
                        imagesTopVisible();
                        break;
                    case R.id.button_liked:
                        ContentLikedListFragment contentLikedListFragment = new ContentLikedListFragment();
/*                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                                contentLikedListFragment).commit();*/
                            setFragment(contentLikedListFragment);
                        imagesTopVisible();
                        break;
                    case R.id.button_contacts:
                        ContactsFragment contactsActivity = new ContactsFragment();
/*                        Intent intent = new Intent(SingleFragmentActivity.this, ContactsFragment.class);
                        startActivity(intent);*/
                        fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                                contactsActivity).commit();
                        imagesTopGone();
                        break;
                }
                return false;
            }
        });
    }


    public static void imagesTopGone(){
        mBannersImageView.setVisibility(GONE);
    }

    public static void imagesTopVisible(){
        mBannersImageView.setVisibility(View.VISIBLE);
    }

    public void setFragment(Fragment fragment){
        android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment).commit();
    }

}
