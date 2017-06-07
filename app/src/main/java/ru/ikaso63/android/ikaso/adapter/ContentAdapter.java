package ru.ikaso63.android.ikaso.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.fragment.ContentListFragment;
import ru.ikaso63.android.ikaso.model.ListContentItem;

import static ru.ikaso63.android.ikaso.utils.DecorateItem.addColorItem;
import static ru.ikaso63.android.ikaso.utils.DecorateItem.getImagesNews;

/**
 * Created by home on 07.05.2017.
 */

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ViewHolder> {

    private Context context;
    private LinearLayout linearLayout;
    private ToggleButton mLikedButton;
    private ImageView mLeftImages;
    private TextView tittleTextView;
    private TextView previewTextView;
    private List<ListContentItem> mItems = new ArrayList<>();
    private List<ListContentItem> mListContentItems;

    CallbackContentAdapter mCallbackContentAdapter;

    public void setOnShareClickedListener(CallbackContentAdapter mCallbackContentAdapter) {
        this.mCallbackContentAdapter = mCallbackContentAdapter;
    }

    public interface CallbackContentAdapter{
        void callbackContent(ListContentItem listContentItem);
    }

    void addItemsAdapter(List<ListContentItem> listContentItems){
        mListContentItems = listContentItems;
    }

    public ContentAdapter(List<ListContentItem> mItems, Context context) {
        this.context = context;
        this.mItems = mItems;
    }

    @Override
    public ContentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
        ContentAdapter.ViewHolder vh = new ContentAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final ContentAdapter.ViewHolder holder, int position) {

        final ListContentItem listContentItem = mItems.get(holder.getAdapterPosition());
        holder.bindNewsListItem(listContentItem);

        if (listContentItem.getKindId().equals("1")){
            for (int i = 0; i < getItemCount(); i++) {
                mLeftImages.setImageResource(getImagesNews(
                        mItems.get(holder.getAdapterPosition()).getTypeId()));

                addColorItem(holder.getAdapterPosition(), linearLayout,
                        mLikedButton, tittleTextView,mLeftImages);
            }
        }else{
            for (int i = 0; i < getItemCount(); i++) {

                if (holder.getAdapterPosition() % 2 != 0){
                    linearLayout.setBackgroundColor(Color.parseColor("#0766b1"));
                    mLikedButton.setBackgroundColor(Color.parseColor("#0766b1"));
                    tittleTextView.setTextColor(Color.parseColor("#FFFFFF"));
                    mLeftImages.setBackgroundColor(Color.parseColor("#1a6fb8"));
                    mLeftImages.setImageResource(R.drawable.ic_calendar_time);
                }else {
                    mLeftImages.setImageResource(R.drawable.ic_calendar_pensil);
                }
            }

        }

        tittleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallbackContentAdapter.callbackContent(listContentItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(View itemView) {
            super(itemView);
            tittleTextView = (TextView) itemView.findViewById(R.id.tittle_item_content);
/*            previewTextView = (TextView) itemView.findViewById(R.id.preview_item_content);*/
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent_item_layout);
            mLikedButton = (ToggleButton) itemView.findViewById(R.id.add_liked);
            mLeftImages = (ImageView) itemView.findViewById(R.id.images_type);
        }

        public void bindNewsListItem(ListContentItem item){
            tittleTextView.setText(checkCountChar(item.getTitle()));
        }
    }

    //Метод для обрезки заголовка
    private String checkCountChar(String str){
        if (str.length() >= 55){
            str = str.substring(0, 53);
        }
        return str;
    }
}
