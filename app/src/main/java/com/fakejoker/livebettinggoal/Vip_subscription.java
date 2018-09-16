package com.fakejoker.livebettinggoal;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipboardManager;
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

import es.dmoral.toasty.Toasty;

public class Vip_subscription extends AppCompatActivity {
    boolean isRedirected;
    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_vip_subscription);

        WebView webview= findViewById(R.id.vip_webview);

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            startWebView(webview,"https://www.livebettinggoal.com/app-subscribe.php");


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
    //progress bar
    private void startWebView(WebView webView,String url) {
        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                webView.loadUrl("about:blank");
                AlertDialog alertDialog = new AlertDialog.Builder(Vip_subscription.this).create();
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

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl("https://www.livebettinggoal.com/app-subscribe.php");
                //start
                Uri tele_origi = Uri.parse("tg://resolve?domain=LBGAdmin");
                Intent origi = new Intent(Intent.ACTION_VIEW, tele_origi);
                origi.setPackage("org.telegram.messenger");
                //end og original telegram

                Uri tele_plus = Uri.parse("tg://resolve?domain=LBGAdmin");
                Intent likeIng = new Intent(Intent.ACTION_VIEW, tele_plus);
                likeIng.setPackage("org.telegram.plus");

                Uri tele_store = Uri.parse("https://play.google.com/store/apps/details?id=org.telegram.messenger");
                Intent telestore = new Intent(Intent.ACTION_VIEW, tele_store);
                likeIng.setPackage("play.google.com");

                //gmail email sending intent
                final Intent contact_noah = new Intent(Intent.ACTION_SEND);
                contact_noah.setType("plain/text");
                contact_noah.putExtra(Intent.EXTRA_EMAIL  , new String[]{"noahali@outlook.com"});
                contact_noah.putExtra(Intent.EXTRA_TEXT   , "");
                contact_noah.setPackage("com.google.android.gm");

                //other mail sending intent
                final Intent i_noah = new Intent(Intent.ACTION_SEND);
                i_noah.setType("plain/text");
                i_noah.putExtra(Intent.EXTRA_EMAIL  , new String[]{"noahali@outlook.com"});
                i_noah.putExtra(Intent.EXTRA_SUBJECT, "");
                i_noah.putExtra(Intent.EXTRA_TEXT   , "");
                i_noah.setPackage("com.google.android.gm");

                //gmail email sending intent
                final Intent contact = new Intent(Intent.ACTION_SEND);
                contact.setType("plain/text");
                contact.putExtra(Intent.EXTRA_EMAIL  , new String[]{"livebettinggoal@gmail.com"});
                contact.putExtra(Intent.EXTRA_TEXT   , "");
                contact.setPackage("com.google.android.gm");

                //other mail sending intent
                final Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("plain/text");
                i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"livebettinggoal@gmail.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "");
                i.putExtra(Intent.EXTRA_TEXT   , "");
                i.setPackage("com.google.android.gm");

                switch (url) {
                    case "mailto:noahali@outlook.com":
                        try {
                            try {
                                startActivity(contact_noah);
                            } catch (ActivityNotFoundException e) {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            }

                        } catch (ActivityNotFoundException e) {
                            Toasty.error(Vip_subscription.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    case "mailto:livebettinggoal@gmail.com":
                        try {
                            try {
                                startActivity(contact);
                            } catch (ActivityNotFoundException e) {
                                startActivity(Intent.createChooser(i, "Send mail..."));
                            }

                        } catch (ActivityNotFoundException e) {
                            Toasty.error(Vip_subscription.this, "There is no email client installed.", Toast.LENGTH_SHORT).show();

                        }

                        break;
                    case "https://play.google.com/store/apps/details?id=org.telegram.messenger":
                        try {
                            startActivity(telestore);
                        } catch (Exception ActivityNotFoundException) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.telegram.me/LBGAdmin")));

                        }

                        break;
                    default:
                        try {
                            try {
                                try {
                                    startActivity(likeIng);
                                } catch (ActivityNotFoundException e) {
                                    startActivity(origi);
                                }

                            } catch (ActivityNotFoundException e) {
                                startActivity(telestore);
                                Toasty.info(Vip_subscription.this, "Please install telegram app", Toast.LENGTH_SHORT).show();
                            }

                            //end of validation of two types of telegram
                        } catch (ActivityNotFoundException e) {
                            Toasty.info(Vip_subscription.this, "Please install telegram app", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("https://www.telegram.me/LBGAdmin")));
                        }
                        break;
                }

                //end
                isRedirected = true;
                return false;
            }


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                isRedirected = false;
            }

            public void onLoadResource (WebView view, String url) {
                if (!isRedirected) {
                    if (progressDialog == null) {
                        progressDialog = new ProgressDialog(Vip_subscription.this);
                        progressDialog.setCancelable(false);
                        progressDialog.setMessage("Loading...");
                        progressDialog.show();
                    }
                }

            }
            public void onPageFinished(WebView view, String url) {
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

        webView.loadUrl("https://www.livebettinggoal.com/app-subscribe.php");
    }

}
