package com.one.tempmail.Repository;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.one.tempmail.Api.ApiRequest;
import com.one.tempmail.Api.RetrofitRequest;
import com.one.tempmail.CheckConnection.CheckNetworkConnection;
import com.one.tempmail.Constants.Constants;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Models.MessageData;

import java.util.ArrayList;

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
                                data.postValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                            data.postValue(null);
                        }
                    });
        }else{
            data.postValue(null);
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
                                data.postValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<ArrayList<InboxData>> call, Throwable t) {
                            data.postValue(null);
                        }
                    });
        }else{
            data.postValue(null);
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
                                data.postValue(response.body());
                            }
                        }

                        @Override
                        public void onFailure(Call<MessageData> call, Throwable t) {
                            data.postValue(null);
                        }
                    });
        }else{
            data.postValue(null);
        }
        return data;
    }

    public void downloadAttachments(String username, String domain, Integer id, String filename) {
        if(CheckNetworkConnection.check(context)){
            if(username!=null && !username.isEmpty() || domain!=null && !domain.isEmpty() || id != null|| filename!=null && !filename.isEmpty()){
                String url = "https://www.1secmail.com/api/v1/?action=download"
                        + "&login=" + username
                        + "&domain=" + domain
                        + "&id=" + id
                        + "&file=" + filename;
                Log.d("TAG", "downloadAttachments: " + url);

                downloadImage(url, filename);

            }

        }

    }

    public void downloadImage(String url, String imageName) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setTitle(imageName);
        request.setDescription("Downloading...");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.allowScanningByMediaScanner();
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, imageName);
        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }

}
