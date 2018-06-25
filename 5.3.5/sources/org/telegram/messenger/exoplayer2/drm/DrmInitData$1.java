package org.telegram.messenger.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable.Creator;

class DrmInitData$1 implements Creator<DrmInitData> {
    DrmInitData$1() {
    }

    public DrmInitData createFromParcel(Parcel in) {
        return new DrmInitData(in);
    }

    public DrmInitData[] newArray(int size) {
        return new DrmInitData[size];
    }
}
