package com.one.tempmail.UI;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.webkit.WebSettingsCompat;
import androidx.webkit.WebViewFeature;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.one.tempmail.Adapter.AttachDecoration;
import com.one.tempmail.Adapter.AttachmentsAdapter;
import com.one.tempmail.CheckConnection.MyReceiver;
import com.one.tempmail.Models.AttachmentsData;
import com.one.tempmail.Models.MessageData;
import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.ViewModelFactory.ApiViewModelFactory;
import com.one.tempmail.databinding.ActivityOpenMailBinding;

import java.util.ArrayList;

public class OpenMail extends AppCompatActivity {

    ActivityOpenMailBinding binding;
    AttachmentsAdapter adapter;
    SharedPreferences savedEmailPreferences;
    BroadcastReceiver myReceiver;
    ApiViewModel apiViewModel;
    ShimmerFrameLayout shimmer;
    int id;
    String email, username, domain, filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_mail);

        initialize();
        getSavedData();
        getMessageData(id, email);

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

    private void getMessageData(Integer id, String email) {
        String[] stringList = email.split("@");
        username = stringList[0];
        domain = stringList[1];
        apiViewModel.getMessageData(username, domain, id).observe(this, new Observer<MessageData>() {
            @Override
            public void onChanged(MessageData messageData) {
                if (messageData != null) {
                    // Stop Shimmer Effect
                    shimmer.stopShimmer();
                    shimmer.setVisibility(View.GONE);

                    // Set Visibility Layout
                    binding.fromLayout.setVisibility(View.VISIBLE);
                    binding.subjectLayout.setVisibility(View.VISIBLE);
                    binding.mailBodyLayout.setVisibility(View.VISIBLE);

                    // Set Values
                    binding.senderEmailOM.setText(messageData.getFrom());
                    binding.subjectTV.setText(messageData.getSubject());

                    // Set Values in Webview
                    String htmlBody;
                    String body = messageData.getHtmlBody();
                    if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK)) {
                        switch (getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) {
                            case Configuration.UI_MODE_NIGHT_YES:
                                htmlBody = "<font size=\"4\"; color=\"#D2D9DD\">" + body + "</font>";
                                binding.webview.setBackgroundColor(0);
                                binding.webview.setVerticalScrollBarEnabled(false);
                                binding.webview.setHorizontalScrollBarEnabled(false);
                                binding.webview.loadDataWithBaseURL(null, htmlBody, "text/html", "utf-8", null);
                                WebSettingsCompat.setForceDark(binding.webview.getSettings(), WebSettingsCompat.FORCE_DARK_ON);
                                break;
                            case Configuration.UI_MODE_NIGHT_NO:
                            case Configuration.UI_MODE_NIGHT_UNDEFINED:
                                htmlBody = "<font size=\"4\"; color=\"#47555a\">" + body + "</font>";
                                binding.webview.setBackgroundColor(0);
                                binding.webview.setVerticalScrollBarEnabled(false);
                                binding.webview.setHorizontalScrollBarEnabled(false);
                                binding.webview.loadDataWithBaseURL(null, htmlBody, "text/html", "utf-8", null);
                                WebSettingsCompat.setForceDark(binding.webview.getSettings(), WebSettingsCompat.FORCE_DARK_OFF);
                                break;
                        }
                    }

                    // Set data in adapter
                    setAttachmentsAdapter(messageData.getAttachments());
                }
            }
        });
    }


    private void initialize() {
        myReceiver = new MyReceiver();
        apiViewModel = new ViewModelProvider(this, new ApiViewModelFactory(this)).get(ApiViewModel.class);
        savedEmailPreferences = getSharedPreferences("savedEmailPreferences", MODE_PRIVATE);
        shimmer = (ShimmerFrameLayout) findViewById(R.id.openMailShimmer);
        shimmer.startShimmer();
    }

    private void getSavedData() {
        id = getIntent().getIntExtra("id", 0);
        email = savedEmailPreferences.getString("savedEmail", null);
    }

    private void setAttachmentsAdapter(ArrayList<AttachmentsData> attachmentsDataList) {
        if (!attachmentsDataList.isEmpty()) {
            binding.attachmentsRv.setVisibility(View.VISIBLE);
            adapter = new AttachmentsAdapter(this, attachmentsDataList);
            binding.attachmentsRv.setAdapter(adapter);
            binding.attachmentsRv.setHasFixedSize(true);
            binding.attachmentsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
            binding.attachmentsRv.addItemDecoration(new AttachDecoration(attachmentsDataList.size()));
        } else {
            binding.attachmentsRv.setVisibility(View.GONE);
        }
    }

    public void downloadAttachements(String filename) {
        apiViewModel.downloadAttachments(username, domain, id, filename);
    }
}