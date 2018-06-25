package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import com.google.android.gms.common.util.CrashUtils.ErrorDialogData;
import java.io.File;
import java.io.FileNotFoundException;

final class zzhj implements Runnable {
    private final /* synthetic */ Uri zzco;
    private final /* synthetic */ long zzcq;
    private final /* synthetic */ long zzcr;
    private final /* synthetic */ String zzcs;
    private final /* synthetic */ ResultHolder zzfh;
    private final /* synthetic */ zzhg zzfi;

    zzhj(zzhg zzhg, Uri uri, ResultHolder resultHolder, String str, long j, long j2) {
        this.zzfi = zzhg;
        this.zzco = uri;
        this.zzfh = resultHolder;
        this.zzcs = str;
        this.zzcq = j;
        this.zzcr = j2;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing sendFileToChannelTask");
        }
        if ("file".equals(this.zzco.getScheme())) {
            File file = new File(this.zzco.getPath());
            try {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(file, ErrorDialogData.BINDER_CRASH);
                try {
                    ((zzep) this.zzfi.getService()).zza(new zzhc(this.zzfh), this.zzcs, open, this.zzcq, this.zzcr);
                    try {
                        open.close();
                        return;
                    } catch (Throwable e) {
                        Log.w("WearableClient", "Failed to close sourceFd", e);
                        return;
                    }
                } catch (Throwable e2) {
                    Log.w("WearableClient", "Channel.sendFile failed.", e2);
                    this.zzfh.setFailedResult(new Status(8));
                    try {
                        open.close();
                        return;
                    } catch (Throwable e22) {
                        Log.w("WearableClient", "Failed to close sourceFd", e22);
                        return;
                    }
                } catch (Throwable e222) {
                    try {
                        open.close();
                    } catch (Throwable e3) {
                        Log.w("WearableClient", "Failed to close sourceFd", e3);
                    }
                    throw e222;
                }
            } catch (FileNotFoundException e4) {
                String valueOf = String.valueOf(file);
                Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 46).append("File couldn't be opened for Channel.sendFile: ").append(valueOf).toString());
                this.zzfh.setFailedResult(new Status(13));
                return;
            }
        }
        Log.w("WearableClient", "Channel.sendFile used with non-file URI");
        this.zzfh.setFailedResult(new Status(10, "Channel.sendFile used with non-file URI"));
    }
}
