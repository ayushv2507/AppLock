package com.apps.ayushvyas.applock.Service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.apps.ayushvyas.applock.AppLock;
import com.apps.ayushvyas.applock.PasswordActivity;

import java.util.List;
import java.util.TreeMap;

public class CheckForegroundAppService extends Service {
    private Thread dThread;
    public static boolean threadIsTerminate = false;

    public CheckForegroundAppService() {
    }

    @Override
    public void onCreate() {

        super.onCreate();
        dThread = new Thread(checkDataRunnable);
        dThread.start();


    }

    private Runnable checkDataRunnable = new Runnable() {

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
        @Override
        public void run() {

            ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);

            String lastLoadPackageName = "";
            String packgeName = "";
            while (!threadIsTerminate) {
                try {
                        packgeName = getForegroundApp();
                    AppLock appLock = (AppLock) getApplicationContext();
                  if(appLock.getblockedApps().contains(packgeName)){
                      Intent password = new Intent(getApplicationContext(), PasswordActivity.class);
                      startActivity(password);
                  }
                        Thread.sleep(300);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    public String getForegroundApp() {
        String currentApp = "NULL";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            UsageStatsManager usm = (UsageStatsManager) this.getSystemService(Context.USAGE_STATS_SERVICE);
            long time = System.currentTimeMillis();
            List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, time - 1000 * 1000, time);
            if (appList != null && appList.size() > 0) {
                TreeMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                for (UsageStats usageStats : appList) {
                    mySortedMap.put(usageStats.getLastTimeUsed(), usageStats);
                }
                if (mySortedMap != null && !mySortedMap.isEmpty()) {
                    currentApp = mySortedMap.get(mySortedMap.lastKey()).getPackageName();
                }
            }
        } else {
            ActivityManager am = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            currentApp = tasks.get(0).processName;
        }

        return currentApp;
    }


                    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
