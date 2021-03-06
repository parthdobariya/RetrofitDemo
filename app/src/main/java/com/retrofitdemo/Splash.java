package com.retrofitdemo;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.retrofitdemo.netUtils.DBHelper;
import com.retrofitdemo.netUtils.MyPreferences;
import com.retrofitdemo.netUtils.RuntimePermissionsActivity;

import java.io.File;
import java.io.IOException;

public class Splash extends RuntimePermissionsActivity {

    MyPreferences myPreferences;
    DBHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        myPreferences = new MyPreferences(Splash.this);
        db = new DBHelper(Splash.this);
        /* todo 31-10-2017 create project date */

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Splash.super.requestAppPermissions(new
                                String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE
                        }, R.string.runtime_permissions_txt
                        , 20);

            }
        }, 1000);
    }

    @Override
    public void onPermissionsGranted(int requestCode) {

        File folder = new File(Environment.getExternalStorageDirectory(), "" + GlobalElements.directory);
        if (!folder.exists()) {
            folder.mkdir();
        }

        try {
            db.createDataBase();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //db.DeleteTable(DBHelper.COUNTRY);
        //db.importDB(DBHelper.DB_NAME);

        try {
            TelephonyManager tele = (TelephonyManager) getApplicationContext()
                    .getSystemService(Context.TELEPHONY_SERVICE);
         String   imei = tele.getDeviceId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Intent i = new Intent(Splash.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
