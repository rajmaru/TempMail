package com.one.tempmail.UI;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.one.tempmail.R;
import com.one.tempmail.ViewModel.ApiViewModel;
import com.one.tempmail.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    ActivityMainBinding binding;
    //  InboxAdapter adapter;
    ApiViewModel apiViewModel;
    SharedPreferences savedEmailPreferences;
    SharedPreferences.Editor editor;
    ArrayList<String> randomEmailList;
    public static final String LOADING = "Loading...";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SplashScreen.installSplashScreen(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initialize();
        getSavedUserData();
        getRandomEmail();
        randomEmailButton();
        copyButton();

    }

    private void initialize() {
        apiViewModel = new ViewModelProvider(this).get(ApiViewModel.class);
        savedEmailPreferences = getSharedPreferences("randomEmailPreferences", MODE_PRIVATE);
        editor = savedEmailPreferences.edit();
        randomEmailList = new ArrayList<>();
    }

    private void getSavedUserData() {
        String savedEmail = savedEmailPreferences.getString("savedEmail", LOADING);
        if (savedEmail != null) {
            binding.randomEmailTV.setText(savedEmail);
        } else {
            getRandomEmail();
        }
    }

    private void saveUserData(String email) {
        editor.putString("savedEmail", email);
        editor.commit();
    }

    private void getRandomEmail() {
        apiViewModel.getRandomEmail()
                .observe(this, new Observer<ArrayList<String>>() {
                    @Override
                    public void onChanged(ArrayList<String> strings) {
                        setRandomEmail(strings);
                        saveUserData(strings.get(0));
                    }
                });
    }

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

    private void setRandomEmail(ArrayList<String> email) {
        if (!email.isEmpty()) {
            binding.randomEmailTV.setText(email.get(0));
        } else {
            binding.randomEmailTV.setText(LOADING);
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