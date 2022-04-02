package com.one.tempmail.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.one.tempmail.Adapter.InboxAdapter;
import com.one.tempmail.Constants.Constants;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    InboxAdapter adapter;
    ApiViewModel apiViewModel;
    String randomEmail, login, domain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        login = "moinu";
        domain = "xojxe.com";

        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);

        apiViewModel.getRandomEmail(Constants.ACTION_GET_RANDOM_EMAIL, 1)
                .observe(this, new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> strings) {
                        setRandomEmail(strings);
                    }
                });

    }

    private void setRandomEmail(List<String> strings) {
        if(!strings.isEmpty()){
            binding.randomEmailTV.setText(strings.get(0));
        }else {
            binding.randomEmailTV.setText("");
        }
    }


//    private void setInboxAdapter(List<InboxData> inboxDataList) {
//       if (!inboxDataList.isEmpty()) {
//            binding.inboxRv.setVisibility(View.VISIBLE);
//            binding.noDataImage.setVisibility(View.GONE);
//            binding.noDataText.setVisibility(View.GONE);
//            adapter = new InboxAdapter(MainActivity.this, inboxDataList);
//            binding.inboxRv.setAdapter(adapter);
//            binding.inboxRv.setHasFixedSize(true);
//            binding.inboxRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
//        } else {
//            binding.inboxRv.setVisibility(View.GONE);
//            binding.noDataImage.setVisibility(View.VISIBLE);
//            binding.noDataText.setVisibility(View.VISIBLE);
//        }
//    }

}