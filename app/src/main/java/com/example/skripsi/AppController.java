package com.example.skripsi;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
    private static AppController instance;
    RequestQueue mRequestqueue;

    @Override
    public void onCreate() {
        super.onCreate();
        instance =this;
    }

    public static synchronized AppController getInstance(){
        return instance;
    }

    private RequestQueue getmRequestqueue(){
        if(mRequestqueue == null){
            mRequestqueue = Volley.newRequestQueue(getApplicationContext());
        }
        return  mRequestqueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag){
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getmRequestqueue().add(req);
    }

    public <T> void addToRequestQueue (Request<T> req){
        req.setTag(TAG);
        getmRequestqueue().add(req);
    }

    public void cancelAllRequest(Object req){
        if (mRequestqueue != null){
            mRequestqueue.cancelAll(req);
        }
    }
}
