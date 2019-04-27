package com.careeranna.boloanna;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.boloanna.models.Constants;
import com.careeranna.boloanna.models.User;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import io.paperdb.Paper;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    Button logIn_tab, signUp_tab;

    LinearLayout log_in_layout, sign_up_layout;

    Button login_btn;

    TextInputLayout login_email, login_password;

    RequestQueue requestQueue;

    StringRequest stringRequest;

    Snackbar snackbar;

    RelativeLayout sign_up_and_in_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Paper.init(this);

        if(getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        sign_up_and_in_layout = findViewById(R.id.sign_up_and_in_layout);

        logIn_tab = findViewById(R.id.log_in_tab);
        signUp_tab = findViewById(R.id.sign_up_tab);

        log_in_layout = findViewById(R.id.login_layout);
        sign_up_layout = findViewById(R.id.sign_up_layout);

        login_email = findViewById(R.id.input_layout_email);
        login_password = findViewById(R.id.input_layout_password);

        login_btn = findViewById(R.id.login_btn);

        logIn_tab.setOnClickListener(this);
        signUp_tab.setOnClickListener(this);
        login_btn.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(this);

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
                logIn_tab.setTextColor(Color.parseColor("#FFFFFF"));
                signUp_tab.setTextColor(Color.parseColor("#777575"));
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
                signUp_tab.setTextColor(Color.parseColor("#FFFFFF"));
                logIn_tab.setTextColor(Color.parseColor("#777575"));
                break;

            case R.id.login_btn:

                if (isUserEmailEmpty()
                        && isUserPasswordEmpty()) {
                    return;
                } else {
                    String email = login_email.getEditText().getText().toString();
                    String password = login_password.getEditText().getText().toString();

                    sendLoginRequest(email, password);

                }
                break;

        }

    }

    private void sendLoginRequest(final String email, final String password) {

        stringRequest = new StringRequest(Request.Method.POST,
                Constants.LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("Incorect Details !")) {
                            snackbar = Snackbar.make(sign_up_and_in_layout, response.toString(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        } else {
                            User user = new User();
                            try {
                                JSONObject userObject = new JSONObject(response.toString());
                                user.setUser_username(userObject.getString("USER_USERNAME"));
                                user.setUser_id(userObject.getString("USER_ID"));
                                user.setGoogle_id(userObject.getString("google_id"));
                                user.setFacebook_id(userObject.getString("facebook_id"));
                                user.setUser_photo(userObject.getString("img_url_app"));
                                user.setUser_email(userObject.getString("USER_EMAIL"));
                                Paper.book().write("user", new Gson().toJson(user));
                                snackbar = Snackbar.make(sign_up_and_in_layout, "Signed in as " + user.getUser_username(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                startActivity(new Intent(SignInActivity.this, LandingActivity.class));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                snackbar = Snackbar.make(sign_up_and_in_layout, "Please Try Again", Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        snackbar = Snackbar.make(sign_up_and_in_layout, getString(R.string.something_went_wrong), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                // Posting params to login url
                Map<String, String> params = new HashMap<String, String>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    public boolean isUserEmailEmpty() {

        String email = login_email.getEditText().getText().toString();

        if (email.isEmpty()) {
            login_email.setError("Please Enter Email");
            return true;
        } else {
            login_email.setError("");
        }
        return false;
    }

    public boolean isUserPasswordEmpty() {

        String password = login_password.getEditText().getText().toString();

        if (password.isEmpty()) {
            login_password.setError("Please Enter Password");
            return true;
        } else {
            login_email.setError("");
        }
        return false;
    }
}
