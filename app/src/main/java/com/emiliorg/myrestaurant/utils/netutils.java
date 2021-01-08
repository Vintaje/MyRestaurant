package com.emiliorg.myrestaurant.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.Objects;

public class netutils {
    public static boolean hayConexion(Activity a) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) Objects.requireNonNull(a).getSystemService
                (Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = Objects.requireNonNull(connectivityManager).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
