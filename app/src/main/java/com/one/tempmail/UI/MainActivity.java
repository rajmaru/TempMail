package com.one.tempmail.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.one.tempmail.Adapter.InboxAdapter;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    InboxAdapter adapter;
    ApiViewModel apiViewModel;
    SharedPreferences savedEmailPreferences;
    ArrayList<InboxData> inboxDataList;
    SharedPreferences.Editor editor;
    ShimmerFrameLayout shimmer;
    String email;
    public static final String LOADING = "Loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialize();
        getSavedUserData();
        randomEmailButton();
        copyButton();

        binding.refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInboxData(email);
            }
        });

    }


    private void initialize() {
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        savedEmailPreferences = getSharedPreferences("savedEmailPreferences", MODE_PRIVATE);
        editor = savedEmailPreferences.edit();
        inboxDataList = new ArrayList<>();
        shimmer = findViewById(R.id.inboxShimmer);
        shimmer.startShimmer();
    }

    // Email
    private void getRandomEmail() {
        apiViewModel.getRandomEmail()
                .observe(this, new Observer<ArrayList<String>>() {
                    @Override
                    public void onChanged(ArrayList<String> strings) {
                        email = strings.get(0);
                        setEmailTV(email);
                        saveEmail(email);
                        getInboxData(email);
                    }
                });
    }

    private void setEmailTV(String email) {
        if (email != null) {
            binding.randomEmailTV.setText(email);
        } else {
            binding.randomEmailTV.setText(LOADING);
        }
    }

    // Inbox
    private void getInboxData(String email) {
        String username, domain;
        String[] stringList = email.split("@");
        username = stringList[0];
        domain = stringList[1];
        apiViewModel.getInboxData(username, domain).observe(this, new Observer<ArrayList<InboxData>>() {
            @Override
            public void onChanged(ArrayList<InboxData> inboxData) {
                inboxDataList = inboxData;
                setInboxAdapter();
            }
        });

    }

    private void setInboxAdapter() {
        if (!inboxDataList.isEmpty()) {
            // Stop Shimmer Effect
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);

            //Set Visibility
            binding.inboxRv.setVisibility(View.VISIBLE);
            binding.noDataImage.setVisibility(View.GONE);
            binding.noDataText.setVisibility(View.GONE);

            //Set Data
            adapter = new InboxAdapter(MainActivity.this, inboxDataList);
            binding.inboxRv.setAdapter(adapter);
            binding.inboxRv.setHasFixedSize(true);
            binding.inboxRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
            binding.refreshLayout.setRefreshing(false);
        } else {
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);
            binding.inboxRv.setVisibility(View.GONE);
            binding.noDataImage.setVisibility(View.VISIBLE);
            binding.noDataText.setVisibility(View.VISIBLE);
            binding.refreshLayout.setRefreshing(false);
        }
    }

    // Saved Data
    private void getSavedUserData() {
        email = savedEmailPreferences.getString("savedEmail", LOADING);
        if (!email.equals(LOADING)) {
            binding.randomEmailTV.setText(email);
            getInboxData(email);
        } else {
            getRandomEmail();
        }
    }

    private void saveEmail(String email) {
        editor.putString("savedEmail", email);
        editor.commit();
    }

    // Buttons
    private void randomEmailButton() {
        binding.randomEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRandomEmail();
            }
        });
    }

    private void copyButton() {
        binding.randomEmailCopyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!binding.randomEmailTV.getText().toString().trim().equals(LOADING)) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("RandomEmail", binding.randomEmailTV.getText().toString().trim());
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(MainActivity.this, "Copied", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Waiting for email", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}