package kr.heek.goline.utils.volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

/**
 * Created by HYEONSU on 2015-02-16.
 * Modified by Junha on 2016-07-14.
 */
public class VolleyManager {
    private static VolleyManager ourInstance = null;
    private RequestQueue queue;
    private ImageLoader mImageLoader;

    private VolleyManager(Context context) {
        queue = Volley.newRequestQueue(context);
        mImageLoader = new ImageLoader(this.queue, new ImageLoader.ImageCache() {
            private final LruCache<String, Bitmap> mCache = new LruCache(10);

            public void putBitmap(String url, Bitmap bitmap) {
                mCache.put(url, bitmap);
            }

            public Bitmap getBitmap(String url) {
                return mCache.get(url);
            }
        });
    }

    public static VolleyManager getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new VolleyManager(context);
        }
        return ourInstance;
    }

    public RequestQueue getRequestQueue() {
        return this.queue;
    }

    public Request pushQueue(Request request) {
        Log.i("VolleyManager", "Request queued: " + request.getUrl());
        return queue.add(request);
    }

    public ImageLoader getImageLoader() {
        return this.mImageLoader;
    }
}
