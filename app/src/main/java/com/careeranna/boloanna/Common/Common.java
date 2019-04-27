package com.careeranna.boloanna.Common;

import com.careeranna.boloanna.Helper.Api;
import com.careeranna.boloanna.Remote.RetrofitClient;

public class Common {

    public final static String BASE_URL_API = "https://www.careeranna.com/";

    public static Api getApiService() {

        return RetrofitClient.getClient(BASE_URL_API).create(Api.class);

    }
}
