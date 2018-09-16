package com.fakejoker.livebettinggoal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import es.dmoral.toasty.Toasty;

public class About extends AppCompatActivity {
    CardView web_url_cardView;
    TextView app_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        CardView contact_dev= findViewById(R.id.contact_dev);
        app_version= findViewById(R.id.app_version);

        /*version name*/
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            String versionName = info.versionName;
            app_version.setText(versionName);
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
        }

        web_url_cardView = findViewById(R.id.web_url_cardView);
        web_url_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.livebettinggoal.com";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                try{
                    startActivity(i);
                }catch (Exception e){
                    Toasty.error(About.this, "Unable to open website. Permission denied", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //gmail email sending intent
        final Intent contact = new Intent(Intent.ACTION_SEND);
        contact.setType("plain/text");
        contact.putExtra(Intent.EXTRA_EMAIL  , new String[]{"codegreeddevelopers@gmail.com"});
        contact.putExtra(Intent.EXTRA_TEXT   , "");
        contact.setPackage("com.google.android.gm");

        //other mail sending intent
        final Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("plain/text");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"codegreeddevelopers@gmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "");
        i.putExtra(Intent.EXTRA_TEXT   , "");
        i.setPackage("com.google.android.gm");

        contact_dev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    try{
                        startActivity(contact);
                    }catch (ActivityNotFoundException e){
                        startActivity(Intent.createChooser(i, "Send mail..."));
                    }

                }catch (ActivityNotFoundException e){
                    Toasty.error(About.this,"There is no email client installed.", Toast.LENGTH_SHORT).show();

                }

            }
        });
    }
}
