package com.google.android.gms.maps.model;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import com.google.android.gms.maps.GoogleMapOptions;

@Class(creator = "CameraPositionCreator")
@Reserved({1})
public final class CameraPosition extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<CameraPosition> CREATOR = new zza();
    @Field(id = 5)
    public final float bearing;
    @Field(id = 2)
    public final LatLng target;
    @Field(id = 4)
    public final float tilt;
    @Field(id = 3)
    public final float zoom;

    public static final class Builder {
        private float bearing;
        private LatLng target;
        private float tilt;
        private float zoom;

        public Builder(CameraPosition cameraPosition) {
            this.target = cameraPosition.target;
            this.zoom = cameraPosition.zoom;
            this.tilt = cameraPosition.tilt;
            this.bearing = cameraPosition.bearing;
        }

        public final Builder bearing(float f) {
            this.bearing = f;
            return this;
        }

        public final CameraPosition build() {
            return new CameraPosition(this.target, this.zoom, this.tilt, this.bearing);
        }

        public final Builder target(LatLng latLng) {
            this.target = latLng;
            return this;
        }

        public final Builder tilt(float f) {
            this.tilt = f;
            return this;
        }

        public final Builder zoom(float f) {
            this.zoom = f;
            return this;
        }
    }

    @Constructor
    public CameraPosition(@Param(id = 2) LatLng latLng, @Param(id = 3) float f, @Param(id = 4) float f2, @Param(id = 5) float f3) {
        Preconditions.checkNotNull(latLng, "null camera target");
        boolean z = BitmapDescriptorFactory.HUE_RED <= f2 && f2 <= 90.0f;
        Preconditions.checkArgument(z, "Tilt needs to be between 0 and 90 inclusive: %s", Float.valueOf(f2));
        this.target = latLng;
        this.zoom = f;
        this.tilt = f2 + BitmapDescriptorFactory.HUE_RED;
        if (((double) f3) <= 0.0d) {
            f3 = (f3 % 360.0f) + 360.0f;
        }
        this.bearing = f3 % 360.0f;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static Builder builder(CameraPosition cameraPosition) {
        return new Builder(cameraPosition);
    }

    public static CameraPosition createFromAttributes(Context context, AttributeSet attributeSet) {
        return GoogleMapOptions.zzb(context, attributeSet);
    }

    public static final CameraPosition fromLatLngZoom(LatLng latLng, float f) {
        return new CameraPosition(latLng, f, BitmapDescriptorFactory.HUE_RED, BitmapDescriptorFactory.HUE_RED);
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof CameraPosition)) {
            return false;
        }
        CameraPosition cameraPosition = (CameraPosition) obj;
        return this.target.equals(cameraPosition.target) && Float.floatToIntBits(this.zoom) == Float.floatToIntBits(cameraPosition.zoom) && Float.floatToIntBits(this.tilt) == Float.floatToIntBits(cameraPosition.tilt) && Float.floatToIntBits(this.bearing) == Float.floatToIntBits(cameraPosition.bearing);
    }

    public final int hashCode() {
        return Objects.hashCode(this.target, Float.valueOf(this.zoom), Float.valueOf(this.tilt), Float.valueOf(this.bearing));
    }

    public final String toString() {
        return Objects.toStringHelper(this).add("target", this.target).add("zoom", Float.valueOf(this.zoom)).add("tilt", Float.valueOf(this.tilt)).add("bearing", Float.valueOf(this.bearing)).toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeParcelable(parcel, 2, this.target, i, false);
        SafeParcelWriter.writeFloat(parcel, 3, this.zoom);
        SafeParcelWriter.writeFloat(parcel, 4, this.tilt);
        SafeParcelWriter.writeFloat(parcel, 5, this.bearing);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
