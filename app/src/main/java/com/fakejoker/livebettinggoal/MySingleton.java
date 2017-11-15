package com.fakejoker.livebettinggoal;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by FakeJoker on 15/08/2017.
 */

public class MySingleton {
    private static MySingleton mInstance;
    private static Context mCtx;
    private static RequestQueue requestQueue;

    private MySingleton(Context context){
        mCtx=context;
        requestQueue=getRequestQueue();
    }

    private RequestQueue getRequestQueue(){
        if (requestQueue==null){
            requestQueue= Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;
    }
    public static synchronized MySingleton getmInstance(Context context){
        if (mInstance==null){
            mInstance=new MySingleton(context);
        }
        return mInstance;

    }
    public<T> void addToRequestque(Request<T> request){
        getRequestQueue().add(request);

    }
}
