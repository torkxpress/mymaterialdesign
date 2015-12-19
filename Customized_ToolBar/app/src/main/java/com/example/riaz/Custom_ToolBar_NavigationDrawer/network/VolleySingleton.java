package com.example.riaz.Custom_ToolBar_NavigationDrawer.network;

import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.example.riaz.Custom_ToolBar_NavigationDrawer.materialtest.MyApplication;

/**
 * Created by riaz on 22/06/15.
 */
public class VolleySingleton {

    private static VolleySingleton sInstance = null;

    private RequestQueue mRequestQueue;

    private ImageLoader imageLoader;
    private VolleySingleton()
    {
        mRequestQueue= Volley.newRequestQueue(MyApplication.getAppContext());
        imageLoader = new ImageLoader(mRequestQueue, new ImageLoader.ImageCache() {

            private LruCache<String, Bitmap> cache = new LruCache<>((int)(Runtime.getRuntime().maxMemory()/1024)/8);

            /*LruCache that holds a strong reference to a limited no of values.
            * diviveded by 1024 to get values in Kbytes and further divide by 8 to give us size of our cache*/

            @Override
            public Bitmap getBitmap(String url) {
                return cache.get(url);
            }

            @Override
            public void putBitmap(String url, Bitmap bitmap) {

                cache.put(url,bitmap);
            }
        });

    }

    public static VolleySingleton getsInstance()
    {
        if(sInstance ==null)
        {
            sInstance = new VolleySingleton();

        }
        return sInstance;
    }

    public RequestQueue getmRequestQueue()
    {

        return mRequestQueue;
    }

    public ImageLoader getImageLoader()
    {
        return imageLoader;
    }

}
