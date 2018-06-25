package com.google.android.gms.common.images;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.zzc;
import com.google.android.gms.internal.zzbgk;

@Hide
public abstract class zza {
    final zzb zzgdp;
    private int zzgdq = 0;
    protected int zzgdr = 0;
    private boolean zzgds = false;
    private boolean zzgdt = true;
    private boolean zzgdu = false;
    private boolean zzgdv = true;

    public zza(Uri uri, int i) {
        this.zzgdp = new zzb(uri);
        this.zzgdr = i;
    }

    final void zza(Context context, Bitmap bitmap, boolean z) {
        zzc.zzv(bitmap);
        zza(new BitmapDrawable(context.getResources(), bitmap), z, false, true);
    }

    final void zza(Context context, zzbgk zzbgk) {
        if (this.zzgdv) {
            zza(null, false, true, false);
        }
    }

    final void zza(Context context, zzbgk zzbgk, boolean z) {
        Drawable drawable = null;
        if (this.zzgdr != 0) {
            drawable = context.getResources().getDrawable(this.zzgdr);
        }
        zza(drawable, z, false, false);
    }

    protected abstract void zza(Drawable drawable, boolean z, boolean z2, boolean z3);

    protected final boolean zzc(boolean z, boolean z2) {
        return (!this.zzgdt || z2 || z) ? false : true;
    }
}
