package com.google.android.gms.wearable;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Hide;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.zzbg;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.Arrays;

@Hide
public class ConnectionConfiguration extends zzbgl implements ReflectedParcelable {
    public static final Creator<ConnectionConfiguration> CREATOR = new zzg();
    private final String mName;
    private volatile boolean zzedw;
    private final int zzenu;
    private final int zzgrc;
    private final String zziuy;
    private final boolean zzlqr;
    private volatile String zzlqs;
    private boolean zzlqt;
    private String zzlqu;

    @Hide
    ConnectionConfiguration(String str, String str2, int i, int i2, boolean z, boolean z2, String str3, boolean z3, String str4) {
        this.mName = str;
        this.zziuy = str2;
        this.zzenu = i;
        this.zzgrc = i2;
        this.zzlqr = z;
        this.zzedw = z2;
        this.zzlqs = str3;
        this.zzlqt = z3;
        this.zzlqu = str4;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof ConnectionConfiguration)) {
            return false;
        }
        ConnectionConfiguration connectionConfiguration = (ConnectionConfiguration) obj;
        return zzbg.equal(this.mName, connectionConfiguration.mName) && zzbg.equal(this.zziuy, connectionConfiguration.zziuy) && zzbg.equal(Integer.valueOf(this.zzenu), Integer.valueOf(connectionConfiguration.zzenu)) && zzbg.equal(Integer.valueOf(this.zzgrc), Integer.valueOf(connectionConfiguration.zzgrc)) && zzbg.equal(Boolean.valueOf(this.zzlqr), Boolean.valueOf(connectionConfiguration.zzlqr)) && zzbg.equal(Boolean.valueOf(this.zzlqt), Boolean.valueOf(connectionConfiguration.zzlqt));
    }

    public int hashCode() {
        return Arrays.hashCode(new Object[]{this.mName, this.zziuy, Integer.valueOf(this.zzenu), Integer.valueOf(this.zzgrc), Boolean.valueOf(this.zzlqr), Boolean.valueOf(this.zzlqt)});
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ConnectionConfiguration[ ");
        String str = "mName=";
        String valueOf = String.valueOf(this.mName);
        stringBuilder.append(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        str = ", mAddress=";
        valueOf = String.valueOf(this.zziuy);
        stringBuilder.append(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        stringBuilder.append(", mType=" + this.zzenu);
        stringBuilder.append(", mRole=" + this.zzgrc);
        stringBuilder.append(", mEnabled=" + this.zzlqr);
        stringBuilder.append(", mIsConnected=" + this.zzedw);
        str = ", mPeerNodeId=";
        valueOf = String.valueOf(this.zzlqs);
        stringBuilder.append(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        stringBuilder.append(", mBtlePriority=" + this.zzlqt);
        str = ", mNodeId=";
        valueOf = String.valueOf(this.zzlqu);
        stringBuilder.append(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.mName, false);
        zzbgo.zza(parcel, 3, this.zziuy, false);
        zzbgo.zzc(parcel, 4, this.zzenu);
        zzbgo.zzc(parcel, 5, this.zzgrc);
        zzbgo.zza(parcel, 6, this.zzlqr);
        zzbgo.zza(parcel, 7, this.zzedw);
        zzbgo.zza(parcel, 8, this.zzlqs, false);
        zzbgo.zza(parcel, 9, this.zzlqt);
        zzbgo.zza(parcel, 10, this.zzlqu, false);
        zzbgo.zzai(parcel, zze);
    }
}
