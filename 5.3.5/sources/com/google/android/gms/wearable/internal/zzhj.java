package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import java.io.File;
import java.io.FileNotFoundException;

final class zzhj implements Runnable {
    private /* synthetic */ String zzehu;
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ long zzltk;
    private /* synthetic */ long zzltl;
    private /* synthetic */ zzn zzlvv;
    private /* synthetic */ zzhg zzlvw;

    zzhj(zzhg zzhg, Uri uri, zzn zzn, String str, long j, long j2) {
        this.zzlvw = zzhg;
        this.zzkff = uri;
        this.zzlvv = zzn;
        this.zzehu = str;
        this.zzltk = j;
        this.zzltl = j2;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing sendFileToChannelTask");
        }
        if ("file".equals(this.zzkff.getScheme())) {
            File file = new File(this.zzkff.getPath());
            try {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(file, 268435456);
                try {
                    ((zzep) this.zzlvw.zzalw()).zza(new zzhc(this.zzlvv), this.zzehu, open, this.zzltk, this.zzltl);
                    try {
                        open.close();
                        return;
                    } catch (Throwable e) {
                        Log.w("WearableClient", "Failed to close sourceFd", e);
                        return;
                    }
                } catch (Throwable e2) {
                    Log.w("WearableClient", "Channel.sendFile failed.", e2);
                    this.zzlvv.zzu(new Status(8));
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
                this.zzlvv.zzu(new Status(13));
                return;
            }
        }
        Log.w("WearableClient", "Channel.sendFile used with non-file URI");
        this.zzlvv.zzu(new Status(10, "Channel.sendFile used with non-file URI"));
    }
}
