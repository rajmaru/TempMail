package com.one.tempmail.UI;

import android.content.BroadcastReceiver;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.one.tempmail.CheckConnection.MyReceiver;
import com.one.tempmail.Models.InboxData;
import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.ViewModelFactory.ApiViewModelFactory;
import com.one.tempmail.databinding.ActivityMainBinding;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    InboxAdapter adapter;
    ApiViewModel apiViewModel;
    BroadcastReceiver myReceiver;
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

        binding.inboxRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getInboxData(email);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(myReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
    }

    private void initialize() {
        myReceiver = new MyReceiver();
        apiViewModel = new ViewModelProvider(this, new ApiViewModelFactory(this)).get(ApiViewModel.class);
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
        if (inboxDataList != null && !inboxDataList.isEmpty()) {
            // Stop Shimmer Effect
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);

            //Set Visibility
            binding.inboxRv.setVisibility(View.VISIBLE);
            binding.noDataImage.setVisibility(View.GONE);
            binding.noDataText.setVisibility(View.GONE);

            //Change Date Format
            changeDateFormat(inboxDataList);

            //Set Data
            adapter = new InboxAdapter(MainActivity.this, inboxDataList);
            binding.inboxRv.setAdapter(adapter);
            binding.inboxRv.setHasFixedSize(true);
            binding.inboxRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));

            //Set Refreshing Off
            binding.inboxRefresh.setRefreshing(false);
        } else {
            shimmer.stopShimmer();
            shimmer.setVisibility(View.GONE);
            binding.inboxRv.setVisibility(View.GONE);
            binding.noDataImage.setVisibility(View.VISIBLE);
            binding.noDataText.setVisibility(View.VISIBLE);
            binding.inboxRefresh.setRefreshing(false);
        }
    }

    // Change Date Format
    private void changeDateFormat(ArrayList<InboxData> inboxDataList) {
        for (int i = 0; i < inboxDataList.size(); i++) {
            String sDate = inboxDataList.get(i).getDate();
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
                sdf.setTimeZone(TimeZone.getTimeZone("Europe/Amsterdam"));
                Date mDate = null;
                mDate = sdf.parse(sDate);

                SimpleDateFormat sdfOutPutToSend = new SimpleDateFormat("dd MMM yyyy, hh:mm a", Locale.ENGLISH);
                sdfOutPutToSend.setTimeZone(TimeZone.getDefault());
                sDate = sdfOutPutToSend.format(mDate);
            } catch (ParseException | java.text.ParseException e) {
                e.printStackTrace();
            }
            sDate = sDate.replace("AM", "am").replace("PM", "pm");
            inboxDataList.get(i).setDate(sDate);
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