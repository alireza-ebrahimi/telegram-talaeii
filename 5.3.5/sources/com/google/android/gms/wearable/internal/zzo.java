package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityApi.AddLocalCapabilityResult;
import com.google.android.gms.wearable.CapabilityApi.CapabilityListener;
import com.google.android.gms.wearable.CapabilityApi.GetAllCapabilitiesResult;
import com.google.android.gms.wearable.CapabilityApi.GetCapabilityResult;
import com.google.android.gms.wearable.CapabilityApi.RemoveLocalCapabilityResult;

public final class zzo implements CapabilityApi {
    private static PendingResult<Status> zza(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, IntentFilter[] intentFilterArr) {
        return zzb.zza(googleApiClient, new zzt(intentFilterArr), capabilityListener);
    }

    public final PendingResult<Status> addCapabilityListener(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, String str) {
        String str2;
        zzc.zzb(str, "capability must not be null");
        CapabilityListener zzv = new zzv(capabilityListener, str);
        IntentFilter zzoe = zzgj.zzoe("com.google.android.gms.wearable.CAPABILITY_CHANGED");
        if (str.startsWith("/")) {
            str2 = str;
        } else {
            String str3 = "/";
            str2 = String.valueOf(str);
            str2 = str2.length() != 0 ? str3.concat(str2) : new String(str3);
        }
        zzoe.addDataPath(str2, 0);
        return zza(googleApiClient, zzv, new IntentFilter[]{zzoe});
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, Uri uri, int i) {
        zzc.zzb(uri, "uri must not be null");
        boolean z = i == 0 || i == 1;
        zzbq.checkArgument(z, "invalid filter type");
        return zza(googleApiClient, capabilityListener, new IntentFilter[]{zzgj.zza("com.google.android.gms.wearable.CAPABILITY_CHANGED", uri, i)});
    }

    public final PendingResult<AddLocalCapabilityResult> addLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzr(this, googleApiClient, str));
    }

    public final PendingResult<GetAllCapabilitiesResult> getAllCapabilities(GoogleApiClient googleApiClient, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbq.checkArgument(z);
        return googleApiClient.zzd(new zzq(this, googleApiClient, i));
    }

    public final PendingResult<GetCapabilityResult> getCapability(GoogleApiClient googleApiClient, String str, int i) {
        boolean z = true;
        if (!(i == 0 || i == 1)) {
            z = false;
        }
        zzbq.checkArgument(z);
        return googleApiClient.zzd(new zzp(this, googleApiClient, str, i));
    }

    public final PendingResult<Status> removeCapabilityListener(GoogleApiClient googleApiClient, CapabilityListener capabilityListener, String str) {
        return googleApiClient.zzd(new zzz(googleApiClient, new zzv(capabilityListener, str), null));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, CapabilityListener capabilityListener) {
        return googleApiClient.zzd(new zzz(googleApiClient, capabilityListener, null));
    }

    public final PendingResult<RemoveLocalCapabilityResult> removeLocalCapability(GoogleApiClient googleApiClient, String str) {
        return googleApiClient.zzd(new zzs(this, googleApiClient, str));
    }
}
