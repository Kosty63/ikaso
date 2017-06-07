package ru.ikaso63.android.ikaso.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.ikaso63.android.ikaso.R;
import ru.ikaso63.android.ikaso.adapter.ContentAdapter;
import ru.ikaso63.android.ikaso.adapter.ImagesAdapter;
import ru.ikaso63.android.ikaso.db.JobBd;
import ru.ikaso63.android.ikaso.model.ListContentItem;
import ru.ikaso63.android.ikaso.utils.App;

import static android.content.ContentValues.TAG;
import static ru.ikaso63.android.ikaso.activities.SingleFragmentActivity.imagesTopGone;
import static ru.ikaso63.android.ikaso.activities.SingleFragmentActivity.imagesTopVisible;
import static ru.ikaso63.android.ikaso.utils.DecorateItem.addColorItem;
import static ru.ikaso63.android.ikaso.utils.DecorateItem.getImagesNews;

/**
 * Created by Константин on 15.11.2016.
 */

public class ContentListFragment extends android.support.v4.app.Fragment implements ContentAdapter.CallbackContentAdapter{

    private RecyclerView mNewsRecyclerVie;
    private static  String ID_NEWS = "news";
    private List<ListContentItem> mItems = new ArrayList<>();
    ContentAdapter contentAdapter;


    public static ContentListFragment newInstance(){
        return new ContentListFragment();
    }

    public static ContentListFragment newInstance(String key){
        ContentListFragment contentListFragment = new ContentListFragment();
        final Bundle arguments = new Bundle();
        arguments.putString("ID_NEWS", key);
        contentListFragment.setArguments(arguments);
        return contentListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        if (savedInstanceState == null);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_news_list, container, false);
        if (!isOnline() ){
            Toast.makeText(getActivity().getApplicationContext(),
                    "Нет соединение с интернетом", Toast.LENGTH_LONG).show();
        }else{
            getListContent();
        }
        mNewsRecyclerVie =(RecyclerView) v.findViewById(R.id.fragment_news_list_recycle_view);
        mNewsRecyclerVie.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL));
        mNewsRecyclerVie.setLayoutManager(new LinearLayoutManager(getActivity()));
        contentAdapter = new ContentAdapter(mItems, getActivity());
        mNewsRecyclerVie.setAdapter(contentAdapter);
        imagesTopVisible();
        contentAdapter.setOnShareClickedListener(this);

        return v;
    }

    /*Получаем данные с сервера*/
    public List<ListContentItem> getListContent(){

        final List<ListContentItem> items = new ArrayList<>();

        try {
                ID_NEWS = getArguments().getString("ID_NEWS");
        }catch (Throwable e){
            Log.e(TAG, e.toString());
        }

        App.getApi().getContent(ID_NEWS).enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {

                if (response.body() != null){
                    for (int i = 0; i < response.body().size(); i++){
                        ListContentItem item = new ListContentItem();
                        item.setId(response.body().get(i).getAsJsonObject().get("id").getAsString());
                        item.setPreview(response.body().get(i).getAsJsonObject().get("preview").getAsString());
                        item.setTitle(response.body().get(i).getAsJsonObject().get("title").getAsString());
                        item.setBody(response.body().get(i).getAsJsonObject().get("body").getAsString());
                        item.setKindId(response.body().get(i).getAsJsonObject().get("kind_id").getAsString());
                        item.setTypeId(response.body().get(i).getAsJsonObject().get("type_id").getAsString());
                        items.add(item);
                    }
                }

                if (contentAdapter.getItemCount() != items.size()){
                    for (int i = 0; i < items.size(); i++){
                        mItems.add(items.get(i));
                    }
                }
                mNewsRecyclerVie.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {

            }
        });
        return items;
    }

    //Проверка интернета
    protected boolean isOnline(){
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null) {
            return false;
        } else { return true; }
    }

    @Override
    public void callbackContent(final ContentAdapter.ViewHolder holder) {
        ContentMoreFragment contentMoreFragment = new ContentMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", mItems.get(holder.getAdapterPosition()).getTitle());
        bundle.putString("body", mItems.get(holder.getAdapterPosition()).getBody());
        contentMoreFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, contentMoreFragment).addToBackStack("TopImagesFragment").commit();
        imagesTopGone();

    }

/*    void addLikedContent(final ContentAdapter.ViewHolder holder){

        holder.itemView.findViewById(R.id.add_liked).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new JobBd().addNewsBd(
                        getActivity(),
                        mItems.get(holder.getAdapterPosition()).getId(),
                        mItems.get(holder.getAdapterPosition()).getTitle(),
                        mItems.get(holder.getAdapterPosition()).getPreview(),
                        mItems.get(holder.getAdapterPosition()).getBody(),
                        mItems.get(holder.getAdapterPosition()).getKindId(),
                        mItems.get(holder.getAdapterPosition()).getTypeId()
                );
            }
        });
    }*/

/*    @Override
    public void callbackContent(ListContentItem listContentItem) {

        ContentMoreFragment contentMoreFragment = new ContentMoreFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", listContentItem.getTitle());
        bundle.putString("body", listContentItem.getBody());
        contentMoreFragment.setArguments(bundle);
        getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, contentMoreFragment).addToBackStack("TopImagesFragment").commit();
        imagesTopGone();

    }*/
}
