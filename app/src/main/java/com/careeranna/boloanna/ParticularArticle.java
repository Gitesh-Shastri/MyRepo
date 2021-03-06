package com.careeranna.boloanna;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.boloanna.models.Article;

public class ParticularArticle extends AppCompatActivity {

    WebView webview;

    WebSettings webSettings;

    Article article;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_particular_article);

        if (getSupportActionBar() != null) {
            if (getSupportActionBar().isShowing()) {
                getSupportActionBar().hide();
            }
        }

        article = (Article) getIntent().getSerializableExtra("article");

        webview = findViewById(R.id.articleWebView);

        webSettings = webview.getSettings();

        webSettings.setJavaScriptEnabled(true);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        String url = "https://careeranna.com/api/particularArticle.php?id=" + article.getId();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        response = response.replace("<img ", "<img class=\"img-thumbnail\" ");
                        response = response.replaceAll("<table .*>", "<table class=\"table table-responsive\"  >");
                        response = response.replaceAll("\\s+", " ");


                        String html = "<html>" +
                "<head>" +
                "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css\" integrity=\"sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm\" crossorigin=\"anonymous\">\n" +
                "<link href='https://fonts.googleapis.com/css?family=Roboto' rel='stylesheet'>" +
                "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.6.3/css/all.css\" integrity=\"sha384-UHRtZLI+pbxtHCWp1t77Bi1L4ZtiqrqD80Kn4Z8NTSRyMA2Fd33n5dQ8lWUE00s/\" crossorigin=\"anonymous\">" +
                "<style>" +
                "body {\n" +
                "    font-family: Roboto; " +
                "}" +
                "table tr:nth-child(odd) {" +
                "   background: #d9e2f3;" +
                "}" +
                "table tr:nth-child(1) {" +
                "  background: #11336f !important;" +
                "}" +
                "table tr:nth-child(1) td span {" +
                "    color: #fff !important;" +
                "}" +
                "</style>" +
                "</head>" +
                "<body>" +
                "<div class=\"container\">" +
                "<img style=\"margin-top:20px;\" class=\"img-thumbnail\" src=" + article.getImage_url() + ">"+
                "<h5 style=\"margin-top:20px;\">" + article.getName() + "</h5><hr/>";
        if (article.getAuthor().startsWith("Sai")) {
            html += "<div class=\"row\"><span class=\"col col-sm-6 col-md-6\"><img src=\"https://secure.gravatar.com/avatar/8a4cbbd46914885872a3bf5109761a97?s=35&d=mm&r=g\" class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getAuthor() + "</span><span class=\"col col-sm-6 col-md-6\">" +
                    "<img src=\"https://www.careeranna.com/upload/calendar.svg\" class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getCreated_at().substring(0, 10) + "</span></div>" +
                    "<br/>" +response +
                    "</div>" +
                    "</html>";
        } else if (article.getAuthor().startsWith("Sri")) {
            html += "<div class=\"row\"><span class=\"col col-sm-6 col-md-6\"><img src=\"https://secure.gravatar.com/avatar/a6f47b79dadb76f0331bcaf66921f657?s=35&d=mm&r=g\" class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getAuthor() + "</span><span class=\"col col-sm-6 col-md-6\">" +
                    "<img src=\"https://www.careeranna.com/upload/calendar.svg\" class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getCreated_at().substring(0, 10) + "</span></div>" +
                    "<br/> " + response +
                    "</div>" +
                    "</html>";
        } else {
            html += "<div class=\"row\"><span class=\"col col-sm-6 col-md-6\"><img src=\"https://www.careeranna.com/icons/favicon-32x32.png\"  class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getAuthor() + "</span><span class=\"col col-sm-6 col-md-6\">" +
                    "<img src=\"https://www.careeranna.com/upload/calendar.svg\" class=\"avatar avatar-35 photo img-circle\" style=\"border-radius: 50%;\"height=\"25\" width=\"25\">" + article.getCreated_at().substring(0, 10) + "</span></div>" +
                    "<br/>" +response +
                    "</div>" +
                    "</html>";
        }
        webview.loadData(html, "text/html", null);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );

        requestQueue.add(stringRequest);
    }
}
