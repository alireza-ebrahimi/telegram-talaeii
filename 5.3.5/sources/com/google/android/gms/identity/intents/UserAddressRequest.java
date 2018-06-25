package com.google.android.gms.identity.intents;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.identity.intents.model.CountrySpecification;
import com.google.android.gms.internal.zzbgl;
import com.google.android.gms.internal.zzbgo;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class UserAddressRequest extends zzbgl implements ReflectedParcelable {
    public static final Creator<UserAddressRequest> CREATOR = new zzd();
    List<CountrySpecification> zziln;

    public final class Builder {
        private /* synthetic */ UserAddressRequest zzilo;

        private Builder(UserAddressRequest userAddressRequest) {
            this.zzilo = userAddressRequest;
        }

        public final Builder addAllowedCountrySpecification(CountrySpecification countrySpecification) {
            if (this.zzilo.zziln == null) {
                this.zzilo.zziln = new ArrayList();
            }
            this.zzilo.zziln.add(countrySpecification);
            return this;
        }

        public final Builder addAllowedCountrySpecifications(Collection<CountrySpecification> collection) {
            if (this.zzilo.zziln == null) {
                this.zzilo.zziln = new ArrayList();
            }
            this.zzilo.zziln.addAll(collection);
            return this;
        }

        public final UserAddressRequest build() {
            if (this.zzilo.zziln != null) {
                this.zzilo.zziln = Collections.unmodifiableList(this.zzilo.zziln);
            }
            return this.zzilo;
        }
    }

    UserAddressRequest() {
    }

    UserAddressRequest(List<CountrySpecification> list) {
        this.zziln = list;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int zze = zzbgo.zze(parcel);
        zzbgo.zzc(parcel, 2, this.zziln, false);
        zzbgo.zzai(parcel, zze);
    }
}
