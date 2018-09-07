package com.anwar014.touristspot.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by abc on 05-04-2018.
 */

public class NetworkUtil {
    public static Boolean isInternetConnected(Context ctx) {
        ConnectivityManager con = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (con != null) {
            NetworkInfo activeNetwork = con.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnected()) {
                return true;
            }
        }
        return false;
    }

}
