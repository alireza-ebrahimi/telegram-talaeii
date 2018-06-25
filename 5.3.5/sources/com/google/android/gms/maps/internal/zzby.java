package com.google.android.gms.maps.internal;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;

@Hide
public final class zzby {
    private zzby() {
    }

    @Hide
    public static void zza(Bundle bundle, String str, Parcelable parcelable) {
        bundle.setClassLoader(zzby.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            bundle2 = new Bundle();
        }
        bundle2.setClassLoader(zzby.class.getClassLoader());
        bundle2.putParcelable(str, parcelable);
        bundle.putBundle("map_state", bundle2);
    }

    @Hide
    public static void zzd(@Nullable Bundle bundle, @Nullable Bundle bundle2) {
        if (bundle != null && bundle2 != null) {
            Parcelable zzg = zzg(bundle, "MapOptions");
            if (zzg != null) {
                zza(bundle2, "MapOptions", zzg);
            }
            zzg = zzg(bundle, "StreetViewPanoramaOptions");
            if (zzg != null) {
                zza(bundle2, "StreetViewPanoramaOptions", zzg);
            }
            zzg = zzg(bundle, "camera");
            if (zzg != null) {
                zza(bundle2, "camera", zzg);
            }
            if (bundle.containsKey("position")) {
                bundle2.putString("position", bundle.getString("position"));
            }
            if (bundle.containsKey("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT")) {
                bundle2.putBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", bundle.getBoolean("com.google.android.wearable.compat.extra.LOWBIT_AMBIENT", false));
            }
        }
    }

    @Hide
    private static <T extends Parcelable> T zzg(@Nullable Bundle bundle, String str) {
        if (bundle == null) {
            return null;
        }
        bundle.setClassLoader(zzby.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle("map_state");
        if (bundle2 == null) {
            return null;
        }
        bundle2.setClassLoader(zzby.class.getClassLoader());
        return bundle2.getParcelable(str);
    }
}
