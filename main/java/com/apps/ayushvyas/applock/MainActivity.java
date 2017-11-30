package com.apps.ayushvyas.applock;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.List;


//TODO create a service that monitors app open and show lock screen. Create Adapter and recyclerview for displaying list with toggle

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showPassword(View view) {
        Intent password = new Intent(this, PasswordActivity.class);
        startActivity(password);
        finish();
    }

    public void getAppList(View view) {
        Intent applist = new Intent(this, AppListActivity.class);
        startActivity(applist);
    }

}
