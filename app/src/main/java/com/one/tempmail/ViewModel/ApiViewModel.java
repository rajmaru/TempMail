package com.one.tempmail.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Repository.ApiRepository;

import java.util.List;

public class ApiViewModel extends ViewModel {
    ApiRepository repository;
    LiveData<List<String>> getRandomEmail;
    LiveData<List<InboxData>> getInboxData;

    public ApiViewModel(){
        repository = new ApiRepository();
    }

    public LiveData<List<String>> getRandomEmail(String action, Integer count){
        getRandomEmail = repository.getRandomEmail(action, count);
        return getRandomEmail;
    }

}
