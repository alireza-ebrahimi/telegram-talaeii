package com.google.android.gms.internal.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;
import com.google.android.gms.vision.barcode.Barcode;
import java.nio.ByteBuffer;

public final class zze extends zzj<zzf> {
    private final zzc zzbb;

    public zze(Context context, zzc zzc) {
        super(context, "BarcodeNativeHandle");
        this.zzbb = zzc;
        zzh();
    }

    protected final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) {
        zzh zzh;
        IBinder instantiate = dynamiteModule.instantiate("com.google.android.gms.vision.barcode.ChimeraNativeBarcodeDetectorCreator");
        if (instantiate == null) {
            zzh = null;
        } else {
            IInterface queryLocalInterface = instantiate.queryLocalInterface("com.google.android.gms.vision.barcode.internal.client.INativeBarcodeDetectorCreator");
            zzh = queryLocalInterface instanceof zzh ? (zzh) queryLocalInterface : new zzi(instantiate);
        }
        return zzh == null ? null : zzh.zza(ObjectWrapper.wrap(context), this.zzbb);
    }

    public final Barcode[] zza(Bitmap bitmap, zzk zzk) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzf) zzh()).zzb(ObjectWrapper.wrap(bitmap), zzk);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    public final Barcode[] zza(ByteBuffer byteBuffer, zzk zzk) {
        if (!isOperational()) {
            return new Barcode[0];
        }
        try {
            return ((zzf) zzh()).zza(ObjectWrapper.wrap(byteBuffer), zzk);
        } catch (Throwable e) {
            Log.e("BarcodeNativeHandle", "Error calling native barcode detector", e);
            return new Barcode[0];
        }
    }

    protected final void zze() {
        if (isOperational()) {
            ((zzf) zzh()).zzf();
        }
    }
}
