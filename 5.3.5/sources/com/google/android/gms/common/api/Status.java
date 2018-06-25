package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.IntentSender.SendIntentException;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

public final class Status extends zzbgl implements Result, ReflectedParcelable {
    public static final Creator<Status> CREATOR = new zzg();
    @Hide
    public static final Status zzftq = new Status(0);
    @Hide
    public static final Status zzftr = new Status(14);
    @Hide
    public static final Status zzfts = new Status(8);
    @Hide
    public static final Status zzftt = new Status(15);
    @Hide
    public static final Status zzftu = new Status(16);
    @Hide
    private static Status zzftv = new Status(17);
    @Hide
    private static Status zzftw = new Status(18);
    private final int zzcc;
    private int zzehz;
    @Nullable
    private final PendingIntent zzekd;
    @Nullable
    private final String zzfqu;

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this.zzehz = i;
        this.zzcc = i2;
        this.zzfqu = str;
        this.zzekd = pendingIntent;
    }

    public Status(int i, @Nullable String str) {
        this(1, i, str, null);
    }

    public Status(int i, @Nullable String str, @Nullable PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzehz == status.zzehz && this.zzcc == status.zzcc && zzbg.equal(this.zzfqu, status.zzfqu) && zzbg.equal(this.zzekd, status.zzekd);
    }

    public final PendingIntent getResolution() {
        return this.zzekd;
    }

    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.zzcc;
    }

    @Nullable
    public final String getStatusMessage() {
        return this.zzfqu;
    }

    public final boolean hasResolution() {
        return this.zzekd != null;
    }

    public final int hashCode() {
        return Arrays.hashCode(new Object[]{Integer.valueOf(this.zzehz), Integer.valueOf(this.zzcc), this.zzfqu, this.zzekd});
    }

    public final boolean isCanceled() {
        return this.zzcc == 16;
    }

    public final boolean isInterrupted() {
        return this.zzcc == 14;
    }

    public final boolean isSuccess() {
        return this.zzcc <= 0;
    }

    public final void startResolutionForResult(Activity activity, int i) throws SendIntentException {
        if (hasResolution()) {
            activity.startIntentSenderForResult(this.zzekd.getIntentSender(), i, null, 0, 0, 0);
        }
    }

    public final String toString() {
        return zzbg.zzx(this).zzg("statusCode", zzaif()).zzg("resolution", this.zzekd).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, getStatusCode());
        zzbgo.zza(parcel, 2, getStatusMessage(), false);
        zzbgo.zza(parcel, 3, this.zzekd, i, false);
        zzbgo.zzc(parcel, 1000, this.zzehz);
        zzbgo.zzai(parcel, zze);
    }

    @Hide
    public final String zzaif() {
        return this.zzfqu != null ? this.zzfqu : CommonStatusCodes.getStatusCodeString(this.zzcc);
    }
}
