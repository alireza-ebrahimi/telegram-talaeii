package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.NonNull;

public final class RepresentationKey implements Parcelable, Comparable<RepresentationKey> {
    public static final Creator<RepresentationKey> CREATOR = new C17551();
    public final int adaptationSetIndex;
    public final int periodIndex;
    public final int representationIndex;

    /* renamed from: org.telegram.messenger.exoplayer2.source.dash.manifest.RepresentationKey$1 */
    static class C17551 implements Creator<RepresentationKey> {
        C17551() {
        }

        public RepresentationKey createFromParcel(Parcel in) {
            return new RepresentationKey(in.readInt(), in.readInt(), in.readInt());
        }

        public RepresentationKey[] newArray(int size) {
            return new RepresentationKey[size];
        }
    }

    public RepresentationKey(int periodIndex, int adaptationSetIndex, int representationIndex) {
        this.periodIndex = periodIndex;
        this.adaptationSetIndex = adaptationSetIndex;
        this.representationIndex = representationIndex;
    }

    public String toString() {
        return this.periodIndex + "." + this.adaptationSetIndex + "." + this.representationIndex;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.periodIndex);
        dest.writeInt(this.adaptationSetIndex);
        dest.writeInt(this.representationIndex);
    }

    public int compareTo(@NonNull RepresentationKey o) {
        int result = this.periodIndex - o.periodIndex;
        if (result != 0) {
            return result;
        }
        result = this.adaptationSetIndex - o.adaptationSetIndex;
        if (result == 0) {
            return this.representationIndex - o.representationIndex;
        }
        return result;
    }
}
