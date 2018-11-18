package com.seb.ahmed.weather_app.common;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by senosy on 11/05/2018.
 */

public class NetworkUtil extends BroadcastReceiver {
    private final ConnectivityListener mConnectivityListener;

    public NetworkUtil() {
        mConnectivityListener=null;
    }

    public NetworkUtil(ConnectivityListener mConnectivityListener) {
        this.mConnectivityListener = mConnectivityListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        final boolean isConnected = isConnectionAvailable(context);
        boolean reachable=isConnectedToServer(Const.BASE_URL,2000);
        try {
            if (isConnected &&reachable) {
                mConnectivityListener.onConnectionAvailable();
            }else{
                mConnectivityListener.onConnectionUnavailable();
            }
        }catch (Exception e)
        {

        }

    }

    public boolean isConnectionAvailable(Context context)
    {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public boolean isConnectedToServer(String url, int timeout) {
        try{
            URL myUrl = new URL(url);
            final URLConnection connection = myUrl.openConnection();
            connection.setConnectTimeout(timeout);
            AsyncTask.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.connect();
                        mConnectivityListener.onConnectionAvailable();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            return true;
        } catch (Exception e) {
            // Handle your exceptions
            return false;
        }
    }

    public void register(Context context){
        context.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void unregister(Context context){
        context.unregisterReceiver(this);
    }

    public interface ConnectivityListener {

        /**
         * Called when a data connection has been established. Can use to
         * trigger any waiting behaviour
         */
        public void onConnectionAvailable();
        public void onConnectionUnavailable();
    }
}
