package com.one.tempmail.Api;

import com.one.tempmail.Constants.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {

    ApiRequest apiRequest;

    public ApiRequest getApiRequest() {
        if (apiRequest == null) {
            apiRequest = new retrofit2.Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ApiRequest.class);
        }
        return apiRequest;
    }

}
