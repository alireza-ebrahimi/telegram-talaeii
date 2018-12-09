package com.google.android.gms.wearable.internal;

import android.content.IntentFilter;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.wearable.Channel;
import com.google.android.gms.wearable.Channel.GetInputStreamResult;
import com.google.android.gms.wearable.Channel.GetOutputStreamResult;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.android.gms.wearable.ChannelClient;

@Class(creator = "ChannelImplCreator")
@Reserved({1})
public final class zzay extends AbstractSafeParcelable implements Channel, ChannelClient.Channel {
    public static final Creator<zzay> CREATOR = new zzbi();
    @Field(getter = "getToken", id = 2)
    private final String zzce;
    @Field(getter = "getPath", id = 4)
    private final String zzcl;
    @Field(getter = "getNodeId", id = 3)
    private final String zzo;

    @Constructor
    public zzay(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3) {
        this.zzce = (String) Preconditions.checkNotNull(str);
        this.zzo = (String) Preconditions.checkNotNull(str2);
        this.zzcl = (String) Preconditions.checkNotNull(str3);
    }

    public final PendingResult<Status> addListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        return zzb.zza(googleApiClient, new zzbf(this.zzce, new IntentFilter[]{zzgj.zzc("com.google.android.gms.wearable.CHANNEL_EVENT")}), channelListener);
    }

    public final PendingResult<Status> close(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzaz(this, googleApiClient));
    }

    public final PendingResult<Status> close(GoogleApiClient googleApiClient, int i) {
        return googleApiClient.enqueue(new zzba(this, googleApiClient, i));
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof zzay)) {
            return false;
        }
        zzay zzay = (zzay) obj;
        return this.zzce.equals(zzay.zzce) && Objects.equal(zzay.zzo, this.zzo) && Objects.equal(zzay.zzcl, this.zzcl);
    }

    public final PendingResult<GetInputStreamResult> getInputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzbb(this, googleApiClient));
    }

    public final String getNodeId() {
        return this.zzo;
    }

    public final PendingResult<GetOutputStreamResult> getOutputStream(GoogleApiClient googleApiClient) {
        return googleApiClient.enqueue(new zzbc(this, googleApiClient));
    }

    public final String getPath() {
        return this.zzcl;
    }

    public final int hashCode() {
        return this.zzce.hashCode();
    }

    public final PendingResult<Status> receiveFile(GoogleApiClient googleApiClient, Uri uri, boolean z) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(uri, "uri is null");
        return googleApiClient.enqueue(new zzbd(this, googleApiClient, uri, z));
    }

    public final PendingResult<Status> removeListener(GoogleApiClient googleApiClient, ChannelListener channelListener) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(channelListener, "listener is null");
        return googleApiClient.enqueue(new zzan(googleApiClient, channelListener, this.zzce));
    }

    public final PendingResult<Status> sendFile(GoogleApiClient googleApiClient, Uri uri) {
        return sendFile(googleApiClient, uri, 0, -1);
    }

    public final PendingResult<Status> sendFile(GoogleApiClient googleApiClient, Uri uri, long j, long j2) {
        Preconditions.checkNotNull(googleApiClient, "client is null");
        Preconditions.checkNotNull(this.zzce, "token is null");
        Preconditions.checkNotNull(uri, "uri is null");
        Preconditions.checkArgument(j >= 0, "startOffset is negative: %s", Long.valueOf(j));
        boolean z = j2 >= 0 || j2 == -1;
        Preconditions.checkArgument(z, "invalid length: %s", Long.valueOf(j2));
        return googleApiClient.enqueue(new zzbe(this, googleApiClient, uri, j, j2));
    }

    public final String toString() {
        String substring;
        int i = 0;
        for (char c : this.zzce.toCharArray()) {
            i += c;
        }
        String trim = this.zzce.trim();
        int length = trim.length();
        if (length > 25) {
            substring = trim.substring(0, 10);
            trim = trim.substring(length - 10, length);
            trim = new StringBuilder((String.valueOf(substring).length() + 16) + String.valueOf(trim).length()).append(substring).append("...").append(trim).append("::").append(i).toString();
        }
        substring = this.zzo;
        String str = this.zzcl;
        return new StringBuilder(((String.valueOf(trim).length() + 31) + String.valueOf(substring).length()) + String.valueOf(str).length()).append("Channel{token=").append(trim).append(", nodeId=").append(substring).append(", path=").append(str).append("}").toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzce, false);
        SafeParcelWriter.writeString(parcel, 3, getNodeId(), false);
        SafeParcelWriter.writeString(parcel, 4, getPath(), false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public final String zzc() {
        return this.zzce;
    }
}
