package com.fakejoker.livebettinggoal;

import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import es.dmoral.toasty.Toasty;

public class Vip_subscription extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_vip_subscription);
    }

    public void copy_email(View view) {
        ClipboardManager email=(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        email.setText("noahali@outlook.com");
        Toasty.success(this, "Email address copied to clipboard", Toast.LENGTH_SHORT, true).show();
    }
}
