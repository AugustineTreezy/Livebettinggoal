package com.fakejoker.livebettinggoal;

import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RatingBar;
import android.widget.Toast;
import com.codemybrainsout.ratingdialog.RatingDialog;
import com.github.javiersantos.appupdater.AppUpdater;
import com.github.javiersantos.appupdater.UpdateClickListener;
import com.google.android.gms.ads.MobileAds;

import es.dmoral.toasty.Toasty;

public class MainActivity extends AppCompatActivity {

    GridView gridview;
    String[] values={
            "Guidelines",
            "Free Live tips",
            "Results",
            "VIP subscription",
            "Contact admin",
            "Share app",
            "Rate us",
            "About"};
    int[] images={
            R.drawable.guideline,
            R.drawable.livetips,
            R.drawable.results,
            R.drawable.vip,
            R.drawable.holaadmin,
            R.drawable.share,
            R.drawable.rate,
            R.drawable.about,


    };
    AlertDialog.Builder popDialog,builder;
    RatingBar ratingBar;
    Button cancel_btn,okay_btn;
    float rate;
    boolean change_state=false;
    AlertDialog popRateUs;

    private static long back_pressed_time;
    private static long PERIOD = 2000;

    @Override
    public void onBackPressed()
    {
        if (back_pressed_time + PERIOD > System.currentTimeMillis()) super.onBackPressed();
        else Toasty.info(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
        back_pressed_time = System.currentTimeMillis();
    }
    public void openStore(){
        Toasty.normal(MainActivity.this, "Please rate us on Playstore", Toast.LENGTH_LONG).show();
        Uri playstore = Uri.parse("https://play.google.com/store/apps/details?id=com.codegreed_devs.livebettinggoal");
        Intent store = new Intent(Intent.ACTION_VIEW, playstore);
        store.setPackage("com.android.vending");
        try {
            startActivity(store);


        } catch (ActivityNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://play.google.com/store/apps/details?id=com.codegreed_devs.livebettinggoal")));
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       MobileAds.initialize(this, "ca-app-pub-4528500193603466~1132528908");
        final AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.start();
        twoStageRate();


        Toolbar toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() !=null)
            getSupportActionBar().setHomeButtonEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }



        //to check for app update
        new AppUpdater(this)
                .setTitleOnUpdateAvailable("Update available")
                .setContentOnUpdateAvailable("Check out the latest version available of LiveBettingGoal")
                .setTitleOnUpdateNotAvailable("Update not available")
                .setContentOnUpdateNotAvailable("No update available. Check for updates again later!")
                .setButtonUpdate("Update now?")
                .setButtonUpdate("Update now")
                .setIcon(R.mipmap.ic_launcher)
                .setButtonDoNotShowAgain(null)
                .setCancelable(false);

        gridview= findViewById(R.id.gridview);
        GridAdapter gridAdapter=new GridAdapter(this,values,images);
        gridview.setAdapter(gridAdapter);
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        Intent guidelines=new Intent(MainActivity.this,Guidelines.class);
                        startActivity(guidelines);
                        break;
                    case 1:
                        Intent live_tips=new Intent(MainActivity.this,Live_tips.class);
                        startActivity(live_tips);
                        break;
                    case 2:
                        Intent results=new Intent(MainActivity.this,Results.class);
                        startActivity(results);
                        break;
                    case 3:
                        Intent vip=new Intent(MainActivity.this,Vip_subscription.class);
                        startActivity(vip);
                        break;
                    case 4:
                        Intent admin=new Intent(MainActivity.this,Contact_admin.class);
                        startActivity(admin);
                        break;
                    case 5:
                        Intent share=new Intent(Intent.ACTION_SEND);
                        share.setType("text/plain");
                        share.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id=com.codegreed_devs.livebettinggoal");
                        startActivity(Intent.createChooser(share,"Share Via"));
                        break;
                    case 7:
                        Intent about=new Intent(MainActivity.this,About.class);
                        startActivity(about);
                        break;
                    default:
                        ShowDialog();


                }


            }
        });

    }

    //rate us popup dialog


    public void twoStageRate(){

        final RatingDialog ratingDialog = new RatingDialog.Builder(this)
                .session(6)
                .threshold(3)
                .title("Do you like the app?\nPlease take a minute to rate us")
                .titleTextColor(R.color.black)
                .ratingBarBackgroundColor(R.color.grey_400)
                .positiveButtonText("Maybe Later")
                .positiveButtonTextColor(R.color.textcolor_black)
                .negativeButtonText("Never")
                .negativeButtonTextColor(R.color.white)
                .formTitle("We're Really Sorry\uD83D\uDE22")
                .formHint("Could you tell us what problem you faced. This will help us improve")
                .formSubmitText("Submit")
                .formCancelText("Cancel")
                .feedbackTextColor(R.color.black)
                .ratingBarColor(R.color.colorPrimary)
                .playstoreUrl("https://play.google.com/store/apps/details?id=com.codegreed_devs.livebettinggoal")
                .onThresholdCleared(new RatingDialog.Builder.RatingThresholdClearedListener() {
                    @Override
                    public void onThresholdCleared(RatingDialog ratingDialog, float rating, boolean thresholdCleared) {
                        builder=new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Thank you!\uD83D\uDE0A")
                                .setCancelable(false)
                               .setMessage("Would you like to post your review on app store. This will help and motivate us a lot")
                               .setPositiveButton("Sure", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                openStore();
                            }
                        });

                        builder.setInverseBackgroundForced(true);
                        builder.create().show();

                        ratingDialog.dismiss();
                    }
                })
                .onRatingBarFormSumbit(new RatingDialog.Builder.RatingDialogFormListener() {
                    @Override
                    public final void onFormSubmitted(String feedback) {
                        //sending feedback


                        String[] TO = {"feedback@livebettinggoal.com"};
                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
                        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Live Betting Goal App Feedback");
                        emailIntent.putExtra(Intent.EXTRA_TEXT,feedback);
                        emailIntent.setPackage("com.google.android.gm");

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                            finish();


                        } catch (android.content.ActivityNotFoundException ex) {
                            Toasty.error(MainActivity.this,"There is no email client installed.", Toast.LENGTH_SHORT).show();
                        }


                    }
                })

                .build();
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ratingDialog.show();
        }
    }



    public void ShowDialog() {
        popDialog = new AlertDialog.Builder(MainActivity.this);

        LayoutInflater inflater=MainActivity.this.getLayoutInflater();
        View view=inflater.inflate(R.layout.ratebar,null);
        popDialog
                .setView(view)
                .setIcon(R.mipmap.ic_launcher);
        ratingBar= view.findViewById(R.id.rate_us);
        //alert dialog creating
        popRateUs=popDialog.create();
        popRateUs.show();
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rate=v;
                change_state=b;
            }
        });
        cancel_btn= view.findViewById(R.id.cancel_btn);
        okay_btn= view.findViewById(R.id.ok_btn);
        okay_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!change_state){
                    rate=ratingBar.getRating();
                }

                //validating user's rate value
                if (rate>=3){
                    openStore();

                }else{

                    Toast.makeText(MainActivity.this, "Please give us feedback", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("plain/text");
                    i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"feedback@livebettinggoal.com"});
                    i.putExtra(Intent.EXTRA_SUBJECT, "Live Betting Goal App Feedback");
                    i.putExtra(Intent.EXTRA_TEXT   , "");
                    i.setPackage("com.google.android.gm");


                    try {
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (ActivityNotFoundException ex) {
                        Toasty.normal(MainActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
                popRateUs.dismiss();
                rate=3.5f;
            }
        });

        cancel_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popRateUs.dismiss();
            }
        });


    }
    @Override
    public void onPause() {
        super.onPause();
        AppUpdater appUpdater = new AppUpdater(this);
        appUpdater.stop();
    }


}
