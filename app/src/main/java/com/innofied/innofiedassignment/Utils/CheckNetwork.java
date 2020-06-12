package com.innofied.innofiedassignment.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Mohit on 9/24/2019.
 */

public class CheckNetwork {

    public static boolean isInternetAvailable(Context context) {
        NetworkInfo info = (NetworkInfo) ((ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();

        if (info == null) return false;
        else {
            if(info.isConnected()) return true;
            else return true;
        }
    }
}
