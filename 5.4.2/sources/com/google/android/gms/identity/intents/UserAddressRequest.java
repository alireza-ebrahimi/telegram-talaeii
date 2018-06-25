package com.google.android.gms.identity.intents;

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
import com.google.android.gms.identity.intents.model.CountrySpecification;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Class(creator = "UserAddressRequestCreator")
@Reserved({1})
public final class UserAddressRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<UserAddressRequest> CREATOR = new zzd();
    @Field(id = 2)
    List<CountrySpecification> zzf;

    public final class Builder {
        private final /* synthetic */ UserAddressRequest zzg;

        private Builder(UserAddressRequest userAddressRequest) {
            this.zzg = userAddressRequest;
        }

        public final Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (this.zzg.zzf == null) {
                this.zzg.zzf = new ArrayList();
            }
            this.zzg.zzf.add(countrySpecification);
            return this;
        }

        public final Builder addAllowedCountrySpecifications(Collection<CountrySpecification> collection) {
            if (this.zzg.zzf == null) {
                this.zzg.zzf = new ArrayList();
            }
            this.zzg.zzf.addAll(collection);
            return this;
        }

        public final UserAddressRequest build() {
            if (this.zzg.zzf != null) {
                this.zzg.zzf = Collections.unmodifiableList(this.zzg.zzf);
            }
            return this.zzg;
        }
    }

    UserAddressRequest() {
    }

    @Constructor
    UserAddressRequest(@Param(id = 2) List<CountrySpecification> list) {
        this.zzf = list;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeTypedList(parcel, 2, this.zzf, false);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
