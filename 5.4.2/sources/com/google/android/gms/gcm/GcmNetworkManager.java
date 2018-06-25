package com.google.android.gms.gcm;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.p022f.C0464a;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.util.CollectionUtils;
import com.google.android.gms.common.util.GmsVersion;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import javax.annotation.concurrent.GuardedBy;

public class GcmNetworkManager {
    public static final int RESULT_FAILURE = 2;
    public static final int RESULT_RESCHEDULE = 1;
    public static final int RESULT_SUCCESS = 0;
    @GuardedBy("GcmNetworkManager.class")
    private static GcmNetworkManager zzg;
    private final Context zzh;
    @GuardedBy("this")
    private final Map<String, Map<String, Boolean>> zzi = new C0464a();

    private GcmNetworkManager(Context context) {
        this.zzh = context;
    }

    public static GcmNetworkManager getInstance(Context context) {
        GcmNetworkManager gcmNetworkManager;
        synchronized (GcmNetworkManager.class) {
            if (zzg == null) {
                zzg = new GcmNetworkManager(context.getApplicationContext());
            }
            gcmNetworkManager = zzg;
        }
        return gcmNetworkManager;
    }

    private final zzn zzd() {
        if (GoogleCloudMessaging.zzf(this.zzh) >= GmsVersion.VERSION_LONGHORN) {
            return new zzm(this.zzh);
        }
        Log.e("GcmNetworkManager", "Google Play services is not available, dropping all GcmNetworkManager requests");
        return new zzo();
    }

    static void zzd(String str) {
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Must provide a valid tag.");
        } else if (100 < str.length()) {
            throw new IllegalArgumentException("Tag is larger than max permissible tag length (100)");
        }
    }

    private final boolean zze(String str) {
        Preconditions.checkNotNull(str, "GcmTaskService must not be null.");
        PackageManager packageManager = this.zzh.getPackageManager();
        Collection emptyList;
        if (packageManager == null) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = packageManager.queryIntentServices(str != null ? new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK).setClassName(this.zzh, str) : new Intent(GcmTaskService.SERVICE_ACTION_EXECUTE_TASK).setPackage(this.zzh.getPackageName()), 0);
        }
        if (CollectionUtils.isEmpty((Collection) r0)) {
            Log.e("GcmNetworkManager", String.valueOf(str).concat(" is not available. This may cause the task to be lost."));
        } else {
            for (ResolveInfo resolveInfo : r0) {
                if (resolveInfo.serviceInfo == null || !resolveInfo.serviceInfo.enabled) {
                }
            }
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 118).append("The GcmTaskService class you provided ").append(str).append(" does not seem to support receiving com.google.android.gms.gcm.ACTION_TASK_READY").toString());
        }
        return true;
    }

    public void cancelAllTasks(Class<? extends GcmTaskService> cls) {
        ComponentName componentName = new ComponentName(this.zzh, cls);
        zze(componentName.getClassName());
        zzd().zzd(componentName);
    }

    public void cancelTask(String str, Class<? extends GcmTaskService> cls) {
        ComponentName componentName = new ComponentName(this.zzh, cls);
        zzd(str);
        zze(componentName.getClassName());
        zzd().zzd(componentName, str);
    }

    public synchronized void schedule(Task task) {
        zze(task.getServiceName());
        if (zzd().zzd(task)) {
            Map map = (Map) this.zzi.get(task.getServiceName());
            if (map != null && map.containsKey(task.getTag())) {
                map.put(task.getTag(), Boolean.valueOf(true));
            }
        }
    }

    final synchronized boolean zzd(String str, String str2) {
        Map map;
        map = (Map) this.zzi.get(str2);
        if (map == null) {
            map = new C0464a();
            this.zzi.put(str2, map);
        }
        return map.put(str, Boolean.valueOf(false)) == null;
    }

    final synchronized void zze(String str, String str2) {
        Map map = (Map) this.zzi.get(str2);
        if (map != null) {
            if ((map.remove(str) != null ? 1 : null) != null && map.isEmpty()) {
                this.zzi.remove(str2);
            }
        }
    }

    final synchronized boolean zzf(String str) {
        return this.zzi.containsKey(str);
    }

    final synchronized boolean zzf(String str, String str2) {
        boolean booleanValue;
        Map map = (Map) this.zzi.get(str2);
        if (map != null) {
            Boolean bool = (Boolean) map.get(str);
            booleanValue = bool == null ? false : bool.booleanValue();
        } else {
            booleanValue = false;
        }
        return booleanValue;
    }
}
