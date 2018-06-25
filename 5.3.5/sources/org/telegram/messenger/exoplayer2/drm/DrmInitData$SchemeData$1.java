package org.telegram.messenger.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import org.telegram.messenger.exoplayer2.drm.DrmInitData.SchemeData;

class DrmInitData$SchemeData$1 implements Creator<SchemeData> {
    DrmInitData$SchemeData$1() {
    }

    public SchemeData createFromParcel(Parcel in) {
        return new SchemeData(in);
    }

    public SchemeData[] newArray(int size) {
        return new SchemeData[size];
    }
}
