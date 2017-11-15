package com.fakejoker.livebettinggoal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class Live_tips extends AppCompatActivity {
    private AdView mAdView;
    Free_MatchesAdapter free_matchesAdapter;
    ListView matches;
    ProgressDialog mprogress;
    boolean connected = false;

    String appserver_url="http://www.livebettinggoal.com/appuser.php";
    String token;
    SwipeRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_live_tips);

//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);


        refreshLayout= findViewById(R.id.live_tips);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent my_class_intent=getIntent();
                finish();
                overridePendingTransition(0,0);
                startActivity(my_class_intent);
                overridePendingTransition(0,0);
                MediaPlayer ring= MediaPlayer.create(Live_tips.this,R.raw.refreshed);
                ring.start();
            }
        });

        //intiializng the progress bar
        mprogress=new ProgressDialog(this);
        mprogress.setMessage("Retrieving Data...");
        mprogress.show();

        sendtoken();

        //checking internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            //getting the list view from the xml file
            matches= findViewById(R.id.matches);


            //initializing the arrayadapter
            ArrayList<Free_Matches> arrayoflivematches=new ArrayList<>();
            free_matchesAdapter=new Free_MatchesAdapter(Live_tips.this,0,arrayoflivematches);

            AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
            asyncHttpClient.post("http://www.livebettinggoal.com/livebet.php", new TextHttpResponseHandler() {
                @Override
                public void onFailure(int i, Header[] headers, String s, Throwable throwable) {
                    mprogress.dismiss();
                    Snackbar snackbar=Snackbar.make(findViewById(R.id.live_tips),"Server Unreachable",Snackbar.LENGTH_INDEFINITE);
                    snackbar.setAction("Reconnect", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent my_class_intent=getIntent();
                            finish();
                            overridePendingTransition(0,0);
                            startActivity(my_class_intent);
                            overridePendingTransition(0,0);
                        }
                    });
                    snackbar.show();
                }

                @Override
                public void onSuccess(int i, Header[] headers, String s) {
                    mprogress.dismiss();
                    try {
                        JSONArray livebet=new JSONArray(s);
                        ArrayList<Free_Matches> live_match=Free_Matches.fromJson(livebet);
                        free_matchesAdapter.addAll(live_match);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //end of getting JSON Data


                }
            });


            matches.setAdapter(free_matchesAdapter);


        }
        else {
            //if not connected to a network
            connected = false;
            mprogress.dismiss();
            Snackbar snackbar = Snackbar.make(findViewById(R.id.live_tips), "No internet connection!", Snackbar.LENGTH_INDEFINITE);
            snackbar.setAction("Reconnect", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent my_class_intent = getIntent();
                    finish();
                    overridePendingTransition(0, 0);
                    startActivity(my_class_intent);
                    overridePendingTransition(0, 0);
                }
            });
            snackbar.show();

        }


    }
    public void sendtoken() {
        SharedPreferences sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.FCM_PREF), Context.MODE_PRIVATE);
        token=sharedPreferences.getString(getString(R.string.FCM_TOKEN),"");
        if (token.isEmpty()){
            token= FirebaseInstanceId.getInstance().getToken();
        }
        StringRequest stringRequest=new StringRequest(Request.Method.POST, appserver_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<String,String>();
                params.put("fcm_token",token);
                return params;
            }
        };
        MySingleton.getmInstance(Live_tips.this).addToRequestque(stringRequest);

    }

}
