package com.careeranna.boloanna;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.careeranna.boloanna.Adapter.SliderAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    Button goToLanding, goToSignUp;

    ViewPager onBoardSlider;

    LinearLayout dots_layout;

    private SliderAdapter sliderAdapter;

    private TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_boarding);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        goToLanding = findViewById(R.id.goToLanding);

        goToSignUp = findViewById(R.id.goToSignUp);

        onBoardSlider = findViewById(R.id.onBaordSlider);

        dots_layout = findViewById(R.id.dots_layout);

        sliderAdapter = new SliderAdapter(this);

        onBoardSlider.setAdapter(sliderAdapter);

        goToLanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, ExploreActivity.class);
                startActivity(intent);
            }
        });

        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OnBoardingActivity.this, SignInActivity.class);
                startActivity(intent);
            }
        });

        addDotsIndicator(0);

        onBoardSlider.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {

                addDotsIndicator(i);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    public void addDotsIndicator(int current_position) {

        mDots = new TextView[3];
        dots_layout.removeAllViews();

        for(int i=0 ; i<mDots.length ; i++) {

            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(Color.parseColor("#CCCCCC"));

            dots_layout.addView(mDots[i]);

        }

        if(mDots.length > 0) {
            mDots[current_position].setTextColor(Color.parseColor("#bb3335"));
        }


    }

}
