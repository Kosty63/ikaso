package ru.ikaso63.android.ikaso.utils;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import ru.ikaso63.android.ikaso.model.ListContentItem;

/**
 * Created by home on 20.11.2016.
 */

public class NewsFetchr {

    private static final String TAG = "NewsFetchr";

    public byte[] getUrlBytes(String urlSpec) throws IOException{
        URL url = new URL(urlSpec);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InputStream in = connection.getInputStream();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                throw new IOException(connection.getResponseMessage()+ ": with " + urlSpec);
            }
            int bytesRead = 0;
            byte[] buffer = new byte[1024];
            while ((bytesRead = in.read(buffer))  > 0){
                out.write(buffer, 0, bytesRead);
            }
            out.close();
            return out.toByteArray();
        }finally{
            connection.disconnect();
        }
    }

    public String getUrlString(String utlSpec) throws IOException{
        return new String(getUrlBytes(utlSpec));
    }

    public List<ListContentItem> newsItems(String url){

        List<ListContentItem> items = new ArrayList<>();

        try {
            Uri.parse(url).toString();
            String jsonString = getUrlString(url);
            Log.i(TAG, " String" + jsonString);
            //JSONObject jsonBody = new JSONObject(jsonString);
            JSONArray jsonArray = new JSONArray(jsonString);
            parseItems(items, jsonArray);
        } catch (JSONException je) {
            Log.e(TAG, "Error ", je);
        }catch (IOException ioe) {
            Log.e(TAG, "Error ", ioe);
        }
        return items;
    }

    public void parseItems(List<ListContentItem> items, JSONArray jsonBody)
            throws IOException, JSONException{
        //JSONObject listNewsJsonObject = new JSONObject(jsonBody.toString());
        //JSONArray listNews = listNewsJsonObject.getJSONArray("values");
        //JSONArray newsJsonArray = new JSONArray();
        for (int i = 0; i < jsonBody.length(); i++){
            JSONObject newsJsonObject = jsonBody.getJSONObject(i);

                ListContentItem item = new ListContentItem();
                item.setId(newsJsonObject.getString("id"));
                item.setPreview(newsJsonObject.getString("preview"));
                item.setTitle(newsJsonObject.getString("title"));
                item.setBody(newsJsonObject.getString("body"));
                item.setKindId(newsJsonObject.getString("kind_id"));
                item.setTypeId(newsJsonObject.getString("type_id"));
                items.add(item);
        }
    }

}
