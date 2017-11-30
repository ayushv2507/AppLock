package com.apps.ayushvyas.applock;

import android.app.Application;
import android.app.ListActivity;
import android.content.pm.ApplicationInfo;

import java.util.List;

/**
 * Created by ayushvyas on 10/5/17.
 */

public class AppLock extends Application {
    public List<String> blockedApps;
    public List<String> getblockedApps() {
        return blockedApps;
    }

    public void setblockedApps(List<String> someVariable) {
        this.blockedApps = someVariable;
    }
}
