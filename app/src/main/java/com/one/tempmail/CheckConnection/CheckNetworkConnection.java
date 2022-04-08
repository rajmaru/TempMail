package com.one.tempmail.CheckConnection;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.one.tempmail.R;

public class CheckNetworkConnection {

    public static boolean check(Context context){

        boolean active = false;

        // Getting Connectivity Manager
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // Getting Active Network Info
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        // Dialog Box
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.no_internet_connection_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        MaterialButton retry = dialog.findViewById(R.id.retryBtn);

        // Checking Network Connection
        if(activeNetwork != null){
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                Log.d("status: ", "active");
                dialog.dismiss();
                active = true;
                return active;
            }
        }else{
            Log.d("status: ", "inactive");
            dialog.show();
            // Retry Button
            retry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CheckNetworkConnection.check(context);
                    dialog.dismiss();
                }
            });
        }
        return active;
    }
}
