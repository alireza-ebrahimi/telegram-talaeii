package com.google.android.gms.common.images;

import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbge;
import com.google.android.gms.internal.zzbgj;
import java.lang.ref.WeakReference;

public final class zzc extends zza {
    private WeakReference<ImageView> zzgdw;

    public zzc(ImageView imageView, int i) {
        super(null, i);
        com.google.android.gms.common.internal.zzc.zzv(imageView);
        this.zzgdw = new WeakReference(imageView);
    }

    public zzc(ImageView imageView, Uri uri) {
        super(uri, 0);
        com.google.android.gms.common.internal.zzc.zzv(imageView);
        this.zzgdw = new WeakReference(imageView);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzc)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        ImageView imageView = (ImageView) this.zzgdw.get();
        ImageView imageView2 = (ImageView) ((zzc) obj).zzgdw.get();
        return (imageView2 == null || imageView == null || !zzbg.equal(imageView2, imageView)) ? false : true;
    }

    public final int hashCode() {
        return 0;
    }

    protected final void zza(Drawable drawable, boolean z, boolean z2, boolean z3) {
        Uri uri = null;
        ImageView imageView = (ImageView) this.zzgdw.get();
        if (imageView != null) {
            Drawable drawable2;
            Object obj = (z2 || z3) ? null : 1;
            if (obj != null && (imageView instanceof zzbgj)) {
                int zzalp = zzbgj.zzalp();
                if (this.zzgdr != 0 && zzalp == this.zzgdr) {
                    return;
                }
            }
            boolean zzc = zzc(z, z2);
            if (zzc) {
                drawable2 = imageView.getDrawable();
                if (drawable2 == null) {
                    drawable2 = null;
                } else if (drawable2 instanceof zzbge) {
                    drawable2 = ((zzbge) drawable2).zzaln();
                }
                drawable2 = new zzbge(drawable2, drawable);
            } else {
                drawable2 = drawable;
            }
            imageView.setImageDrawable(drawable2);
            if (imageView instanceof zzbgj) {
                if (z3) {
                    uri = this.zzgdp.uri;
                }
                zzbgj.zzn(uri);
                zzbgj.zzcc(obj != null ? this.zzgdr : 0);
            }
            if (zzc) {
                ((zzbge) drawable2).startTransition(250);
            }
        }
    }
}
