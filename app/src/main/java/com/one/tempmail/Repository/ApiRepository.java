package com.one.tempmail.Repository;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.one.tempmail.Api.ApiRequest;
import com.one.tempmail.Api.RetrofitRequest;
import com.one.tempmail.CheckConnection.CheckNetworkConnection;
import com.one.tempmail.Constants.Constants;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Models.MessageData;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiRepository {
    Context context;
    ApiRequest apiRequest;

    public ApiRepository(Context context) {
        this.context = context;
        apiRequest = new RetrofitRequest().getApiRequest();
    }

    public LiveData<ArrayList<String>> getRandomEmail() {
        final MutableLiveData<ArrayList<String>> data = new MutableLiveData<>();
        if(CheckNetworkConnection.check(context)){
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
        }else{
            data.setValue(null);
        }
        return data;
    }

    public LiveData<ArrayList<InboxData>> getInboxData(String username, String domain) {
        final MutableLiveData<ArrayList<InboxData>> data = new MutableLiveData<>();
        if(CheckNetworkConnection.check(context)){
            apiRequest.getInboxData(Constants.ACTION_GET_MESSAGES, username, domain)
                    .enqueue(new Callback<ArrayList<InboxData>>() {
                        @Override
                        public void onResponse(Call<ArrayList<InboxData>> call, Response<ArrayList<InboxData>> response) {
                            if (response.body() != null) {
                                data.setValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<InboxData>> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
        }else{
            data.setValue(null);
        }
        return data;
    }

    public LiveData<MessageData> getMessageData(String username, String domain, Integer id) {
        final MutableLiveData<MessageData> data = new MutableLiveData<>();
        if(CheckNetworkConnection.check(context)){
            apiRequest.getMessageData(Constants.ACTION_READ_MESSAGE, username, domain, id)
                    .enqueue(new Callback<MessageData>() {
                        @Override
                        public void onResponse(Call<MessageData> call, Response<MessageData> response) {
                            if (response.body() != null) {
                                data.setValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageData> call, Throwable t) {
                            data.setValue(null);
                        }
                    });
        }else{
            data.setValue(null);
        }
        return data;
    }

    public void downloadAttachments(String username, String domain, Integer id, String filename) {
        String url = "https://www.1secmail.com/api/v1/?action=download"
                + "&login=" + username
                + "&domain=" + domain
                + "&id=" + id
                + "&file=" + filename;

        if(CheckNetworkConnection.check(context)){
            apiRequest.downloadAttachments(url).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {

                    } catch (Exception ex) {
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {

                }
            });
        }

    }

}
