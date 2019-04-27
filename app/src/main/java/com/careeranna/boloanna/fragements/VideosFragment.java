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
import com.android.volley.toolbox.Volley;
import com.careeranna.boloanna.Adapter.SubCategoryArrayAdapter;
import com.careeranna.boloanna.Adapter.VideosArrayAdapter;
import com.careeranna.boloanna.ParticularVideoActivity;
import com.careeranna.boloanna.R;
import com.careeranna.boloanna.models.Constants;
import com.careeranna.boloanna.models.SubCategory;
import com.careeranna.boloanna.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class VideosFragment extends Fragment implements
        SubCategoryArrayAdapter.OnItemClickListener,
        VideosArrayAdapter.OnItemClickListener {

    private static final String KEY_TITLE = "VideosFragment";

    private Context mContext;

    ArrayList<User> users;
    ArrayList<SubCategory> subCategories;

    ProgressBar progressBar;

    VideosArrayAdapter videosArrayAdpater;

    RecyclerView users_videos_rv, sub_category_rv;

    SubCategoryArrayAdapter subCategoryArrayAdapter;

    JsonArrayRequest jsonArrayRequest;

    RequestQueue requestQueue;

    public VideosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_videos, container, false);

        mContext = inflater.getContext();

        subCategories = new ArrayList<>();
        users = new ArrayList<>();

        progressBar = view.findViewById(R.id.progress_bar);

        sub_category_rv = view.findViewById(R.id.sub_category_name_rv);
        users_videos_rv = view.findViewById(R.id.videos_rv);

        requestQueue = Volley.newRequestQueue(mContext);

        fetchSubCategories();

        return view;
    }

    private void fetchSubCategories() {

        progressBar.setVisibility(View.VISIBLE);

        users.add(new User());
        users.add(new User());

        videosArrayAdpater = new VideosArrayAdapter(mContext, users);

        users_videos_rv.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        users_videos_rv.setAdapter(videosArrayAdpater);

        videosArrayAdpater.setOnItemClicklistener(this);

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

                        subCategoryArrayAdapter.setOnItemClicklistener(VideosFragment.this);
                        progressBar.setVisibility(View.GONE);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("error_from_sub_category", error.getMessage().toString());
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
    public void onVideoItemSelected(int position) {

        startActivity(new Intent(mContext, ParticularVideoActivity.class));

    }
}
