package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;

public final class WalletObjectMessage extends zzbgl {
    public static final Creator<WalletObjectMessage> CREATOR = new zzn();
    String body;
    String zzlqg;
    TimeInterval zzlqj;
    UriData zzlqk;
    UriData zzlql;

    public final class Builder {
        private /* synthetic */ WalletObjectMessage zzlqm;

        private Builder(WalletObjectMessage walletObjectMessage) {
            this.zzlqm = walletObjectMessage;
        }

        public final WalletObjectMessage build() {
            return this.zzlqm;
        }

        public final Builder setActionUri(UriData uriData) {
            this.zzlqm.zzlqk = uriData;
            return this;
        }

        public final Builder setBody(String str) {
            this.zzlqm.body = str;
            return this;
        }

        public final Builder setDisplayInterval(TimeInterval timeInterval) {
            this.zzlqm.zzlqj = timeInterval;
            return this;
        }

        public final Builder setHeader(String str) {
            this.zzlqm.zzlqg = str;
            return this;
        }

        public final Builder setImageUri(UriData uriData) {
            this.zzlqm.zzlql = uriData;
            return this;
        }
    }

    WalletObjectMessage() {
    }

    WalletObjectMessage(String str, String str2, TimeInterval timeInterval, UriData uriData, UriData uriData2) {
        this.zzlqg = str;
        this.body = str2;
        this.zzlqj = timeInterval;
        this.zzlqk = uriData;
        this.zzlql = uriData2;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final UriData getActionUri() {
        return this.zzlqk;
    }

    public final String getBody() {
        return this.body;
    }

    public final TimeInterval getDisplayInterval() {
        return this.zzlqj;
    }

    public final String getHeader() {
        return this.zzlqg;
    }

    public final UriData getImageUri() {
        return this.zzlql;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlqg, false);
        zzbgo.zza(parcel, 3, this.body, false);
        zzbgo.zza(parcel, 4, this.zzlqj, i, false);
        zzbgo.zza(parcel, 5, this.zzlqk, i, false);
        zzbgo.zza(parcel, 6, this.zzlql, i, false);
        zzbgo.zzai(parcel, zze);
    }
}
