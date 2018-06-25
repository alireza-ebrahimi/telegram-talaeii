package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.List;

public final class Cart extends zzbgl implements ReflectedParcelable {
    public static final Creator<Cart> CREATOR = new zzg();
    String zzljt;
    String zzlju;
    ArrayList<LineItem> zzljv;

    public final class Builder {
        private /* synthetic */ Cart zzljw;

        private Builder(Cart cart) {
            this.zzljw = cart;
        }

        public final Builder addLineItem(LineItem lineItem) {
            this.zzljw.zzljv.add(lineItem);
            return this;
        }

        public final Cart build() {
            return this.zzljw;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzljw.zzlju = str;
            return this;
        }

        public final Builder setLineItems(List<LineItem> list) {
            this.zzljw.zzljv.clear();
            this.zzljw.zzljv.addAll(list);
            return this;
        }

        public final Builder setTotalPrice(String str) {
            this.zzljw.zzljt = str;
            return this;
        }
    }

    Cart() {
        this.zzljv = new ArrayList();
    }

    Cart(String str, String str2, ArrayList<LineItem> arrayList) {
        this.zzljt = str;
        this.zzlju = str2;
        this.zzljv = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzlju;
    }

    public final ArrayList<LineItem> getLineItems() {
        return this.zzljv;
    }

    public final String getTotalPrice() {
        return this.zzljt;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zza(parcel, 2, this.zzljt, false);
        zzbgo.zza(parcel, 3, this.zzlju, false);
        zzbgo.zzc(parcel, 4, this.zzljv, false);
        zzbgo.zzai(parcel, zze);
    }
}
