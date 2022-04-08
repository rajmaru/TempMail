package com.one.tempmail.UI;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.one.tempmail.Adapter.AttachmentsAdapter;
import com.one.tempmail.Adapter.AttachDecoration;
import com.one.tempmail.CheckConnection.MyReceiver;
import com.one.tempmail.Models.AttachmentsData;
import com.one.tempmail.Models.MessageData;
import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.databinding.ActivityOpenMailBinding;

import java.util.ArrayList;

public class OpenMail extends AppCompatActivity {

    ActivityOpenMailBinding binding;
    AttachmentsAdapter adapter;
    SharedPreferences savedEmailPreferences;
    ApiViewModel apiViewModel;
    ShimmerFrameLayout shimmer;
    BroadcastReceiver myreceiver;
    int id;
    String email, username, domain, filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_mail);

        initialize();
       regBroadcastIntent();
        getSavedData();
        getMessageData(id, email);

    }

    private void getMessageData(Integer id, String email) {
        String[] stringList = email.split("@");
        username = stringList[0];
        domain = stringList[1];
        apiViewModel.getMessageData(username, domain, id).observe(this, new Observer<MessageData>() {
            @Override
            public void onChanged(MessageData messageData) {
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
                binding.mailBodyOM.setText(messageData.getTextBody());
                setAttachmentsAdapter(messageData.getAttachments());
            }
        });
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        regBroadcastIntent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        regBroadcastIntent();
    }

    @Override
    protected void onResume() {
        super.onResume();
        regBroadcastIntent();
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregBroadcastIntent();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregBroadcastIntent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregBroadcastIntent();
    }

    private void initialize() {
        myreceiver = new MyReceiver();
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        savedEmailPreferences = getSharedPreferences("savedEmailPreferences", MODE_PRIVATE);
        shimmer = (ShimmerFrameLayout) findViewById(R.id.openMailShimmer);
        shimmer.startShimmer();
    }

    private void regBroadcastIntent() {
        registerReceiver(myreceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unregBroadcastIntent() {
        unregisterReceiver(myreceiver);
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

    public void downloadAttachements() {
        apiViewModel.downloadAttachments(username, domain, id, filename);
    }
}