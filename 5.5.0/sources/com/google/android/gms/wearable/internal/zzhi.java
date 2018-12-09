package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.BaseImplementation.ResultHolder;
import java.io.File;
import java.io.FileNotFoundException;
import org.telegram.tgnet.ConnectionsManager;

final class zzhi implements Runnable {
    private final /* synthetic */ Uri zzco;
    private final /* synthetic */ boolean zzcp;
    private final /* synthetic */ String zzcs;
    private final /* synthetic */ ResultHolder zzfh;
    private final /* synthetic */ zzhg zzfi;

    zzhi(zzhg zzhg, Uri uri, ResultHolder resultHolder, boolean z, String str) {
        this.zzfi = zzhg;
        this.zzco = uri;
        this.zzfh = resultHolder;
        this.zzcp = z;
        this.zzcs = str;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing receiveFileFromChannelTask");
        }
        if ("file".equals(this.zzco.getScheme())) {
            Object file = new File(this.zzco.getPath());
            try {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(file, (this.zzcp ? ConnectionsManager.FileTypeVideo : 0) | 671088640);
                try {
                    ((zzep) this.zzfi.getService()).zza(new zzhf(this.zzfh), this.zzcs, open);
                    try {
                        open.close();
                        return;
                    } catch (Throwable e) {
                        Log.w("WearableClient", "Failed to close targetFd", e);
                        return;
                    }
                } catch (Throwable e2) {
                    Log.w("WearableClient", "Channel.receiveFile failed.", e2);
                    this.zzfh.setFailedResult(new Status(8));
                    try {
                        open.close();
                        return;
                    } catch (Throwable e22) {
                        Log.w("WearableClient", "Failed to close targetFd", e22);
                        return;
                    }
                } catch (Throwable e222) {
                    try {
                        open.close();
                    } catch (Throwable e3) {
                        Log.w("WearableClient", "Failed to close targetFd", e3);
                    }
                    throw e222;
                }
            } catch (FileNotFoundException e4) {
                String valueOf = String.valueOf(file);
                Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 49).append("File couldn't be opened for Channel.receiveFile: ").append(valueOf).toString());
                this.zzfh.setFailedResult(new Status(13));
                return;
            }
        }
        Log.w("WearableClient", "Channel.receiveFile used with non-file URI");
        this.zzfh.setFailedResult(new Status(10, "Channel.receiveFile used with non-file URI"));
    }
}
