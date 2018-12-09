package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;

@Class(creator = "LineItemCreator")
@Reserved({1})
public final class LineItem extends AbstractSafeParcelable {
    public static final Creator<LineItem> CREATOR = new zzt();
    @Field(id = 2)
    String description;
    @Field(id = 5)
    String zzan;
    @Field(id = 7)
    String zzao;
    @Field(id = 3)
    String zzca;
    @Field(id = 4)
    String zzcb;
    @Field(defaultValueUnchecked = "com.google.android.gms.wallet.LineItem.Role.REGULAR", id = 6)
    int zzcc;

    public final class Builder {
        private final /* synthetic */ LineItem zzcd;

        private Builder(LineItem lineItem) {
            this.zzcd = lineItem;
        }

        public final LineItem build() {
            return this.zzcd;
        }

        public final Builder setCurrencyCode(String str) {
            this.zzcd.zzao = str;
            return this;
        }

        public final Builder setDescription(String str) {
            this.zzcd.description = str;
            return this;
        }

        public final Builder setQuantity(String str) {
            this.zzcd.zzca = str;
            return this;
        }

        public final Builder setRole(int i) {
            this.zzcd.zzcc = i;
            return this;
        }

        public final Builder setTotalPrice(String str) {
            this.zzcd.zzan = str;
            return this;
        }

        public final Builder setUnitPrice(String str) {
            this.zzcd.zzcb = str;
            return this;
        }
    }

    public interface Role {
        public static final int REGULAR = 0;
        public static final int SHIPPING = 2;
        public static final int TAX = 1;
    }

    LineItem() {
        this.zzcc = 0;
    }

    @Constructor
    LineItem(@Param(id = 2) String str, @Param(id = 3) String str2, @Param(id = 4) String str3, @Param(id = 5) String str4, @Param(id = 6) int i, @Param(id = 7) String str5) {
        this.description = str;
        this.zzca = str2;
        this.zzcb = str3;
        this.zzan = str4;
        this.zzcc = i;
        this.zzao = str5;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final String getCurrencyCode() {
        return this.zzao;
    }

    public final String getDescription() {
        return this.description;
    }

    public final String getQuantity() {
        return this.zzca;
    }

    public final int getRole() {
        return this.zzcc;
    }

    public final String getTotalPrice() {
        return this.zzan;
    }

    public final String getUnitPrice() {
        return this.zzcb;
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.description, false);
        SafeParcelWriter.writeString(parcel, 3, this.zzca, false);
        SafeParcelWriter.writeString(parcel, 4, this.zzcb, false);
        SafeParcelWriter.writeString(parcel, 5, this.zzan, false);
        SafeParcelWriter.writeInt(parcel, 6, this.zzcc);
        SafeParcelWriter.writeString(parcel, 7, this.zzao, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
