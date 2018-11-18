package com.seb.ahmed.weather_app.Abstract;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.seb.ahmed.weather_app.common.NetworkUtil;
import com.seb.ahmed.weather_app.common.PermissionsHelper;


public abstract class AbstractActivity extends AppCompatActivity implements NetworkUtil.ConnectivityListener{

    NetworkUtil connectivity;
    AlertDialog dialog;
    PermissionsHelper helper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        connectivity = new NetworkUtil(this);
        connectivity.register(this);

        dialog= new AlertDialog.Builder(this).create();
        dialog.setTitle("Connection Lost");
        dialog.setMessage("Check your internet connection");
        //TODO: Add Reachability
//        dialog.setButton(DialogInterface.BUTTON_POSITIVE,
//                "Retry", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        if(connectivity.isConnectionAvilable(getApplicationContext()))
//                        {
//                            onConnectionAvailable();
//                        }
//                        else
//                        {
//                            onConnectionUnavailable();
//                        }
//                    }
//                }
//        );
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        helper=new PermissionsHelper(this);
        helper.areAllRequiredPermissionsGranted(new String[]{ Manifest.permission.INTERNET},new int[]{1});

    }
//
//    protected abstract void setDataBinding();
//
//    protected abstract void getDataBinding();

    @Override
    public void onConnectionAvailable() {
        if(dialog.isShowing())
        {
            dialog.dismiss();
        }
    }

    @Override
    public void onConnectionUnavailable() {


            dialog.show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(helper.checkPermissions())
        {
            Log.d("Permission","granteed");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        connectivity.unregister(this);
    }


}
