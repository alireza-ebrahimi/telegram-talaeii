package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.ArrayList;
import java.util.Collection;

@Class(creator = "IsReadyToPayRequestCreator")
@Reserved({1})
public final class IsReadyToPayRequest extends AbstractSafeParcelable {
    public static final Creator<IsReadyToPayRequest> CREATOR = new zzr();
    @Field(id = 2)
    ArrayList<Integer> zzai;
    @Field(id = 4)
    private String zzbu;
    @Field(id = 5)
    private String zzbv;
    @Field(id = 6)
    ArrayList<Integer> zzbw;
    @Field(id = 7)
    boolean zzbx;
    @Field(id = 8)
    private String zzby;

    public final class Builder {
        private final /* synthetic */ IsReadyToPayRequest zzbz;

        private Builder(IsReadyToPayRequest isReadyToPayRequest) {
            this.zzbz = isReadyToPayRequest;
        }

        public final Builder addAllowedCardNetwork(int i) {
            if (this.zzbz.zzai == null) {
                this.zzbz.zzai = new ArrayList();
            }
            this.zzbz.zzai.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedCardNetworks(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            Preconditions.checkArgument(z, "allowedCardNetworks can't be null or empty. If you want the defaults, leave it unset.");
            if (this.zzbz.zzai == null) {
                this.zzbz.zzai = new ArrayList();
            }
            this.zzbz.zzai.addAll(collection);
            return this;
        }

        public final Builder addAllowedPaymentMethod(int i) {
            if (this.zzbz.zzbw == null) {
                this.zzbz.zzbw = new ArrayList();
            }
            this.zzbz.zzbw.add(Integer.valueOf(i));
            return this;
        }

        public final Builder addAllowedPaymentMethods(Collection<Integer> collection) {
            boolean z = (collection == null || collection.isEmpty()) ? false : true;
            Preconditions.checkArgument(z, "allowedPaymentMethods can't be null or empty. If you want the default, leave it unset.");
            if (this.zzbz.zzbw == null) {
                this.zzbz.zzbw = new ArrayList();
            }
            this.zzbz.zzbw.addAll(collection);
            return this;
        }

        public final IsReadyToPayRequest build() {
            return this.zzbz;
        }

        public final Builder setExistingPaymentMethodRequired(boolean z) {
            this.zzbz.zzbx = z;
            return this;
        }
    }

    IsReadyToPayRequest() {
    }

    @Constructor
    IsReadyToPayRequest(@Param(id = 2) ArrayList<Integer> arrayList, @Param(id = 4) String str, @Param(id = 5) String str2, @Param(id = 6) ArrayList<Integer> arrayList2, @Param(id = 7) boolean z, @Param(id = 8) String str3) {
        this.zzai = arrayList;
        this.zzbu = str;
        this.zzbv = str2;
        this.zzbw = arrayList2;
        this.zzbx = z;
        this.zzby = str3;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final ArrayList<Integer> getAllowedCardNetworks() {
        return this.zzai;
    }

    public final ArrayList<Integer> getAllowedPaymentMethods() {
        return this.zzbw;
    }

    public final boolean isExistingPaymentMethodRequired() {
        return this.zzbx;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeIntegerList(parcel, 2, this.zzai, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzbu, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzbv, false);
        SafeParcelWriter.writeIntegerList(parcel, 6, this.zzbw, false);
        SafeParcelWriter.writeBoolean(parcel, 7, this.zzbx);
        SafeParcelWriter.writeString(parcel, 8, this.zzby, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
