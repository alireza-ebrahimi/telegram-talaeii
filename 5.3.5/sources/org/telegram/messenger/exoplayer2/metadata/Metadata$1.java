package org.telegram.messenger.exoplayer2.metadata;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class Metadata$1 implements Creator<Metadata> {
    Metadata$1() {
    }

    public Metadata createFromParcel(Parcel in) {
        return new Metadata(in);
    }

    public Metadata[] newArray(int size) {
        return new Metadata[0];
    }
}
