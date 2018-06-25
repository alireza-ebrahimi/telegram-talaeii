package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

public final class zzaw extends zzbgl {
    public static final Creator<zzaw> CREATOR = new zzax();
    private int type;
    private int zzltf;
    private int zzltg;
    private zzay zzlth;

    public zzaw(zzay zzay, int i, int i2, int i3) {
        this.zzlth = zzay;
        this.type = i;
        this.zzltf = i2;
        this.zzltg = i3;
    }

    public final String toString() {
        String str;
        String str2;
        String valueOf = String.valueOf(this.zzlth);
        int i = this.type;
        switch (i) {
            case 1:
                str = "CHANNEL_OPENED";
                break;
            case 2:
                str = "CHANNEL_CLOSED";
                break;
            case 3:
                str = "INPUT_CLOSED";
                break;
            case 4:
                str = "OUTPUT_CLOSED";
                break;
            default:
                str = Integer.toString(i);
                break;
        }
        int i2 = this.zzltf;
        switch (i2) {
            case 0:
                str2 = "CLOSE_REASON_NORMAL";
                break;
            case 1:
                str2 = "CLOSE_REASON_DISCONNECTED";
                break;
            case 2:
                str2 = "CLOSE_REASON_REMOTE_CLOSE";
                break;
            case 3:
                str2 = "CLOSE_REASON_LOCAL_CLOSE";
                break;
            default:
                str2 = Integer.toString(i2);
                break;
        }
        return new StringBuilder(((String.valueOf(valueOf).length() + 81) + String.valueOf(str).length()) + String.valueOf(str2).length()).append("ChannelEventParcelable[, channel=").append(valueOf).append(", type=").append(str).append(", closeReason=").append(str2).append(", appErrorCode=").append(this.zzltg).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlth, i, false);
        zzbgo.zzc(parcel, 3, this.type);
        zzbgo.zzc(parcel, 4, this.zzltf);
        zzbgo.zzc(parcel, 5, this.zzltg);
        zzbgo.zzai(parcel, zze);
    }

    public final void zza(ChannelListener channelListener) {
        switch (this.type) {
            case 1:
                channelListener.onChannelOpened(this.zzlth);
                return;
            case 2:
                channelListener.onChannelClosed(this.zzlth, this.zzltf, this.zzltg);
                return;
            case 3:
                channelListener.onInputClosed(this.zzlth, this.zzltf, this.zzltg);
                return;
            case 4:
                channelListener.onOutputClosed(this.zzlth, this.zzltf, this.zzltg);
                return;
            default:
                Log.w("ChannelEventParcelable", "Unknown type: " + this.type);
                return;
        }
    }
}
