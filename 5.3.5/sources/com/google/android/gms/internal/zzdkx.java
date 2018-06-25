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
import com.google.android.gms.vision.barcode.Barcode;
import java.nio.ByteBuffer;

@Hide
public final class zzdkx extends zzdlc<zzdky> {
    private final zzdkv zzlgp;

    public zzdkx(Context context, zzdkv zzdkv) {
        super(context, "BarcodeNativeHandle");
        this.zzlgp = zzdkv;
        zzblo();
    }

    protected final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) throws RemoteException, zzc {
        zzdla zzdla;
        IBinder zzhk = dynamiteModule.zzhk("com.google.android.gms.vision.barcode.ChimeraNativeBarcodeDetectorCreator");
        if (zzhk == null) {
            zzdla = null;
        } else {
            IInterface queryLocalInterface = zzhk.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
            zzdla = queryLocalInterface instanceof zzdla ? (zzdla) queryLocalInterface : new zzdlb(zzhk);
        }
        return zzdla == null ? null : zzdla.zza(zzn.zzz(context), this.zzlgp);
    }

    public final Barcode[] zza(Bitmap bitmap, zzdld zzdld) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzdky) zzblo()).zzb(zzn.zzz(bitmap), zzdld);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    public final Barcode[] zza(ByteBuffer byteBuffer, zzdld zzdld) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzdky) zzblo()).zza(zzn.zzz(byteBuffer), zzdld);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    protected final void zzbll() throws RemoteException {
        if (isOperational()) {
            ((zzdky) zzblo()).zzblm();
        }
    }
}
