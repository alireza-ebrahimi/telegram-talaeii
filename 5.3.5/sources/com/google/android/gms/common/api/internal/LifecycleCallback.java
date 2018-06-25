package com.google.android.gms.common.api.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.MainThread;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class LifecycleCallback {
    protected final zzcf zzgam;

    protected LifecycleCallback(zzcf zzcf) {
        this.zzgam = zzcf;
    }

    @Keep
    private static zzcf getChimeraLifecycleFragmentImpl(zzce zzce) {
        throw new IllegalStateException("Method not available in SDK.");
    }

    protected static zzcf zzb(zzce zzce) {
        if (zzce.zzaks()) {
            return zzdc.zza(zzce.zzakv());
        }
        if (zzce.zzakt()) {
            return zzcg.zzp(zzce.zzaku());
        }
        throw new IllegalArgumentException("Can't get fragment for unexpected activity.");
    }

    public static zzcf zzo(Activity activity) {
        return zzb(new zzce(activity));
    }

    @MainThread
    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
    }

    public final Activity getActivity() {
        return this.zzgam.zzakw();
    }

    @MainThread
    public void onActivityResult(int i, int i2, Intent intent) {
    }

    @MainThread
    public void onCreate(Bundle bundle) {
    }

    @MainThread
    public void onDestroy() {
    }

    @MainThread
    public void onResume() {
    }

    @MainThread
    public void onSaveInstanceState(Bundle bundle) {
    }

    @MainThread
    public void onStart() {
    }

    @MainThread
    public void onStop() {
    }
}
