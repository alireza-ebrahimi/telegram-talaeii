package org.telegram.messenger.exoplayer2.metadata.id3;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.util.Util;

public final class GeobFrame extends Id3Frame {
    public static final Creator<GeobFrame> CREATOR = new C35041();
    public static final String ID = "GEOB";
    public final byte[] data;
    public final String description;
    public final String filename;
    public final String mimeType;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.id3.GeobFrame$1 */
    static class C35041 implements Creator<GeobFrame> {
        C35041() {
        }

        public GeobFrame createFromParcel(Parcel parcel) {
            return new GeobFrame(parcel);
        }

        public GeobFrame[] newArray(int i) {
            return new GeobFrame[i];
        }
    }

    GeobFrame(Parcel parcel) {
        super(ID);
        this.mimeType = parcel.readString();
        this.filename = parcel.readString();
        this.description = parcel.readString();
        this.data = parcel.createByteArray();
    }

    public GeobFrame(String str, String str2, String str3, byte[] bArr) {
        super(ID);
        this.mimeType = str;
        this.filename = str2;
        this.description = str3;
        this.data = bArr;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeobFrame geobFrame = (GeobFrame) obj;
        return Util.areEqual(this.mimeType, geobFrame.mimeType) && Util.areEqual(this.filename, geobFrame.filename) && Util.areEqual(this.description, geobFrame.description) && Arrays.equals(this.data, geobFrame.data);
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((this.filename != null ? this.filename.hashCode() : 0) + (((this.mimeType != null ? this.mimeType.hashCode() : 0) + 527) * 31)) * 31;
        if (this.description != null) {
            i = this.description.hashCode();
        }
        return ((hashCode + i) * 31) + Arrays.hashCode(this.data);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mimeType);
        parcel.writeString(this.filename);
        parcel.writeString(this.description);
        parcel.writeByteArray(this.data);
    }
}
