package ru.ikaso63.android.ikaso.utils;

import android.graphics.Bitmap;
import android.nfc.Tag;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by home on 18.02.2017.
 */

public class ThumbnailDownloader <T> extends HandlerThread {

    private static final String TAG = "ThumbnailDownloader";
    private static final int MESSAGE_DOWNLOAD = 0;
    private Handler mRequestHandler;
    private ConcurrentMap<T,String> mRequestMap = new ConcurrentHashMap<>();

    public ThumbnailDownloader(){
        super(TAG);
    }

    @Override
    protected void onLooperPrepared() {
        mRequestHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == MESSAGE_DOWNLOAD) {
                    T target = (T) msg.obj;
                    Log.i(TAG, "Got a request for URL: " +
                            mRequestMap.get(target));
                    //handleRequest(target);

                }
            }
        };
    }

    public void queueThumbnail(T target, String url){
        if(url == null){
            mRequestMap.remove(target);
        }else {
            mRequestMap.put(target, url);
            mRequestHandler.obtainMessage(MESSAGE_DOWNLOAD, target).sendToTarget();
        }
        //Log.i(TAG, "Go a url");
    }

}
