package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.google.android.gms.common.images.ImageManager.OnImageLoadedListener;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzc;
import java.lang.ref.WeakReference;
import java.util.Arrays;

public final class zzd extends zza {
    private WeakReference<OnImageLoadedListener> zzgdx;

    public zzd(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        super(uri, 0);
        zzc.zzv(onImageLoadedListener);
        this.zzgdx = new WeakReference(onImageLoadedListener);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzd)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        zzd zzd = (zzd) obj;
        OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzgdx.get();
        OnImageLoadedListener onImageLoadedListener2 = (OnImageLoadedListener) zzd.zzgdx.get();
        return onImageLoadedListener2 != null && onImageLoadedListener != null && zzbg.equal(onImageLoadedListener2, onImageLoadedListener) && zzbg.equal(zzd.zzgdp, this.zzgdp);
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzgdp});
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        if (!z2) {
            OnImageLoadedListener onImageLoadedListener = (OnImageLoadedListener) this.zzgdx.get();
            if (onImageLoadedListener != null) {
                onImageLoadedListener.onImageLoaded(this.zzgdp.uri, drawable, z3);
            }
        }
    }
}
