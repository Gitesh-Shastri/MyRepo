package com.careeranna.boloanna;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button logIn_tab, signUp_tab;

    LinearLayout log_in_layout, sign_up_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        logIn_tab = findViewById(R.id.log_in_tab);
        signUp_tab = findViewById(R.id.sign_up_tab);

        log_in_layout = findViewById(R.id.login_layout);
        sign_up_layout = findViewById(R.id.sign_up_layout);

        logIn_tab.setOnClickListener(this);
        signUp_tab.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {

        final int sdk = android.os.Build.VERSION.SDK_INT;

        switch (v.getId()) {

            case R.id.log_in_tab:
                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    logIn_tab.setBackgroundDrawable(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_active_tab));
                    signUp_tab.setBackgroundDrawable(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_non_active_tab));
                } else {
                    logIn_tab.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_active_tab));
                    signUp_tab.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_non_active_tab));
                }
                log_in_layout.setVisibility(View.VISIBLE);
                sign_up_layout.setVisibility(View.GONE);
                break;

            case R.id.sign_up_tab:

                if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                    logIn_tab.setBackgroundDrawable(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_non_active_tab));
                    signUp_tab.setBackgroundDrawable(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_active_tab));
                } else {
                    logIn_tab.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_non_active_tab));
                    signUp_tab.setBackground(ContextCompat.getDrawable(SignInActivity.this, R.drawable.border_bottom_for_active_tab));
                }

                log_in_layout.setVisibility(View.GONE);
                sign_up_layout.setVisibility(View.VISIBLE);
                break;

        }

    }
}
