package com.google.android.gms.wearable.internal;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Looper;
import com.google.android.gms.common.api.GoogleApi.Settings;
import com.google.android.gms.common.api.internal.ListenerHolder;
import com.google.android.gms.common.api.internal.ListenerHolders;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.PendingResultUtil;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityClient;
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener;
import com.google.android.gms.wearable.CapabilityInfo;
import java.util.Map;

public final class zzaa extends CapabilityClient {
    private final CapabilityApi zzbw = new zzo();

    public zzaa(Activity activity, Settings settings) {
        super(activity, settings);
    }

    public zzaa(Context context, Settings settings) {
        super(context, settings);
    }

    private final Task<Void> zza(ListenerHolder<OnCapabilityChangedListener> listenerHolder, OnCapabilityChangedListener onCapabilityChangedListener, IntentFilter[] intentFilterArr) {
        return doRegisterEventListener(new zzaf(onCapabilityChangedListener, intentFilterArr, listenerHolder, null), new zzag(onCapabilityChangedListener, listenerHolder.getListenerKey(), null));
    }

    public final Task<Void> addListener(OnCapabilityChangedListener onCapabilityChangedListener, Uri uri, int i) {
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        Preconditions.checkArgument(z, "invalid filter type");
        IntentFilter zza = zzgj.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, i);
        return zza(ListenerHolders.createListenerHolder(onCapabilityChangedListener, getLooper(), "CapabilityListener"), onCapabilityChangedListener, new IntentFilter[]{zza});
    }

    public final Task<Void> addListener(OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        String str2;
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(str, "capability must not be null");
        IntentFilter zzc = zzgj.zzc("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        if (str.startsWith("/")) {
            str2 = str;
        } else {
            String str3 = "/";
            str2 = String.valueOf(str);
            str2 = str2.length() != 0 ? str3.concat(str2) : new String(str3);
        }
        zzc.addDataPath(str2, 0);
        IntentFilter[] intentFilterArr = new IntentFilter[]{zzc};
        Looper looper = getLooper();
        String str4 = "CapabilityListener:";
        String valueOf = String.valueOf(str2);
        return zza(ListenerHolders.createListenerHolder(onCapabilityChangedListener, looper, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4)), new zzae(onCapabilityChangedListener, str2), intentFilterArr);
    }

    public final Task<Void> addLocalCapability(String str) {
        Asserts.checkNotNull(str, "capability must not be null");
        return PendingResultUtil.toVoidTask(this.zzbw.addLocalCapability(asGoogleApiClient(), str));
    }

    public final Task<Map<String, CapabilityInfo>> getAllCapabilities(int i) {
        return PendingResultUtil.toTask(this.zzbw.getAllCapabilities(asGoogleApiClient(), i), zzac.zzbx);
    }

    public final Task<CapabilityInfo> getCapability(String str, int i) {
        Asserts.checkNotNull(str, "capability must not be null");
        return PendingResultUtil.toTask(this.zzbw.getCapability(asGoogleApiClient(), str, i), zzab.zzbx);
    }

    public final Task<Boolean> removeListener(OnCapabilityChangedListener onCapabilityChangedListener) {
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        return doUnregisterEventListener(ListenerHolders.createListenerHolder(onCapabilityChangedListener, getLooper(), "CapabilityListener").getListenerKey());
    }

    public final Task<Boolean> removeListener(OnCapabilityChangedListener onCapabilityChangedListener, String str) {
        String str2;
        Object concat;
        Asserts.checkNotNull(onCapabilityChangedListener, "listener must not be null");
        Asserts.checkNotNull(str, "capability must not be null");
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
        return doUnregisterEventListener(ListenerHolders.createListenerHolder(onCapabilityChangedListener, looper, str2.length() != 0 ? str4.concat(str2) : new String(str4)).getListenerKey());
    }

    public final Task<Void> removeLocalCapability(String str) {
        Asserts.checkNotNull(str, "capability must not be null");
        return PendingResultUtil.toVoidTask(this.zzbw.removeLocalCapability(asGoogleApiClient(), str));
    }
}
