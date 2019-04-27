package com.careeranna.boloanna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ChooseLanguage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_language);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    public void chooseLanguage(View view) {
        startActivity(new Intent(ChooseLanguage.this, OnBoardingActivity.class));
        finish();
    }
}
