package org.telegram.messenger.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Util;

public final class PrivFrame extends Id3Frame {
    public static final Creator<PrivFrame> CREATOR = new C17311();
    public static final String ID = "PRIV";
    public final String owner;
    public final byte[] privateData;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.id3.PrivFrame$1 */
    static class C17311 implements Creator<PrivFrame> {
        C17311() {
        }

        public PrivFrame createFromParcel(Parcel in) {
            return new PrivFrame(in);
        }

        public PrivFrame[] newArray(int size) {
            return new PrivFrame[size];
        }
    }

    public PrivFrame(String owner, byte[] privateData) {
        super(ID);
        this.owner = owner;
        this.privateData = privateData;
    }

    PrivFrame(Parcel in) {
        super(ID);
        this.owner = in.readString();
        this.privateData = in.createByteArray();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        PrivFrame other = (PrivFrame) obj;
        if (Util.areEqual(this.owner, other.owner) && Arrays.equals(this.privateData, other.privateData)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (((this.owner != null ? this.owner.hashCode() : 0) + 527) * 31) + Arrays.hashCode(this.privateData);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.owner);
        dest.writeByteArray(this.privateData);
    }
}
