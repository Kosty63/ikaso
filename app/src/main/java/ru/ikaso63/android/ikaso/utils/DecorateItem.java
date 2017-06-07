package ru.ikaso63.android.ikaso.utils;

import android.graphics.Color;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import ru.ikaso63.android.ikaso.R;

/**
 * Created by home on 13.05.2017.
 */

public class DecorateItem {

    public static int getImagesNews(String typeId){

        if (typeId.equals("1")){
            return R.drawable.ic_economic;
        }else if(typeId.equals("2")){
            return R.drawable.ic_jurist;
        }else {
            return R.drawable.ic_other;
        }

    }

    public static void addColorItem(int colItem, LinearLayout linearLayout, ToggleButton mLikedButton,
                                    TextView tittleTextView, ImageView mLeftImages) {
        if (colItem % 2 != 0) {
            linearLayout.setBackgroundColor(Color.parseColor("#0766b1"));
            mLikedButton.setBackgroundColor(Color.parseColor("#0766b1"));
            tittleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            mLeftImages.setBackgroundColor(Color.parseColor("#1a6fb8"));
        }
    }

    public static void addColorItem(int colItem, LinearLayout linearLayout,
                                    TextView tittleTextView, TextView typeContent, ImageView mLeftImages) {
        if (colItem % 2 != 0) {
            linearLayout.setBackgroundColor(Color.parseColor("#0766b1"));
            tittleTextView.setTextColor(Color.parseColor("#FFFFFF"));
            typeContent.setTextColor(Color.parseColor("#FFFFFF"));
            mLeftImages.setBackgroundColor(Color.parseColor("#1a6fb8"));
        }

    }
}