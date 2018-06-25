package org.telegram.messenger.exoplayer2;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class Format$1 implements Creator<Format> {
    Format$1() {
    }

    public Format createFromParcel(Parcel in) {
        return new Format(in);
    }

    public Format[] newArray(int size) {
        return new Format[size];
    }
}
