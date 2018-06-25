package org.telegram.messenger.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Util;

public final class GeobFrame extends Id3Frame {
    public static final Creator<GeobFrame> CREATOR = new C17301();
    public static final String ID = "GEOB";
    public final byte[] data;
    public final String description;
    public final String filename;
    public final String mimeType;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.id3.GeobFrame$1 */
    static class C17301 implements Creator<GeobFrame> {
        C17301() {
        }

        public GeobFrame createFromParcel(Parcel in) {
            return new GeobFrame(in);
        }

        public GeobFrame[] newArray(int size) {
            return new GeobFrame[size];
        }
    }

    public GeobFrame(String mimeType, String filename, String description, byte[] data) {
        super(ID);
        this.mimeType = mimeType;
        this.filename = filename;
        this.description = description;
        this.data = data;
    }

    GeobFrame(Parcel in) {
        super(ID);
        this.mimeType = in.readString();
        this.filename = in.readString();
        this.description = in.readString();
        this.data = in.createByteArray();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeobFrame other = (GeobFrame) obj;
        if (Util.areEqual(this.mimeType, other.mimeType) && Util.areEqual(this.filename, other.filename) && Util.areEqual(this.description, other.description) && Arrays.equals(this.data, other.data)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int hashCode;
        int i = 0;
        if (this.mimeType != null) {
            hashCode = this.mimeType.hashCode();
        } else {
            hashCode = 0;
        }
        int i2 = (hashCode + 527) * 31;
        if (this.filename != null) {
            hashCode = this.filename.hashCode();
        } else {
            hashCode = 0;
        }
        hashCode = (i2 + hashCode) * 31;
        if (this.description != null) {
            i = this.description.hashCode();
        }
        return ((hashCode + i) * 31) + Arrays.hashCode(this.data);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mimeType);
        dest.writeString(this.filename);
        dest.writeString(this.description);
        dest.writeByteArray(this.data);
    }
}
