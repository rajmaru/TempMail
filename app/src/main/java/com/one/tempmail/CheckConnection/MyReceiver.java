package com.one.tempmail.CheckConnection;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.one.tempmail.R;

public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
       //Diallog Box
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_internet_connection_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        MaterialButton retry = dialog.findViewById(R.id.retryBtn);

        if(CheckNetworkConnection.check(context)){
            Log.d("status: ", CheckNetworkConnection.check(context)+"");
            dialog.dismiss();
        }else {
            Log.d("status: ", CheckNetworkConnection.check(context)+"");
            dialog.show();
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
