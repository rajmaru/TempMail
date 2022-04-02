package com.one.tempmail.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.one.tempmail.Adapter.AttachmentsAdapter;
import com.one.tempmail.Models.AttachmentsData;
import com.one.tempmail.R;
import com.one.tempmail.databinding.ActivityOpenMailBinding;

import java.util.ArrayList;

public class OpenMail extends AppCompatActivity {

    ActivityOpenMailBinding binding;
    AttachmentsAdapter adapter;
    ArrayList<AttachmentsData> fakeData;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_open_mail);

        getIntentData();
        setAttachmentsAdapter();

        Toast.makeText(this, "id:" + id, Toast.LENGTH_LONG).show();

    }

    private void getIntentData() {
        id = getIntent().getIntExtra("id", 0);
    }

    private void setAttachmentsAdapter() {
        fakeData = new ArrayList<>();
        fakeData.add(new AttachmentsData("asdfaf","application\\/pdf", 100));
        fakeData.add(new AttachmentsData("asdfaf","application\\/pdf", 100));
        fakeData.add(new AttachmentsData("asdfaf","application\\/pdf", 100));
        fakeData.add(new AttachmentsData("asdfaf","application\\/pdf", 100));
        fakeData.add(new AttachmentsData("asdfaf","application\\/pdf", 100));
        if (!fakeData.isEmpty()) {
            binding.attachmentsRv.setVisibility(View.VISIBLE);
            adapter = new AttachmentsAdapter(this, fakeData);
            binding.attachmentsRv.setAdapter(adapter);
            binding.attachmentsRv.setHasFixedSize(true);
            binding.attachmentsRv.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        } else {
            binding.attachmentsRv.setVisibility(View.GONE);
        }
    }
}