package com.one.tempmail.UI;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
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

        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.no_internet_connection_dialog);
        dialog.setCancelable(false);
        dialog.show();
        MaterialButton retry = dialog.findViewById(R.id.retryBtn);
        retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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

            //Change Date Format
            changeDateFormat(inboxDataList);

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

    // Change Date Format
    private void changeDateFormat(ArrayList<InboxData> inboxDataList) {
        for(int i=0; i<inboxDataList.size(); i++){
            // Date and Time
            String[] dateAndTime, dateData, timeData, date, time;
            String year, day, month, hours, minutes, seconds, AMPM;
            int intHours, intMinutes;

            // Split Date and Time
            dateAndTime = inboxDataList.get(i).getDate().split(" ");

            // Split Date
            dateData = dateAndTime[0].split("-");

            // Split Time
            timeData = dateAndTime[1].split(":");

            // Set Date Values
            year = dateData[0];
            month = dateData[1];
            day = dateData[2];

            //Remove '0' from index[0]
            if(month.indexOf('0')==0){
                month = month.substring(1,month.length());
            }
            if(day.indexOf('0')==0){
                day = day.substring(1,day.length());
            }

            // Set Time Values
            hours = timeData[0];
            intHours = Integer.parseInt(hours) + 4;
            minutes = timeData[1];
            intMinutes = Integer.parseInt(minutes) - 30;
            seconds = timeData[2];

            //Set 12 hours period
            if(intHours <= 12){
                AMPM = "am";
            }else{
                intHours = intHours - 12;
                hours = intHours+"";
                AMPM = "pm";
            }

            // Convert months from number to name
            switch (month) {
                case "1":
                    month = "Jan";
                    break;
                case "2":
                    month = "Feb";
                    break;
                case "3":
                    month = "Mar";
                    break;
                case "4":
                    month = "Apr";
                    break;
                case "5":
                    month = "May";
                    break;
                case "6":
                    month = "Jun";
                    break;
                case "7":
                    month = "Jul";
                    break;
                case "8":
                    month = "Aug";
                    break;
                case "9":
                    month = "Sept";
                    break;
                case "10":
                    month = "Oct";
                    break;
                case "11":
                    month = "Nov";
                    break;
                case "12":
                    month = "Dec";
                    break;
            }
            String finalTime = day + " " + month + ", " + year + " | " + hours + ":" + minutes + " " + AMPM;
            inboxDataList.get(i).setDate(finalTime);
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