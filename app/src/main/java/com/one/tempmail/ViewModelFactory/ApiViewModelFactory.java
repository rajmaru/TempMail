package com.one.tempmail.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.one.tempmail.ViewModel.ApiViewModel;

public class ApiViewModelFactory implements ViewModelProvider.Factory {

    Context context;

    public ApiViewModelFactory(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> aClass) {
        ApiViewModel apiViewModel = new ApiViewModel(context);
        return (T) apiViewModel;
    }
}
