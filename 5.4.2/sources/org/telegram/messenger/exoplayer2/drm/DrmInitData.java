package org.telegram.messenger.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DrmInitData implements Parcelable, Comparator<SchemeData> {
    public static final Creator<DrmInitData> CREATOR = new C34711();
    private int hashCode;
    public final int schemeDataCount;
    private final SchemeData[] schemeDatas;

    /* renamed from: org.telegram.messenger.exoplayer2.drm.DrmInitData$1 */
    static class C34711 implements Creator<DrmInitData> {
        C34711() {
        }

        public DrmInitData createFromParcel(Parcel parcel) {
            return new DrmInitData(parcel);
        }

        public DrmInitData[] newArray(int i) {
            return new DrmInitData[i];
        }
    }

    public static final class SchemeData implements Parcelable {
        public static final Creator<SchemeData> CREATOR = new C34721();
        public final byte[] data;
        private int hashCode;
        public final String mimeType;
        public final boolean requiresSecureDecryption;
        public final String type;
        private final UUID uuid;

        /* renamed from: org.telegram.messenger.exoplayer2.drm.DrmInitData$SchemeData$1 */
        static class C34721 implements Creator<SchemeData> {
            C34721() {
            }

            public SchemeData createFromParcel(Parcel parcel) {
                return new SchemeData(parcel);
            }

            public SchemeData[] newArray(int i) {
                return new SchemeData[i];
            }
        }

        SchemeData(Parcel parcel) {
            this.uuid = new UUID(parcel.readLong(), parcel.readLong());
            this.type = parcel.readString();
            this.mimeType = parcel.readString();
            this.data = parcel.createByteArray();
            this.requiresSecureDecryption = parcel.readByte() != (byte) 0;
        }

        public SchemeData(UUID uuid, String str, String str2, byte[] bArr) {
            this(uuid, str, str2, bArr, false);
        }

        public SchemeData(UUID uuid, String str, String str2, byte[] bArr, boolean z) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.type = str;
            this.mimeType = (String) Assertions.checkNotNull(str2);
            this.data = (byte[]) Assertions.checkNotNull(bArr);
            this.requiresSecureDecryption = z;
        }

        public SchemeData copyWithSchemeType(String str) {
            if (Util.areEqual(this.type, str)) {
                return this;
            }
            return new SchemeData(this.uuid, str, this.mimeType, this.data, this.requiresSecureDecryption);
        }

        public int describeContents() {
            return 0;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SchemeData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            SchemeData schemeData = (SchemeData) obj;
            return this.mimeType.equals(schemeData.mimeType) && Util.areEqual(this.uuid, schemeData.uuid) && Util.areEqual(this.type, schemeData.type) && Arrays.equals(this.data, schemeData.data);
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = (((((this.type == null ? 0 : this.type.hashCode()) + (this.uuid.hashCode() * 31)) * 31) + this.mimeType.hashCode()) * 31) + Arrays.hashCode(this.data);
            }
            return this.hashCode;
        }

        public boolean matches(UUID uuid) {
            return C3446C.UUID_NIL.equals(this.uuid) || uuid.equals(this.uuid);
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeLong(this.uuid.getMostSignificantBits());
            parcel.writeLong(this.uuid.getLeastSignificantBits());
            parcel.writeString(this.type);
            parcel.writeString(this.mimeType);
            parcel.writeByteArray(this.data);
            parcel.writeByte((byte) (this.requiresSecureDecryption ? 1 : 0));
        }
    }

    DrmInitData(Parcel parcel) {
        this.schemeDatas = (SchemeData[]) parcel.createTypedArray(SchemeData.CREATOR);
        this.schemeDataCount = this.schemeDatas.length;
    }

    public DrmInitData(List<SchemeData> list) {
        this(false, (SchemeData[]) list.toArray(new SchemeData[list.size()]));
    }

    private DrmInitData(boolean z, SchemeData... schemeDataArr) {
        SchemeData[] schemeDataArr2 = z ? (SchemeData[]) schemeDataArr.clone() : schemeDataArr;
        Arrays.sort(schemeDataArr2, this);
        for (int i = 1; i < schemeDataArr2.length; i++) {
            if (schemeDataArr2[i - 1].uuid.equals(schemeDataArr2[i].uuid)) {
                throw new IllegalArgumentException("Duplicate data for uuid: " + schemeDataArr2[i].uuid);
            }
        }
        this.schemeDatas = schemeDataArr2;
        this.schemeDataCount = schemeDataArr2.length;
    }

    public DrmInitData(SchemeData... schemeDataArr) {
        this(true, schemeDataArr);
    }

    public int compare(SchemeData schemeData, SchemeData schemeData2) {
        return C3446C.UUID_NIL.equals(schemeData.uuid) ? C3446C.UUID_NIL.equals(schemeData2.uuid) ? 0 : 1 : schemeData.uuid.compareTo(schemeData2.uuid);
    }

    public DrmInitData copyWithSchemeType(String str) {
        int i;
        int i2 = 0;
        for (SchemeData schemeData : this.schemeDatas) {
            if (!Util.areEqual(schemeData.type, str)) {
                i = 1;
                break;
            }
        }
        i = 0;
        if (i == 0) {
            return this;
        }
        SchemeData[] schemeDataArr = new SchemeData[this.schemeDatas.length];
        while (i2 < schemeDataArr.length) {
            schemeDataArr[i2] = this.schemeDatas[i2].copyWithSchemeType(str);
            i2++;
        }
        return new DrmInitData(schemeDataArr);
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        return this == obj ? true : (obj == null || getClass() != obj.getClass()) ? false : Arrays.equals(this.schemeDatas, ((DrmInitData) obj).schemeDatas);
    }

    public SchemeData get(int i) {
        return this.schemeDatas[i];
    }

    public SchemeData get(UUID uuid) {
        for (SchemeData schemeData : this.schemeDatas) {
            if (schemeData.matches(uuid)) {
                return schemeData;
            }
        }
        return null;
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = Arrays.hashCode(this.schemeDatas);
        }
        return this.hashCode;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedArray(this.schemeDatas, 0);
    }
}
