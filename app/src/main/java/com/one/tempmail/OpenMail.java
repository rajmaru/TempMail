package com.one.tempmail;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class OpenMail extends AppCompatActivity {

    TextView mailBody;
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_mail);

        mailBody = findViewById(R.id.mailBody);
        backBtn = findViewById(R.id.backBtn);

        mailBody.setMovementMethod(new ScrollingMovementMethod());

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenMail.super.onBackPressed();
            }
        });
    }
}