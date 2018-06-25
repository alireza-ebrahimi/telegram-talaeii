package com.google.android.gms.internal;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.dynamic.zzn;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.dynamite.DynamiteModule.zzc;

@Hide
public final class zzdlr extends zzdlc<zzdlh> {
    private final zzdls zzlie;

    public zzdlr(Context context, zzdls zzdls) {
        super(context, "TextNativeHandle");
        this.zzlie = zzdls;
        zzblo();
    }

    protected final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, zzc {
        zzdlj zzdlj;
        IBinder zzhk = dynamiteModule.zzhk("com.google.android.gms.vision.text.ChimeraNativeTextRecognizerCreator");
        if (zzhk == null) {
            zzdlj = null;
        } else {
            IInterface queryLocalInterface = zzhk.queryLocalInterface("com.google.android.gms.vision.text.internal.client.INativeTextRecognizerCreator");
            zzdlj = queryLocalInterface instanceof zzdlj ? (zzdlj) queryLocalInterface : new zzdlk(zzhk);
        }
        return zzdlj.zza(zzn.zzz(context), this.zzlie);
    }

    public final zzdll[] zza(Bitmap bitmap, zzdld zzdld, zzdln zzdln) {
        if (!isOperational()) {
            return new zzdll[0];
        }
        try {
            return ((zzdlh) zzblo()).zza(zzn.zzz(bitmap), zzdld, zzdln);
        } catch (Throwable e) {
            Log.e("TextNativeHandle", "Error calling native text recognizer", e);
            return new zzdll[0];
        }
    }

    protected final void zzbll() throws RemoteException {
        ((zzdlh) zzblo()).zzblp();
    }
}
