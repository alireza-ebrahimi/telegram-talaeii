package com.google.android.gms.gcm;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.WorkerThread;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.iid.zzaa;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    private static GcmNetworkManager zziji;
    private Context mContext;
    private final PendingIntent zzekd;
    private final Map<String, Map<String, Boolean>> zzijj = new ArrayMap();

    private GcmNetworkManager(Context context) {
        this.mContext = context;
        this.zzekd = PendingIntent.getBroadcast(this.mContext, 0, new Intent().setPackage("com.google.example.invalidpackage"), 0);
    }

    public static GcmNetworkManager getInstance(Context context) {
        GcmNetworkManager gcmNetworkManager;
        synchronized (GcmNetworkManager.class) {
            if (zziji == null) {
                zziji = new GcmNetworkManager(context.getApplicationContext());
            }
            gcmNetworkManager = zziji;
        }
        return gcmNetworkManager;
    }

    private final Intent zzawe() {
        String zzdr = zzaa.zzdr(this.mContext);
        int i = -1;
        if (zzdr != null) {
            i = GoogleCloudMessaging.zzdn(this.mContext);
        }
        if (zzdr == null || i < 5000000) {
            Log.e("GcmNetworkManager", "Google Play Services is not available, dropping GcmNetworkManager request. code=" + i);
            return null;
        }
        Intent intent = new Intent("com.google.android.gms.gcm.ACTION_SCHEDULE");
        intent.setPackage(zzdr);
        intent.putExtra(SettingsJsonConstants.APP_KEY, this.zzekd);
        intent.putExtra(Param.SOURCE, 4);
        intent.putExtra("source_version", 12211000);
        return intent;
    }

    static void zzid(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        } else if (100 < str.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private final boolean zzie(String str) {
        zzbq.checkNotNull(str, "GcmTaskService must not be null.");
        PackageManager packageManager = this.mContext.getPackageManager();
        if (packageManager == null) {
            Collection emptyList = Collections.emptyList();
        } else {
            Object queryIntentServices = packageManager.queryIntentServices(str != null ? new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK).setClassName(this.mContext, str) : new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK).setPackage(this.mContext.getPackageName()), 0);
        }
        if (r2 == null ? true : r2.isEmpty()) {
            Log.e("GcmNetworkManager", String.valueOf(str).concat(" is not available. This may cause the task to be lost."));
        } else {
            for (ResolveInfo resolveInfo : r2) {
                if (resolveInfo.serviceInfo == null || !resolveInfo.serviceInfo.enabled) {
                }
            }
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 118).append("The GcmTaskService class you provided ").append(str).append(" does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY").toString());
        }
        return true;
    }

    @WorkerThread
    public void cancelAllTasks(Class<? extends GcmTaskService> cls) {
        Parcelable componentName = new ComponentName(this.mContext, cls);
        zzie(componentName.getClassName());
        Intent zzawe = zzawe();
        if (zzawe != null) {
            zzawe.putExtra("scheduler_action", "CANCEL_ALL");
            zzawe.putExtra("component", componentName);
            this.mContext.sendBroadcast(zzawe);
        }
    }

    @WorkerThread
    public void cancelTask(String str, Class<? extends GcmTaskService> cls) {
        Parcelable componentName = new ComponentName(this.mContext, cls);
        zzid(str);
        zzie(componentName.getClassName());
        Intent zzawe = zzawe();
        if (zzawe != null) {
            zzawe.putExtra("scheduler_action", "CANCEL_TASK");
            zzawe.putExtra("tag", str);
            zzawe.putExtra("component", componentName);
            this.mContext.sendBroadcast(zzawe);
        }
    }

    @WorkerThread
    public synchronized void schedule(Task task) {
        zzie(task.getServiceName());
        Intent zzawe = zzawe();
        if (zzawe != null) {
            Bundle extras = zzawe.getExtras();
            extras.putString("scheduler_action", "SCHEDULE_TASK");
            task.toBundle(extras);
            zzawe.putExtras(extras);
            this.mContext.sendBroadcast(zzawe);
            Map map = (Map) this.zzijj.get(task.getServiceName());
            if (map != null && map.containsKey(task.getTag())) {
                map.put(task.getTag(), Boolean.valueOf(true));
            }
        }
    }

    @Hide
    final synchronized boolean zzaa(String str, String str2) {
        Map map;
        map = (Map) this.zzijj.get(str2);
        if (map == null) {
            map = new ArrayMap();
            this.zzijj.put(str2, map);
        }
        return map.put(str, Boolean.valueOf(false)) == null;
    }

    @Hide
    final synchronized void zzab(String str, String str2) {
        Map map = (Map) this.zzijj.get(str2);
        if (map != null) {
            if ((map.remove(str) != null ? 1 : null) != null && map.isEmpty()) {
                this.zzijj.remove(str2);
            }
        }
    }

    @Hide
    final synchronized boolean zzac(String str, String str2) {
        boolean booleanValue;
        Map map = (Map) this.zzijj.get(str2);
        if (map != null) {
            Boolean bool = (Boolean) map.get(str);
            booleanValue = bool == null ? false : bool.booleanValue();
        } else {
            booleanValue = false;
        }
        return booleanValue;
    }

    @Hide
    final synchronized boolean zzif(String str) {
        return this.zzijj.containsKey(str);
    }
}
