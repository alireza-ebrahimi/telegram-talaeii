package com.google.android.gms.wearable.internal;

import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.util.Log;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.internal.zzn;
import java.io.File;
import java.io.FileNotFoundException;
import org.telegram.tgnet.ConnectionsManager;

final class zzhi implements Runnable {
    private /* synthetic */ String zzehu;
    private /* synthetic */ Uri zzkff;
    private /* synthetic */ boolean zzltj;
    private /* synthetic */ zzn zzlvv;
    private /* synthetic */ zzhg zzlvw;

    zzhi(zzhg zzhg, Uri uri, zzn zzn, boolean z, String str) {
        this.zzlvw = zzhg;
        this.zzkff = uri;
        this.zzlvv = zzn;
        this.zzltj = z;
        this.zzehu = str;
    }

    public final void run() {
        if (Log.isLoggable("WearableClient", 2)) {
            Log.v("WearableClient", "Executing receiveFileFromChannelTask");
        }
        if ("file".equals(this.zzkff.getScheme())) {
            Object file = new File(this.zzkff.getPath());
            try {
                ParcelFileDescriptor open = ParcelFileDescriptor.open(file, (this.zzltj ? ConnectionsManager.FileTypeVideo : 0) | 671088640);
                try {
                    ((zzep) this.zzlvw.zzalw()).zza(new zzhf(this.zzlvv), this.zzehu, open);
                    try {
                        open.close();
                        return;
                    } catch (Throwable e) {
                        Log.w("WearableClient", "Failed to close targetFd", e);
                        return;
                    }
                } catch (Throwable e2) {
                    Log.w("WearableClient", "Channel.receiveFile failed.", e2);
                    this.zzlvv.zzu(new Status(8));
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
                this.zzlvv.zzu(new Status(13));
                return;
            }
        }
        Log.w("WearableClient", "Channel.receiveFile used with non-file URI");
        this.zzlvv.zzu(new Status(10, "Channel.receiveFile used with non-file URI"));
    }
}
