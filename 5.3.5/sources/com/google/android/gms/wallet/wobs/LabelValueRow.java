package com.google.android.gms.wallet.wobs;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;

public final class LabelValueRow extends zzbgl {
    public static final Creator<LabelValueRow> CREATOR = new zze();
    String zzlpu;
    String zzlpv;
    ArrayList<LabelValue> zzlpw;

    public final class Builder {
        private /* synthetic */ LabelValueRow zzlpx;

        private Builder(LabelValueRow labelValueRow) {
            this.zzlpx = labelValueRow;
        }

        public final Builder addColumn(LabelValue labelValue) {
            this.zzlpx.zzlpw.add(labelValue);
            return this;
        }

        public final Builder addColumns(Collection<LabelValue> collection) {
            this.zzlpx.zzlpw.addAll(collection);
            return this;
        }

        public final LabelValueRow build() {
            return this.zzlpx;
        }

        public final Builder setHexBackgroundColor(String str) {
            this.zzlpx.zzlpv = str;
            return this;
        }

        public final Builder setHexFontColor(String str) {
            this.zzlpx.zzlpu = str;
            return this;
        }
    }

    LabelValueRow() {
        this.zzlpw = new ArrayList();
    }

    LabelValueRow(String str, String str2, ArrayList<LabelValue> arrayList) {
        this.zzlpu = str;
        this.zzlpv = str2;
        this.zzlpw = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<LabelValue> getColumns() {
        return this.zzlpw;
    }

    public final String getHexBackgroundColor() {
        return this.zzlpv;
    }

    public final String getHexFontColor() {
        return this.zzlpu;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzlpu, false);
        zzbgo.zza(parcel, 3, this.zzlpv, false);
        zzbgo.zzc(parcel, 4, this.zzlpw, false);
        zzbgo.zzai(parcel, zze);
    }
}
