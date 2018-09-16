package com.fakejoker.livebettinggoal;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import es.dmoral.toasty.Toasty;

public class VipResults extends AppCompatActivity {
    WebView webview;
    private AdView mAdView;
    boolean isRedirected;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_vip_results);

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        webview = findViewById(R.id.webview);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            startWebView(webview,"https://www.livebettinggoal.com/web-results.php?phone");


        }
        else {
            connected = false;

            Snackbar snackbar = Snackbar.make(findViewById(R.id.contact_admin), "No internet connection!", Snackbar.LENGTH_INDEFINITE);
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
    private void startWebView(WebView webView,String url) {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("https://www.livebettinggoal.com/web-results.php?phone");
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url+"&phone"));
                try{
                    startActivity(i);
                }catch (Exception e){
                    Toasty.error(VipResults.this, "Unable to open browser. Permission denied", Toast.LENGTH_SHORT).show();
                }

                //end
                isRedirected = true;
                return false;
            }

            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(VipResults.this).create();
                alertDialog.setTitle("Error");

                alertDialog.setMessage(Html.fromHtml("<font color='#000000'>Server unreachable. Check your internet connection and try again.</font>"));
                alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try Again", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                        startActivity(getIntent());
                    }
                });

                alertDialog.show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }

            public void onLoadResource (WebView view, String url) {
                if (!isRedirected) {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(VipResults.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }

            }
            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:(function() { " +
                        "document.getElementById('toolbar').style.display='none'; " +
                        "document.getElementById('menu-box').style.display='none'; " +
                        "document.getElementById('header-title').style.display='none'; " +
                        "document.getElementById('header-nav').style.display='none'; " +
                        "document.getElementById('side-nav').style.display='none'; " +

                        "})()");
                try{
                    isRedirected=true;
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        //progressDialog = null;
                    }

                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        webView.getSettings().setJavaScriptEnabled(true);

        webView.loadUrl("https://www.livebettinggoal.com/web-results.php?phone");
    }
}
