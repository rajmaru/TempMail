package com.one.tempmail.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.one.tempmail.Api.ApiRequest;
import com.one.tempmail.Api.RetrofitRequest;
import com.one.tempmail.Constants.Constants;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ApiRepository {

    ApiRequest apiRequest;

    public ApiRepository() {
        apiRequest = new RetrofitRequest().getApiRequest();
    }

    public LiveData<ArrayList<String>> getRandomEmail() {
        final MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        apiRequest.getRandomEmail(Constants.ACTION_GET_RANDOM_EMAIL)
                .enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
                if (response.body() != null) {
                    data.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

}
