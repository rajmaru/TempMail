package com.one.tempmail.Repository;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.one.tempmail.Api.ApiRequest;
import com.one.tempmail.Api.RetrofitRequest;

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

    public LiveData<List<String>> getRandomEmail(String action, Integer count) {
        final MutableLiveData<List<String>> data = new MutableLiveData<>();
        apiRequest.getRandomEmail(action, count)
                .enqueue(new Callback<List<String>>() {
                    @Override
                    public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                        if(response.body() != null){
                            data.setValue(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<String>> call, Throwable t) {
                            data.setValue(null);
                    }
                });
        return data;
    }

}
