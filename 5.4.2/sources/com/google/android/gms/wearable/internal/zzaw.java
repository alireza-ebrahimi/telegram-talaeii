package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;

@Class(creator = "ChannelEventParcelableCreator")
@Reserved({1})
public final class zzaw extends AbstractSafeParcelable {
    public static final Creator<zzaw> CREATOR = new zzax();
    @Field(id = 3)
    private final int type;
    @Field(id = 5)
    private final int zzcj;
    @Field(id = 2)
    private final zzay zzck;
    @Field(id = 4)
    private final int zzg;

    @Constructor
    public zzaw(@Param(id = 2) zzay zzay, @Param(id = 3) int i, @Param(id = 4) int i2, @Param(id = 5) int i3) {
        this.zzck = zzay;
        this.type = i;
        this.zzg = i2;
        this.zzcj = i3;
    }

    public final String toString() {
        String str;
        String str2;
        String valueOf = String.valueOf(this.zzck);
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
        int i2 = this.zzg;
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
        return new StringBuilder(((String.valueOf(valueOf).length() + 81) + String.valueOf(str).length()) + String.valueOf(str2).length()).append("ChannelEventParcelable[, channel=").append(valueOf).append(", type=").append(str).append(", closeReason=").append(str2).append(", appErrorCode=").append(this.zzcj).append("]").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.zzck, i, false);
        SafeParcelWriter.writeInt(parcel, 3, this.type);
        SafeParcelWriter.writeInt(parcel, 4, this.zzg);
        SafeParcelWriter.writeInt(parcel, 5, this.zzcj);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final void zza(ChannelListener channelListener) {
        switch (this.type) {
            case 1:
                channelListener.onChannelOpened(this.zzck);
                return;
            case 2:
                channelListener.onChannelClosed(this.zzck, this.zzg, this.zzcj);
                return;
            case 3:
                channelListener.onInputClosed(this.zzck, this.zzg, this.zzcj);
                return;
            case 4:
                channelListener.onOutputClosed(this.zzck, this.zzg, this.zzcj);
                return;
            default:
                Log.w("ChannelEventParcelable", "Unknown type: " + this.type);
                return;
        }
    }
}
