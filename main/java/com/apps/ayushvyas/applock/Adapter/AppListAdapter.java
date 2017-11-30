package com.apps.ayushvyas.applock.Adapter;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.apps.ayushvyas.applock.AppListActivity;
import com.apps.ayushvyas.applock.AppLock;
import com.apps.ayushvyas.applock.R;

import java.util.List;

/**
 * Created by ayushvyas on 10/3/17.
 */

public class AppListAdapter extends RecyclerView.Adapter<AppListAdapter.ViewHolder> {
    private List<ApplicationInfo> appList;
    private List<String> blockedList;
    private Context context;
    private PackageManager pm;
    private AdapterCallback mAdapterCallback;


    public AppListAdapter(Context context) {
        this.context = context;
        try {
            this.mAdapterCallback = ((AdapterCallback) context);
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement AdapterCallback.");
        }

    }

    public AppListAdapter(Context context, List<ApplicationInfo> itemList) {
        this.appList = itemList;
        this.context = context;
        pm = context.getPackageManager();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.app_item, null, false);
        return new ViewHolder(layoutView);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.appName.setText(appList.get(position).packageName);
        holder.appIcon.setImageDrawable(appList.get(position).loadIcon(pm));
        if (blockedList.contains(appList.get(position).packageName))
        {
            holder.s.setChecked(true);
        }
        else
        {
            holder.s.setChecked(false);
        }
        holder.s.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if (isChecked)
                {
                    blockedList.add(appList.get(position).packageName);
                }
                else
                {
                    blockedList.remove(appList.get(position).packageName);
                }
            }
        });
        try {
            mAdapterCallback.onMethodCallback(blockedList);
        } catch (ClassCastException exception) {
            // do something
        }
    }



    @Override
    public int getItemCount() {
        if (appList != null && !appList.isEmpty())
            return appList.size();
        else return 0;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView appName;
        ImageView appIcon;
        Switch s;

        ViewHolder(View itemView) {
            super(itemView);

            appName = (TextView) itemView.findViewById(R.id.app_name);
            appIcon = (ImageView) itemView.findViewById(R.id.app_icon);
            s = (Switch) itemView.findViewById(R.id.block);
        }
    }

    public static interface AdapterCallback {
        void onMethodCallback(List<String> blockedList);
    }

}
