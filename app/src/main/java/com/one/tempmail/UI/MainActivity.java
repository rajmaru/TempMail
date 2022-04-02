package com.one.tempmail.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.one.tempmail.Adapter.InboxAdapter;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.R;
import com.one.tempmail.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    InboxAdapter adapter;
    ArrayList<InboxData> fakeDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        setInboxAdapter();

    }

    private void setInboxAdapter() {
        if (!fakeDataList.isEmpty()) {
            binding.inboxRv.setVisibility(View.VISIBLE);
            binding.noDataImage.setVisibility(View.GONE);
            binding.noDataText.setVisibility(View.GONE);
            adapter = new InboxAdapter(MainActivity.this, fakeDataList);
            binding.inboxRv.setAdapter(adapter);
            binding.inboxRv.setHasFixedSize(true);
            binding.inboxRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        } else {
            binding.inboxRv.setVisibility(View.GONE);
            binding.noDataImage.setVisibility(View.VISIBLE);
            binding.noDataText.setVisibility(View.VISIBLE);
        }
    }

}