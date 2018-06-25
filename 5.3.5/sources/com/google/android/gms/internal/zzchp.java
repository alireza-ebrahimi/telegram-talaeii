package com.google.android.gms.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.location.Geofence;
import java.util.Locale;

@Hide
public final class zzchp extends zzbgl implements Geofence {
    public static final Creator<zzchp> CREATOR = new zzchq();
    private final String zzcwj;
    private final int zziro;
    private final short zzirq;
    private final double zzirr;
    private final double zzirs;
    private final float zzirt;
    private final int zziru;
    private final int zzirv;
    private final long zziuu;

    public zzchp(String str, int i, short s, double d, double d2, float f, long j, int i2, int i3) {
        if (str == null || str.length() > 100) {
            String str2 = "requestId is null or too long: ";
            String valueOf = String.valueOf(str);
            throw new IllegalArgumentException(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        } else if (f <= 0.0f) {
            throw new IllegalArgumentException("invalid radius: " + f);
        } else if (d > 90.0d || d < -90.0d) {
            throw new IllegalArgumentException("invalid latitude: " + d);
        } else if (d2 > 180.0d || d2 < -180.0d) {
            throw new IllegalArgumentException("invalid longitude: " + d2);
        } else {
            int i4 = i & 7;
            if (i4 == 0) {
                throw new IllegalArgumentException("No supported transition specified: " + i);
            }
            this.zzirq = s;
            this.zzcwj = str;
            this.zzirr = d;
            this.zzirs = d2;
            this.zzirt = f;
            this.zziuu = j;
            this.zziro = i4;
            this.zziru = i2;
            this.zzirv = i3;
        }
    }

    public static zzchp zzq(byte[] bArr) {
        Parcel obtain = Parcel.obtain();
        obtain.unmarshall(bArr, 0, bArr.length);
        obtain.setDataPosition(0);
        zzchp zzchp = (zzchp) CREATOR.createFromParcel(obtain);
        obtain.recycle();
        return zzchp;
    }

    @Hide
    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof zzchp)) {
            return false;
        }
        zzchp zzchp = (zzchp) obj;
        return this.zzirt != zzchp.zzirt ? false : this.zzirr != zzchp.zzirr ? false : this.zzirs != zzchp.zzirs ? false : this.zzirq == zzchp.zzirq;
    }

    public final String getRequestId() {
        return this.zzcwj;
    }

    @Hide
    public final int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.zzirr);
        int i = ((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) + 31;
        long doubleToLongBits2 = Double.doubleToLongBits(this.zzirs);
        return (((((((i * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + Float.floatToIntBits(this.zzirt)) * 31) + this.zzirq) * 31) + this.zziro;
    }

    @Hide
    public final String toString() {
        String str;
        Locale locale = Locale.US;
        String str2 = "Geofence[%s id:%s transitions:%d %.6f, %.6f %.0fm, resp=%ds, dwell=%dms, @%d]";
        Object[] objArr = new Object[9];
        switch (this.zzirq) {
            case (short) 1:
                str = "CIRCLE";
                break;
            default:
                str = null;
                break;
        }
        objArr[0] = str;
        objArr[1] = this.zzcwj.replaceAll("\\p{C}", "?");
        objArr[2] = Integer.valueOf(this.zziro);
        objArr[3] = Double.valueOf(this.zzirr);
        objArr[4] = Double.valueOf(this.zzirs);
        objArr[5] = Float.valueOf(this.zzirt);
        objArr[6] = Integer.valueOf(this.zziru / 1000);
        objArr[7] = Integer.valueOf(this.zzirv);
        objArr[8] = Long.valueOf(this.zziuu);
        return String.format(locale, str2, objArr);
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 1, getRequestId(), false);
        zzbgo.zza(parcel, 2, this.zziuu);
        zzbgo.zza(parcel, 3, this.zzirq);
        zzbgo.zza(parcel, 4, this.zzirr);
        zzbgo.zza(parcel, 5, this.zzirs);
        zzbgo.zza(parcel, 6, this.zzirt);
        zzbgo.zzc(parcel, 7, this.zziro);
        zzbgo.zzc(parcel, 8, this.zziru);
        zzbgo.zzc(parcel, 9, this.zzirv);
        zzbgo.zzai(parcel, zze);
    }
}
