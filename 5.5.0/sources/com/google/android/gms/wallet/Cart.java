package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.ArrayList;
import java.util.List;

@Class(creator = "CartCreator")
@Reserved({1})
public final class Cart extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<Cart> CREATOR = new zzg();
    @Field(id = 2)
    String zzan;
    @Field(id = 3)
    String zzao;
    @Field(defaultValueUnchecked = "new java.util.ArrayList<LineItem>()", id = 4)
    ArrayList<LineItem> zzap;

    public final class Builder {
        private final /* synthetic */ Cart zzaq;

        private Builder(Cart cart) {
            this.zzaq = cart;
        }

        public final Builder addLineItem(LineItem lineItem) {
            this.zzaq.zzap.add(lineItem);
            return this;
        }

        public final Cart build() {
            return this.zzaq;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzaq.zzao = str;
            return this;
        }

        public final Builder setLineItems(List<LineItem> list) {
            this.zzaq.zzap.clear();
            this.zzaq.zzap.addAll(list);
            return this;
        }

        public final Builder setTotalPrice(String str) {
            this.zzaq.zzan = str;
            return this;
        }
    }

    Cart() {
        this.zzap = new ArrayList();
    }

    @Constructor
    Cart(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) ArrayList<LineItem> arrayList) {
        this.zzan = str;
        this.zzao = str2;
        this.zzap = arrayList;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzao;
    }

    public final ArrayList<LineItem> getLineItems() {
        return this.zzap;
    }

    public final String getTotalPrice() {
        return this.zzan;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.zzan, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzao, false);
        SafeParcelWriter.writeTypedList(parcel, 4, this.zzap, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
