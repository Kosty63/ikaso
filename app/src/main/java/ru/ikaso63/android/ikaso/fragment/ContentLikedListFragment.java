package ru.ikaso63.android.ikaso.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.db.JobBd;
import ru.ikaso63.android.ikaso.model.ListContentItem;

import static ru.ikaso63.android.ikaso.activities.SingleFragmentActivity.imagesTopGone;
import static ru.ikaso63.android.ikaso.activities.SingleFragmentActivity.imagesTopVisible;
import static ru.ikaso63.android.ikaso.utils.DecorateItem.addColorItem;
import static ru.ikaso63.android.ikaso.utils.DecorateItem.getImagesNews;

/**
 * Created by home on 11.03.2017.
 */

public class ContentLikedListFragment extends android.support.v4.app.Fragment{

    private RecyclerView mLIkedContentRecycle;
    private TextView tittleTextView;
    private TextView typeContent;
    private ImageView leftImages;
    private LinearLayout linearLayout;
    private static  String ID_NEWS = "ID";
    private List<ListContentItem> mListLikedContentItems;
    LikedContentAdapter likedContentAdapter = new LikedContentAdapter();
    private Paint p = new Paint();

    public void initSwipe(){
        ItemTouchHelper.SimpleCallback ItemSwipe = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                final int fromPos = viewHolder.getAdapterPosition();
                final int toPos = target.getAdapterPosition();
                return true;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                int position = 0;
                String idLikedContent = mListLikedContentItems.get(position).getId();
                new JobBd().deleteContentBd(getActivity(), idLikedContent);
                updateLikedAdapter(position);
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                    float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;

                    if(dX < 0) {
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX,
                                (float) itemView.getTop(), (float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background, p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width,
                                (float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(ItemSwipe);
        itemTouchHelper.attachToRecyclerView(mLIkedContentRecycle);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_news_list, container, false);
        mLIkedContentRecycle = (RecyclerView) view.findViewById(R.id.fragment_news_list_recycle_view);
        mLIkedContentRecycle.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mLIkedContentRecycle.setLayoutManager(new LinearLayoutManager(getActivity()));
        setupAdapterLikedContent();
        initSwipe();
        return view;

    }

    void setupAdapterLikedContent(){
       likedContentAdapter.addItemsLikeAdapter(new JobBd().readContentBd(getActivity()));
       if(isAdded()){
         mLIkedContentRecycle.setAdapter(likedContentAdapter);
       }
    }

    void updateLikedAdapter(int position){
        mListLikedContentItems.remove(position);
        likedContentAdapter.notifyItemRemoved(position);
        likedContentAdapter.notifyDataSetChanged();
        checkContentItem(mListLikedContentItems);

        //likedContentAdapter.notifyItemChanged(position, mListLikedContentItems.size());
    }

    private class LikedContentHolder extends RecyclerView.ViewHolder{

        public LikedContentHolder(View itemView) {
            super(itemView);
            tittleTextView = (TextView) itemView.findViewById(R.id.items_liked_content);
            typeContent = (TextView) itemView.findViewById(R.id.type_content);
            leftImages = (ImageView) itemView.findViewById(R.id.left_images);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.parent_liked_content);
        }

        //Добавление типа контента и картинки
        public void bindLikedKontentItem(ListContentItem item){
            ID_NEWS = item.getKindId();
            tittleTextView.setText(item.getTitle());
            if (ID_NEWS.equals("1")){
                typeContent.setText(R.string.type_news);
                leftImages.setImageResource(getImagesNews(item.getTypeId()));
            }else{
                typeContent.setText(R.string.type_events);
                leftImages.setImageResource(R.drawable.ic_calendar_pensil);
            }
        }
    }

    private class LikedContentAdapter extends RecyclerView.Adapter<LikedContentHolder> {

       public void addItemsLikeAdapter(List<ListContentItem> listContentItems){
           checkContentItem(listContentItems);
        }

        @Override
        public LikedContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view;
            view = layoutInflater.inflate(R.layout.fragment_liked_content, parent,
                        false);
            return new LikedContentHolder(view);
        }

        @Override
        public void onBindViewHolder(final LikedContentHolder likedContentHolder, int position) {

            likedContentHolder.bindLikedKontentItem(mListLikedContentItems.get(likedContentHolder.getAdapterPosition()));

            addColorItem(likedContentHolder.getAdapterPosition(), linearLayout,
                     tittleTextView, typeContent, leftImages);

            likedContentHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ContentMoreFragment contentMoreFragment = new ContentMoreFragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("title", mListLikedContentItems.get(likedContentHolder.getAdapterPosition()).getTitle());
                    bundle.putString("body", mListLikedContentItems.get(likedContentHolder.getAdapterPosition()).getBody());
                    contentMoreFragment.setArguments(bundle);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.fragmentContainer,
                            contentMoreFragment).addToBackStack("contentMore").commit();
                    imagesTopGone();
                }
            });
        }

        @Override
        public int getItemCount() {
            return mListLikedContentItems.size();
        }

    }

    public void checkContentItem(List<ListContentItem> listContentItems){
        if (listContentItems.size() == 0){
            mLIkedContentRecycle.setVisibility(View.GONE);
            imagesTopGone();
        }else{
            mListLikedContentItems = listContentItems;
            imagesTopVisible();
        }
    }

}
