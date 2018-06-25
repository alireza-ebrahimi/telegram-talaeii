package com.google.android.gms.wearable.internal;

import android.os.ParcelFileDescriptor;
import android.os.ParcelFileDescriptor.AutoCloseOutputStream;
import android.util.Log;
import java.io.IOException;
import java.util.concurrent.Callable;

final class zzhh implements Callable<Boolean> {
    private final /* synthetic */ byte[] zzee;
    private final /* synthetic */ ParcelFileDescriptor zzfg;

    zzhh(zzhg zzhg, ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        this.zzfg = parcelFileDescriptor;
        this.zzee = bArr;
    }

    private final Boolean zzd() {
        if (Log.isLoggable("WearableClient", 3)) {
            String valueOf = String.valueOf(this.zzfg);
            Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf).length() + 36).append("processAssets: writing data to FD : ").append(valueOf).toString());
        }
        AutoCloseOutputStream autoCloseOutputStream = new AutoCloseOutputStream(this.zzfg);
        String valueOf2;
        String valueOf3;
        try {
            autoCloseOutputStream.write(this.zzee);
            autoCloseOutputStream.flush();
            if (Log.isLoggable("WearableClient", 3)) {
                valueOf2 = String.valueOf(this.zzfg);
                Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 27).append("processAssets: wrote data: ").append(valueOf2).toString());
            }
            Boolean valueOf4 = Boolean.valueOf(true);
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    valueOf3 = String.valueOf(this.zzfg);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf3).length() + 24).append("processAssets: closing: ").append(valueOf3).toString());
                }
                autoCloseOutputStream.close();
                return valueOf4;
            } catch (IOException e) {
                return valueOf4;
            }
        } catch (IOException e2) {
            valueOf2 = String.valueOf(this.zzfg);
            Log.w("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 36).append("processAssets: writing data failed: ").append(valueOf2).toString());
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    valueOf2 = String.valueOf(this.zzfg);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf2).length() + 24).append("processAssets: closing: ").append(valueOf2).toString());
                }
                autoCloseOutputStream.close();
            } catch (IOException e3) {
            }
            return Boolean.valueOf(false);
        } catch (Throwable th) {
            try {
                if (Log.isLoggable("WearableClient", 3)) {
                    valueOf3 = String.valueOf(this.zzfg);
                    Log.d("WearableClient", new StringBuilder(String.valueOf(valueOf3).length() + 24).append("processAssets: closing: ").append(valueOf3).toString());
                }
                autoCloseOutputStream.close();
            } catch (IOException e4) {
            }
            throw th;
        }
    }

    public final /* synthetic */ Object call() {
        return zzd();
    }
}
