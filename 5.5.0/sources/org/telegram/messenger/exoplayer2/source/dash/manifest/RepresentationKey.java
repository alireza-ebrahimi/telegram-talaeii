package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class RepresentationKey implements Parcelable, Comparable<RepresentationKey> {
    public static final Creator<RepresentationKey> CREATOR = new C35301();
    public final int adaptationSetIndex;
    public final int periodIndex;
    public final int representationIndex;

    /* renamed from: org.telegram.messenger.exoplayer2.source.dash.manifest.RepresentationKey$1 */
    static class C35301 implements Creator<RepresentationKey> {
        C35301() {
        }

        public RepresentationKey createFromParcel(Parcel parcel) {
            return new RepresentationKey(parcel.readInt(), parcel.readInt(), parcel.readInt());
        }

        public RepresentationKey[] newArray(int i) {
            return new RepresentationKey[i];
        }
    }

    public RepresentationKey(int i, int i2, int i3) {
        this.periodIndex = i;
        this.adaptationSetIndex = i2;
        this.representationIndex = i3;
    }

    public int compareTo(RepresentationKey representationKey) {
        int i = this.periodIndex - representationKey.periodIndex;
        if (i != 0) {
            return i;
        }
        i = this.adaptationSetIndex - representationKey.adaptationSetIndex;
        return i == 0 ? this.representationIndex - representationKey.representationIndex : i;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return this.periodIndex + "." + this.adaptationSetIndex + "." + this.representationIndex;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.periodIndex);
        parcel.writeInt(this.adaptationSetIndex);
        parcel.writeInt(this.representationIndex);
    }
}
