package com.one.tempmail.Api;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("api/v1/")
    Call<ArrayList<String>> getRandomEmail( @Query("action") String action );

}
