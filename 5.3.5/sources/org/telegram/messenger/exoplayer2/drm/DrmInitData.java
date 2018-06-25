package org.telegram.messenger.exoplayer2.drm;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DrmInitData implements Comparator<SchemeData>, Parcelable {
    public static final Creator<DrmInitData> CREATOR = new DrmInitData$1();
    private int hashCode;
    public final int schemeDataCount;
    private final SchemeData[] schemeDatas;

    public static final class SchemeData implements Parcelable {
        public static final Creator<SchemeData> CREATOR = new DrmInitData$SchemeData$1();
        public final byte[] data;
        private int hashCode;
        public final String mimeType;
        public final boolean requiresSecureDecryption;
        @Nullable
        public final String type;
        private final UUID uuid;

        public SchemeData(UUID uuid, @Nullable String type, String mimeType, byte[] data) {
            this(uuid, type, mimeType, data, false);
        }

        public SchemeData(UUID uuid, @Nullable String type, String mimeType, byte[] data, boolean requiresSecureDecryption) {
            this.uuid = (UUID) Assertions.checkNotNull(uuid);
            this.type = type;
            this.mimeType = (String) Assertions.checkNotNull(mimeType);
            this.data = (byte[]) Assertions.checkNotNull(data);
            this.requiresSecureDecryption = requiresSecureDecryption;
        }

        SchemeData(Parcel in) {
            this.uuid = new UUID(in.readLong(), in.readLong());
            this.type = in.readString();
            this.mimeType = in.readString();
            this.data = in.createByteArray();
            this.requiresSecureDecryption = in.readByte() != (byte) 0;
        }

        public boolean matches(UUID schemeUuid) {
            return C0907C.UUID_NIL.equals(this.uuid) || schemeUuid.equals(this.uuid);
        }

        public SchemeData copyWithSchemeType(String type) {
            if (Util.areEqual(this.type, type)) {
                return this;
            }
            return new SchemeData(this.uuid, type, this.mimeType, this.data, this.requiresSecureDecryption);
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof SchemeData)) {
                return false;
            }
            if (obj == this) {
                return true;
            }
            SchemeData other = (SchemeData) obj;
            if (this.mimeType.equals(other.mimeType) && Util.areEqual(this.uuid, other.uuid) && Util.areEqual(this.type, other.type) && Arrays.equals(this.data, other.data)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            if (this.hashCode == 0) {
                this.hashCode = (((((this.uuid.hashCode() * 31) + (this.type == null ? 0 : this.type.hashCode())) * 31) + this.mimeType.hashCode()) * 31) + Arrays.hashCode(this.data);
            }
            return this.hashCode;
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel dest, int flags) {
            dest.writeLong(this.uuid.getMostSignificantBits());
            dest.writeLong(this.uuid.getLeastSignificantBits());
            dest.writeString(this.type);
            dest.writeString(this.mimeType);
            dest.writeByteArray(this.data);
            dest.writeByte((byte) (this.requiresSecureDecryption ? 1 : 0));
        }
    }

    public DrmInitData(List<SchemeData> schemeDatas) {
        this(false, (SchemeData[]) schemeDatas.toArray(new SchemeData[schemeDatas.size()]));
    }

    public DrmInitData(SchemeData... schemeDatas) {
        this(true, schemeDatas);
    }

    private DrmInitData(boolean cloneSchemeDatas, SchemeData... schemeDatas) {
        if (cloneSchemeDatas) {
            schemeDatas = (SchemeData[]) schemeDatas.clone();
        }
        Arrays.sort(schemeDatas, this);
        for (int i = 1; i < schemeDatas.length; i++) {
            if (schemeDatas[i - 1].uuid.equals(schemeDatas[i].uuid)) {
                throw new IllegalArgumentException("Duplicate data for uuid: " + schemeDatas[i].uuid);
            }
        }
        this.schemeDatas = schemeDatas;
        this.schemeDataCount = schemeDatas.length;
    }

    DrmInitData(Parcel in) {
        this.schemeDatas = (SchemeData[]) in.createTypedArray(SchemeData.CREATOR);
        this.schemeDataCount = this.schemeDatas.length;
    }

    public SchemeData get(UUID uuid) {
        for (SchemeData schemeData : this.schemeDatas) {
            if (schemeData.matches(uuid)) {
                return schemeData;
            }
        }
        return null;
    }

    public SchemeData get(int index) {
        return this.schemeDatas[index];
    }

    public DrmInitData copyWithSchemeType(@Nullable String schemeType) {
        boolean isCopyRequired = false;
        for (SchemeData schemeData : this.schemeDatas) {
            if (!Util.areEqual(schemeData.type, schemeType)) {
                isCopyRequired = true;
                break;
            }
        }
        if (!isCopyRequired) {
            return this;
        }
        SchemeData[] schemeDatas = new SchemeData[this.schemeDatas.length];
        for (int i = 0; i < schemeDatas.length; i++) {
            schemeDatas[i] = this.schemeDatas[i].copyWithSchemeType(schemeType);
        }
        return new DrmInitData(schemeDatas);
    }

    public int hashCode() {
        if (this.hashCode == 0) {
            this.hashCode = Arrays.hashCode(this.schemeDatas);
        }
        return this.hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        return Arrays.equals(this.schemeDatas, ((DrmInitData) obj).schemeDatas);
    }

    public int compare(SchemeData first, SchemeData second) {
        if (C0907C.UUID_NIL.equals(first.uuid)) {
            return C0907C.UUID_NIL.equals(second.uuid) ? 0 : 1;
        } else {
            return first.uuid.compareTo(second.uuid);
        }
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedArray(this.schemeDatas, 0);
    }
}
