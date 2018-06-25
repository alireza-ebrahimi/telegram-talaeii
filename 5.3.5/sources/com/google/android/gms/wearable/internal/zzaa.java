package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import android.support.annotation.NonNull;
import com.google.android.gms.common.api.GoogleApi.zza;
import com.google.android.gms.common.api.internal.zzci;
import com.google.android.gms.common.api.internal.zzcm;
import com.google.android.gms.common.internal.zzbj;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;
import com.google.android.gms.wearable.CapabilityInfo;
import java.util.Map;

public final class zzaa extends CapabilityClient {
    private final CapabilityApi zzlsu = new zzo();

    public zzaa(@NonNull Activity activity, @NonNull zza zza) {
        super(activity, zza);
    }

    public zzaa(@NonNull Context context, @NonNull zza zza) {
        super(context, zza);
    }

    private final Task<Void> zza(zzci<OnCapabilityChangedListener> zzci, OnCapabilityChangedListener onCapabilityChangedListener, IntentFilter[] intentFilterArr) {
        return zza(new zzaf(onCapabilityChangedListener, intentFilterArr, zzci, null), new zzag(onCapabilityChangedListener, zzci.zzakx(), null));
    }

    public final Task<Void> addListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, @NonNull Uri uri, int i) {
        zzc.zzb(onCapabilityChangedListener, "listener must not be null");
        zzc.zzb(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        zzbq.checkArgument(z, "invalid filter type");
        IntentFilter zza = zzgj.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, i);
        return zza(zzcm.zzb(onCapabilityChangedListener, getLooper(), "CapabilityListener"), onCapabilityChangedListener, new IntentFilter[]{zza});
    }

    public final Task<Void> addListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, @NonNull String str) {
        String str2;
        zzc.zzb(onCapabilityChangedListener, "listener must not be null");
        zzc.zzb(str, "capability must not be null");
        IntentFilter zzoe = zzgj.zzoe("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        if (str.startsWith("/")) {
            str2 = str;
        } else {
            String str3 = "/";
            str2 = String.valueOf(str);
            str2 = str2.length() != 0 ? str3.concat(str2) : new String(str3);
        }
        zzoe.addDataPath(str2, 0);
        IntentFilter[] intentFilterArr = new IntentFilter[]{zzoe};
        Looper looper = getLooper();
        String str4 = "CapabilityListener:";
        String valueOf = String.valueOf(str2);
        return zza(zzcm.zzb(onCapabilityChangedListener, looper, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4)), new zzae(onCapabilityChangedListener, str2), intentFilterArr);
    }

    public final Task<Void> addLocalCapability(@NonNull String str) {
        zzc.zzb(str, "capability must not be null");
        return zzbj.zzb(this.zzlsu.addLocalCapability(zzahw(), str));
    }

    public final Task<Map<String, CapabilityInfo>> getAllCapabilities(int i) {
        return zzbj.zza(this.zzlsu.getAllCapabilities(zzahw(), i), zzac.zzgui);
    }

    public final Task<CapabilityInfo> getCapability(@NonNull String str, int i) {
        zzc.zzb(str, "capability must not be null");
        return zzbj.zza(this.zzlsu.getCapability(zzahw(), str, i), zzab.zzgui);
    }

    public final Task<Boolean> removeListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener) {
        zzc.zzb(onCapabilityChangedListener, "listener must not be null");
        return zza(zzcm.zzb(onCapabilityChangedListener, getLooper(), "CapabilityListener").zzakx());
    }

    public final Task<Boolean> removeListener(@NonNull OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        String str2;
        Object concat;
        zzc.zzb(onCapabilityChangedListener, "listener must not be null");
        zzc.zzb(str, "capability must not be null");
        if (str.startsWith("/")) {
            str2 = str;
        } else {
            String str3 = "/";
            str2 = String.valueOf(str);
            concat = str2.length() != 0 ? str3.concat(str2) : new String(str3);
        }
        Looper looper = getLooper();
        String str4 = "CapabilityListener:";
        str2 = String.valueOf(concat);
        return zza(zzcm.zzb(onCapabilityChangedListener, looper, str2.length() != 0 ? str4.concat(str2) : new String(str4)).zzakx());
    }

    public final Task<Void> removeLocalCapability(@NonNull String str) {
        zzc.zzb(str, "capability must not be null");
        return zzbj.zzb(this.zzlsu.removeLocalCapability(zzahw(), str));
    }
}
