package com.google.android.gms.phenotype;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Base64;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Class;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Constructor;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Field;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Param;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable.Reserved;
import java.util.Arrays;
import java.util.Comparator;

@Class(creator = "FlagCreator")
@Reserved({1})
public final class zzi extends AbstractSafeParcelable implements Comparable<zzi> {
    public static final Creator<zzi> CREATOR = new zzk();
    private static final Comparator<zzi> zzai = new zzj();
    @Field(id = 2)
    public final String name;
    @Field(id = 3)
    private final long zzab;
    @Field(id = 4)
    private final boolean zzac;
    @Field(id = 5)
    private final double zzad;
    @Field(id = 6)
    private final String zzae;
    @Field(id = 7)
    private final byte[] zzaf;
    @Field(id = 8)
    private final int zzag;
    @Field(id = 9)
    public final int zzah;

    @Constructor
    public zzi(@Param(id = 2) String str, @Param(id = 3) long j, @Param(id = 4) boolean z, @Param(id = 5) double d, @Param(id = 6) String str2, @Param(id = 7) byte[] bArr, @Param(id = 8) int i, @Param(id = 9) int i2) {
        this.name = str;
        this.zzab = j;
        this.zzac = z;
        this.zzad = d;
        this.zzae = str2;
        this.zzaf = bArr;
        this.zzag = i;
        this.zzah = i2;
    }

    private static int compare(int i, int i2) {
        return i < i2 ? -1 : i == i2 ? 0 : 1;
    }

    public final /* synthetic */ int compareTo(Object obj) {
        int i = 0;
        zzi zzi = (zzi) obj;
        int compareTo = this.name.compareTo(zzi.name);
        if (compareTo != 0) {
            return compareTo;
        }
        compareTo = compare(this.zzag, zzi.zzag);
        if (compareTo != 0) {
            return compareTo;
        }
        switch (this.zzag) {
            case 1:
                long j = this.zzab;
                long j2 = zzi.zzab;
                return j < j2 ? -1 : j != j2 ? 1 : 0;
            case 2:
                boolean z = this.zzac;
                return z != zzi.zzac ? z ? 1 : -1 : 0;
            case 3:
                return Double.compare(this.zzad, zzi.zzad);
            case 4:
                String str = this.zzae;
                String str2 = zzi.zzae;
                return str != str2 ? str == null ? -1 : str2 == null ? 1 : str.compareTo(str2) : 0;
            case 5:
                if (this.zzaf == zzi.zzaf) {
                    return 0;
                }
                if (this.zzaf == null) {
                    return -1;
                }
                if (zzi.zzaf == null) {
                    return 1;
                }
                while (i < Math.min(this.zzaf.length, zzi.zzaf.length)) {
                    compareTo = this.zzaf[i] - zzi.zzaf[i];
                    if (compareTo != 0) {
                        return compareTo;
                    }
                    i++;
                }
                return compare(this.zzaf.length, zzi.zzaf.length);
            default:
                throw new AssertionError("Invalid enum value: " + this.zzag);
        }
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzi)) {
            return false;
        }
        zzi zzi = (zzi) obj;
        if (!zzn.equals(this.name, zzi.name) || this.zzag != zzi.zzag || this.zzah != zzi.zzah) {
            return false;
        }
        switch (this.zzag) {
            case 1:
                return this.zzab == zzi.zzab;
            case 2:
                return this.zzac == zzi.zzac;
            case 3:
                return this.zzad == zzi.zzad;
            case 4:
                return zzn.equals(this.zzae, zzi.zzae);
            case 5:
                return Arrays.equals(this.zzaf, zzi.zzaf);
            default:
                throw new AssertionError("Invalid enum value: " + this.zzag);
        }
    }

    public final String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Flag(");
        stringBuilder.append(this.name);
        stringBuilder.append(", ");
        switch (this.zzag) {
            case 1:
                stringBuilder.append(this.zzab);
                break;
            case 2:
                stringBuilder.append(this.zzac);
                break;
            case 3:
                stringBuilder.append(this.zzad);
                break;
            case 4:
                stringBuilder.append("'");
                stringBuilder.append(this.zzae);
                stringBuilder.append("'");
                break;
            case 5:
                if (this.zzaf != null) {
                    stringBuilder.append("'");
                    stringBuilder.append(Base64.encodeToString(this.zzaf, 3));
                    stringBuilder.append("'");
                    break;
                }
                stringBuilder.append("null");
                break;
            default:
                String str = this.name;
                throw new AssertionError(new StringBuilder(String.valueOf(str).length() + 27).append("Invalid type: ").append(str).append(", ").append(this.zzag).toString());
        }
        stringBuilder.append(", ");
        stringBuilder.append(this.zzag);
        stringBuilder.append(", ");
        stringBuilder.append(this.zzah);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public final void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 2, this.name, false);
        SafeParcelWriter.writeLong(parcel, 3, this.zzab);
        SafeParcelWriter.writeBoolean(parcel, 4, this.zzac);
        SafeParcelWriter.writeDouble(parcel, 5, this.zzad);
        SafeParcelWriter.writeString(parcel, 6, this.zzae, false);
        SafeParcelWriter.writeByteArray(parcel, 7, this.zzaf, false);
        SafeParcelWriter.writeInt(parcel, 8, this.zzag);
        SafeParcelWriter.writeInt(parcel, 9, this.zzah);
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }
}
