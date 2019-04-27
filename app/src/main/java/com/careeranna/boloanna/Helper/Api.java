package com.careeranna.boloanna.Helper;

import com.careeranna.boloanna.models.SubCategory;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Api {

    @GET("getSubCategoryVideos")
    Call<List<SubCategory>> getSubCategory();

}
