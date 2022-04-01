package com.cwt.phonerepair;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Build;

import androidx.annotation.RequiresApi;

public class Utility {
    
    public static void showErrorMessage(String string, Context context) {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static boolean CheckNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(String.valueOf(context));

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
}
