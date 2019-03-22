package com.careeranna.boloanna.fragements;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.careeranna.boloanna.Adapter.ArticleArrayAdapter;
import com.careeranna.boloanna.Adapter.SubCategoryArrayAdapter;
import com.careeranna.boloanna.ParticularArticle;
import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.Article;
import com.careeranna.boloanna.models.Constants;
import com.careeranna.boloanna.models.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NewsFragment extends Fragment implements SubCategoryArrayAdapter.OnItemClickListener, ArticleArrayAdapter.OnItemClickListener {


    private Context mContext;

    ArrayList<SubCategory> subCategories;
    ArrayList<Article> articles;

    ProgressBar progressBar;

    RecyclerView sub_category_rv, artciles_rv;

    SubCategoryArrayAdapter subCategoryArrayAdapter;
    ArticleArrayAdapter articleArrayAdapter;

    JsonArrayRequest jsonArrayRequest;

    RequestQueue requestQueue;

    public NewsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_news, container, false);

        mContext = inflater.getContext();

        subCategories = new ArrayList<>();
        articles = new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);

        sub_category_rv = view.findViewById(R.id.sub_category_name_rv);
        artciles_rv = view.findViewById(R.id.articles_rv);

        requestQueue = Volley.newRequestQueue(mContext);

        fetchSubCategories();

        return view;
    }

    private void fetchSubCategories() {

        progressBar.setVisibility(View.VISIBLE);

        jsonArrayRequest = new JsonArrayRequest(Constants.SUB_CATEGORY_URL + "1",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("data_from_sub_category", response.toString());

                        JSONObject jsonObject = null;

                        for (int i=0;i<response.length();i++) {
                            try {

                                jsonObject = response.getJSONObject(i);

                                SubCategory subCategory = new SubCategory();

                                if(jsonObject.has("ACTIVE_STATUS")) {
                                    subCategory.setACTIVE_STATUS(jsonObject.getString("ACTIVE_STATUS"));
                                }
                                if(jsonObject.has("EXAM_NAME")) {
                                    subCategory.setEXAM_NAME(jsonObject.getString("EXAM_NAME"));
                                }
                                if(jsonObject.has("CATEGORY_ID")) {
                                    subCategory.setCATEGORY_ID(jsonObject.getString("CATEGORY_ID"));
                                }
                                if(jsonObject.has("EXAM_NAME_ID")) {
                                    subCategory.setEXAM_NAME_ID(jsonObject.getString("EXAM_NAME_ID"));
                                }

                                subCategories.add(subCategory);

                            } catch (JSONException e) {
                                Log.e("NewsFragment", "json_error ");
                                e.printStackTrace();
                            }

                        }

                        subCategoryArrayAdapter = new SubCategoryArrayAdapter(subCategories, mContext);

                        sub_category_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
                        sub_category_rv.setAdapter(subCategoryArrayAdapter);

                        subCategoryArrayAdapter.setOnItemClicklistener(NewsFragment.this);
                        fetchArticles();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error_from_sub_category", error.getMessage().toString());
                        fetchArticles();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);


    }

    private void fetchArticles() {

        jsonArrayRequest = new JsonArrayRequest(Constants.ENGLISH_ARTICLES_URL + "1",
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("data_from_articles", response.toString());

                        JSONObject Articles = null;

                        for (int i=0;i<response.length();i++) {
                            try {

                                Articles = response.getJSONObject(i);

                                articles.add(new Article(Articles.getString("ID"),
                                        Articles.getString("post_title"),
                                        "https://www.careeranna.com/articles/wp-content/uploads/" + Articles.getString("meta_value").replace("\\", ""),
                                        Articles.getString("display_name"),
                                        "CAT",
                                        "",
                                        Articles.getString("post_date")));

                            } catch (JSONException e) {
                                Log.e("NewsFragment", "json_error ");
                                e.printStackTrace();
                            }

                        }

                        articleArrayAdapter = new ArticleArrayAdapter(articles, mContext);

                        artciles_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
                        artciles_rv.setAdapter(articleArrayAdapter);

                        articleArrayAdapter.setOnItemClicklistener(NewsFragment.this);

                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("error_from_articles", error.getMessage().toString());
                        progressBar.setVisibility(View.GONE);
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onCategorySelected(int position) {

    }

    @Override
    public void onArticleSelected(int position) {
        startActivity(new Intent(mContext, ParticularArticle.class).putExtra("article", articles.get(position)));
    }
}
