package com.one.tempmail.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.tempmail.Adapter.AttachmentsAdapter;
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

    private void getMessageData(Integer id, String email) {
        String[] stringList = email.split("@");
        username = stringList[0];
        domain = stringList[1];
        apiViewModel.getMessageData(username, domain, id).observe(this, new Observer<MessageData>() {
            @Override
            public void onChanged(MessageData messageData) {
                binding.senderEmailOM.setText(messageData.getFrom());
                binding.subjectTV.setText(messageData.getSubject());
                binding.mailBodyOM.setText(messageData.getTextBody());
                setAttachmentsAdapter(messageData.getAttachments());
            }
        });
    }

    private void initialize() {
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        savedEmailPreferences = getSharedPreferences("savedEmailPreferences", MODE_PRIVATE);
    }

    private void getSavedData() {
        id = getIntent().getIntExtra("id", 0);
        email = savedEmailPreferences.getString("savedEmail", null);
        Log.d("TAG", email+"");
    }

    private void setAttachmentsAdapter(ArrayList<AttachmentsData> attachmentsDataList) {
        if (!attachmentsDataList.isEmpty()) {
            binding.attachmentsRv.setVisibility(View.VISIBLE);
            adapter = new AttachmentsAdapter(this, attachmentsDataList);
            binding.attachmentsRv.setAdapter(adapter);
            binding.attachmentsRv.setHasFixedSize(true);
            binding.attachmentsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        } else {
            binding.attachmentsRv.setVisibility(View.GONE);
        }
    }

    public void downloadAttachements(){
        apiViewModel.downloadAttachments(username, domain, id, filename);
    }
}