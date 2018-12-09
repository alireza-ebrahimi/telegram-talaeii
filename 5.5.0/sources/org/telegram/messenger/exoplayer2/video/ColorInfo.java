package org.telegram.messenger.exoplayer2.video;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;

public final class ColorInfo implements Parcelable {
    public static final Creator<ColorInfo> CREATOR = new C35441();
    public final int colorRange;
    public final int colorSpace;
    public final int colorTransfer;
    private int hashCode;
    public final byte[] hdrStaticInfo;

    /* renamed from: org.telegram.messenger.exoplayer2.video.ColorInfo$1 */
    static class C35441 implements Creator<ColorInfo> {
        C35441() {
        }

        public ColorInfo createFromParcel(Parcel parcel) {
            return new ColorInfo(parcel);
        }

        public ColorInfo[] newArray(int i) {
            return new ColorInfo[0];
        }
    }

    public ColorInfo(int i, int i2, int i3, byte[] bArr) {
        this.colorSpace = i;
        this.colorRange = i2;
        this.colorTransfer = i3;
        this.hdrStaticInfo = bArr;
    }

    ColorInfo(Parcel parcel) {
        this.colorSpace = parcel.readInt();
        this.colorRange = parcel.readInt();
        this.colorTransfer = parcel.readInt();
        this.hdrStaticInfo = (parcel.readInt() != 0 ? 1 : null) != null ? parcel.createByteArray() : null;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        ColorInfo colorInfo = (ColorInfo) obj;
        return this.colorSpace == colorInfo.colorSpace && this.colorRange == colorInfo.colorRange && this.colorTransfer == colorInfo.colorTransfer && Arrays.equals(this.hdrStaticInfo, colorInfo.hdrStaticInfo);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = ((((((this.colorSpace + 527) * 31) + this.colorRange) * 31) + this.colorTransfer) * 31) + Arrays.hashCode(this.hdrStaticInfo);
        }
        return this.hashCode;
    }

    public String toString() {
        return "ColorInfo(" + this.colorSpace + ", " + this.colorRange + ", " + this.colorTransfer + ", " + (this.hdrStaticInfo != null) + ")";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.colorSpace);
        parcel.writeInt(this.colorRange);
        parcel.writeInt(this.colorTransfer);
        parcel.writeInt(this.hdrStaticInfo != null ? 1 : 0);
        if (this.hdrStaticInfo != null) {
            parcel.writeByteArray(this.hdrStaticInfo);
        }
    }
}
