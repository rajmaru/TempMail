package com.one.tempmail.Api;

import com.one.tempmail.Models.InboxData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiRequest {

    @GET("api/v1/")
    Call<List<String>> getRandomEmail(
            @Query("action") String action,
            @Query("count") Integer count
    );
}
