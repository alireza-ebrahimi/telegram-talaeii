package com.google.android.gms.internal.vision;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;
import com.google.android.gms.dynamic.ObjectWrapper;
import com.google.android.gms.dynamite.DynamiteModule;

public final class zzz extends zzj<zzp> {
    private final zzaa zzdb;

    public zzz(Context context, zzaa zzaa) {
        super(context, "TextNativeHandle");
        this.zzdb = zzaa;
        zzh();
    }

    protected final /* synthetic */ Object zza(DynamiteModule dynamiteModule, Context context) {
        zzr zzr;
        IBinder instantiate = dynamiteModule.instantiate("com.google.android.gms.vision.text.ChimeraNativeTextRecognizerCreator");
        if (instantiate == null) {
            zzr = null;
        } else {
            IInterface queryLocalInterface = instantiate.queryLocalInterface("com.google.android.gms.vision.text.internal.client.INativeTextRecognizerCreator");
            zzr = queryLocalInterface instanceof zzr ? (zzr) queryLocalInterface : new zzs(instantiate);
        }
        return zzr == null ? null : zzr.zza(ObjectWrapper.wrap(context), this.zzdb);
    }

    public final zzt[] zza(Bitmap bitmap, zzk zzk, zzv zzv) {
        if (!isOperational()) {
            return new zzt[0];
        }
        try {
            return ((zzp) zzh()).zza(ObjectWrapper.wrap(bitmap), zzk, zzv);
        } catch (Throwable e) {
            Log.e("TextNativeHandle", "Error calling native text recognizer", e);
            return new zzt[0];
        }
    }

    protected final void zze() {
        ((zzp) zzh()).zzi();
    }
}
