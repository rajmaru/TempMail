package com.one.tempmail.Api;

import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Models.MessageData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiRequest {

    @GET("api/v1/")
    Call<ArrayList<String>> getRandomEmail( @Query("action") String action );

    @GET("api/v1/")
    Call<ArrayList<InboxData>> getInboxData(
            @Query("action") String action,
            @Query("login") String login,
            @Query("domain") String domain
    );

    @GET("api/v1/")
    Call<MessageData> getMessageData(
            @Query("action") String action,
            @Query("login") String login,
            @Query("domain") String domain,
            @Query("id") Integer id
    );

}
