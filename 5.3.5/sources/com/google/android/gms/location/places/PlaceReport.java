package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.common.internal.zzbi;
import com.google.android.gms.common.internal.zzbq;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.firebase.analytics.FirebaseAnalytics.Param;
import java.util.Arrays;

public class PlaceReport extends zzbgl implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR = new zzl();
    private final String mTag;
    private final String zzdwr;
    private int zzehz;
    private final String zzivz;

    @Hide
    PlaceReport(int i, String str, String str2, String str3) {
        this.zzehz = i;
        this.zzivz = str;
        this.mTag = str2;
        this.zzdwr = str3;
    }

    public static PlaceReport create(String str, String str2) {
        boolean z = false;
        String str3 = "unknown";
        zzbq.checkNotNull(str);
        zzbq.zzgv(str2);
        zzbq.zzgv(str3);
        boolean z2 = true;
        switch (str3.hashCode()) {
            case -1436706272:
                if (str3.equals("inferredGeofencing")) {
                    z2 = true;
                    break;
                }
                break;
            case -1194968642:
                if (str3.equals("userReported")) {
                    z2 = true;
                    break;
                }
                break;
            case -284840886:
                if (str3.equals("unknown")) {
                    z2 = false;
                    break;
                }
                break;
            case -262743844:
                if (str3.equals("inferredReverseGeocoding")) {
                    z2 = true;
                    break;
                }
                break;
            case 1164924125:
                if (str3.equals("inferredSnappedToRoad")) {
                    z2 = true;
                    break;
                }
                break;
            case 1287171955:
                if (str3.equals("inferredRadioSignals")) {
                    z2 = true;
                    break;
                }
                break;
        }
        switch (z2) {
            case false:
            case true:
            case true:
            case true:
            case true:
            case true:
                z = true;
                break;
        }
        zzbq.checkArgument(z, "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    @Hide
    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        return zzbg.equal(this.zzivz, placeReport.zzivz) && zzbg.equal(this.mTag, placeReport.mTag) && zzbg.equal(this.zzdwr, placeReport.zzdwr);
    }

    public String getPlaceId() {
        return this.zzivz;
    }

    public String getTag() {
        return this.mTag;
    }

    @Hide
    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.zzivz, this.mTag, this.zzdwr});
    }

    @Hide
    public String toString() {
        zzbi zzx = zzbg.zzx(this);
        zzx.zzg("placeId", this.zzivz);
        zzx.zzg("tag", this.mTag);
        if (!"unknown".equals(this.zzdwr)) {
            zzx.zzg(Param.SOURCE, this.zzdwr);
        }
        return zzx.toString();
    }

    @Hide
    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 1, this.zzehz);
        zzbgo.zza(parcel, 2, getPlaceId(), false);
        zzbgo.zza(parcel, 3, getTag(), false);
        zzbgo.zza(parcel, 4, this.zzdwr, false);
        zzbgo.zzai(parcel, zze);
    }
}
