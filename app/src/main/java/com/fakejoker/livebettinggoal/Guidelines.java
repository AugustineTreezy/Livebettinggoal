package com.fakejoker.livebettinggoal;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.ads.AdView;

public class Guidelines extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_guidelines);


        Typeface typeface=Typeface.createFromAsset(getAssets(),"mvboli.ttf");

        TextView rule1= findViewById(R.id.rules1);
        TextView rule2= findViewById(R.id.rules2);
        TextView rule3= findViewById(R.id.rules3);
        TextView rule4= findViewById(R.id.rules4);
        TextView rules_followed= findViewById(R.id.rules_followed);
        TextView summary= findViewById(R.id.summary);
        TextView plans= findViewById(R.id.plans);

        rule1.setTypeface(typeface);
        rule2.setTypeface(typeface);
        rule3.setTypeface(typeface);
        rule4.setTypeface(typeface);
        summary.setTypeface(typeface);
        plans.setTypeface(typeface);
        rules_followed.setTypeface(typeface);
    }
}
