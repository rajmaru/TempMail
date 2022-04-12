package com.one.tempmail.CheckConnection;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.one.tempmail.R;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        View view = LayoutInflater.from(context).inflate(R.layout.no_internet_connection_dialog, null, false);
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setCancelable(false)
                .setView(view)
                .create();
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        dialog.show();
        MaterialButton retry = dialog.findViewById(R.id.retryBtn);

        if (CheckNetworkConnection.check(context)) {
            Log.d("status: ", CheckNetworkConnection.check(context) + "");
            dialog.dismiss();
        } else {
            Log.d("status: ", CheckNetworkConnection.check(context) + "");
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(CheckNetworkConnection.check(context)){
                        dialog.dismiss();
                    }
                }
            });

        }

    }
}
