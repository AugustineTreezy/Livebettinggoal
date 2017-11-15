package com.fakejoker.livebettinggoal;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import cz.msebera.android.httpclient.Header;

public class Results extends AppCompatActivity {
    Result_MatchesAdapter result_matchesAdapter;
    private AdView mAdView;

    ListView res_matches;
    ProgressDialog mprogress;
    View view;
    boolean connected = false;
    SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_tips);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        mAdView = findViewById(R.id.adView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        //start of swipe refreshing
        refreshLayout= findViewById(R.id.live_tips);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                MediaPlayer ring= MediaPlayer.create(Results.this,R.raw.refreshed);
                ring.start();
                Intent my_class_intent=getIntent();
                finish();
                overridePendingTransition(0,0);
                startActivity(my_class_intent);
                overridePendingTransition(0,0);


            }
        });

        //intiializng the progress bar
        mprogress=new ProgressDialog(this);
        mprogress.setMessage("Retrieving Data...");
        mprogress.show();

        //checking internet connection
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            //getting the list view from the xml file
            res_matches= findViewById(R.id.matches);


            //initializing the arrayadapter
            ArrayList<Results_Matches> arrayoflivematches=new ArrayList<>();
            result_matchesAdapter=new Result_MatchesAdapter(this,0,arrayoflivematches);

            AsyncHttpClient asyncHttpClient=new AsyncHttpClient();
            asyncHttpClient.post("http://www.livebettinggoal.com/results.php", new TextHttpResponseHandler() {
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
                        ArrayList<Results_Matches> live_match=Results_Matches.fromJson(livebet);
                        result_matchesAdapter.addAll(live_match);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    //end of getting JSON Data


                }
            });


            res_matches.setAdapter(result_matchesAdapter);


        }
        else {
            //if no internet connection
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
}
