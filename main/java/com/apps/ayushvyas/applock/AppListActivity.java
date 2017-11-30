package com.apps.ayushvyas.applock;

import android.app.ActivityManager;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.apps.ayushvyas.applock.Adapter.AppListAdapter;
import com.apps.ayushvyas.applock.Service.CheckForegroundAppService;

import java.util.List;
import java.util.TreeMap;

public class AppListActivity extends AppCompatActivity implements AppListAdapter.AdapterCallback {

    private List<ApplicationInfo> apps;
    private RecyclerView mRecyclerView;
    private AppListAdapter mMyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_list);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        this.mMyAdapter = new AppListAdapter(this);
        PackageManager pm = this.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);


        for (ApplicationInfo packageInfo : packages)

        {
            if (!isSystemPackage(packageInfo)) {
                //add to  list
                apps.add(packageInfo);
            }
        }
        showList(apps);
        this.startService(
                new Intent(this, CheckForegroundAppService.class));
    }



    @Override
    public void onMethodCallback(List<String> blockedList) {
       AppLock appLock = (AppLock) getApplicationContext();
        appLock.setblockedApps(blockedList);
    }

    private void showList(List<ApplicationInfo> apps) {
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);
        AppListAdapter mAppListAdapter = new AppListAdapter(this, apps);
        mRecyclerView.setAdapter(mAppListAdapter);

    }

    private boolean isSystemPackage(ApplicationInfo pkgInfo) {
        return ((pkgInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private void setBlockedAppsList(List<String> list){

    }

}
