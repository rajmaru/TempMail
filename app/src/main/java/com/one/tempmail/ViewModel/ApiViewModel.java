package com.one.tempmail.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.one.tempmail.Constants.Constants;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Models.MessageData;
import com.one.tempmail.Repository.ApiRepository;

import java.util.ArrayList;
import java.util.List;

public class ApiViewModel extends ViewModel {
    ApiRepository repository;
    LiveData<ArrayList<String>> getRandomEmail;
    LiveData<ArrayList<InboxData>> getInboxData;
    LiveData<MessageData> getMessageData;

    public ApiViewModel(){
        repository = new ApiRepository();
    }

    public LiveData<ArrayList<String>> getRandomEmail(){
        getRandomEmail = repository.getRandomEmail();
        return getRandomEmail;
    }

    public LiveData<ArrayList<InboxData>> getInboxData(String username, String domain){
        getInboxData = repository.getInboxData(username, domain);
        return getInboxData;
    }

    public LiveData<MessageData> getMessageData(String username, String domain, Integer id){
        getMessageData = repository.getMessageData(username, domain, id);
        return getMessageData;
    }

    public void downloadAttachments(String username, String domain, Integer id, String filename){
        repository.downloadAttachments(username, domain, id, filename);
    }

}
