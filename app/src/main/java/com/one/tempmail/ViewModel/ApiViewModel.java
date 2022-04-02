package com.one.tempmail.ViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.one.tempmail.Models.InboxData;
import com.one.tempmail.Repository.ApiRepository;

import java.util.ArrayList;
import java.util.List;

public class ApiViewModel extends ViewModel {
    ApiRepository repository;
    LiveData<ArrayList<String>> getRandomEmail;

    public ApiViewModel(){
        repository = new ApiRepository();
    }

    public LiveData<ArrayList<String>> getRandomEmail(){
        getRandomEmail = repository.getRandomEmail();
        return getRandomEmail;
    }

}
