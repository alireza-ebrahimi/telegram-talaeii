package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class PrivateCommand extends SpliceCommand {
    public static final Creator<PrivateCommand> CREATOR = new C35081();
    public final byte[] commandBytes;
    public final long identifier;
    public final long ptsAdjustment;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.scte35.PrivateCommand$1 */
    static class C35081 implements Creator<PrivateCommand> {
        C35081() {
        }

        public PrivateCommand createFromParcel(Parcel parcel) {
            return new PrivateCommand(parcel);
        }

        public PrivateCommand[] newArray(int i) {
            return new PrivateCommand[i];
        }
    }

    private PrivateCommand(long j, byte[] bArr, long j2) {
        this.ptsAdjustment = j2;
        this.identifier = j;
        this.commandBytes = bArr;
    }

    private PrivateCommand(Parcel parcel) {
        this.ptsAdjustment = parcel.readLong();
        this.identifier = parcel.readLong();
        this.commandBytes = new byte[parcel.readInt()];
        parcel.readByteArray(this.commandBytes);
    }

    static PrivateCommand parseFromSection(ParsableByteArray parsableByteArray, int i, long j) {
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        byte[] bArr = new byte[(i - 4)];
        parsableByteArray.readBytes(bArr, 0, bArr.length);
        return new PrivateCommand(readUnsignedInt, bArr, j);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeLong(this.ptsAdjustment);
        parcel.writeLong(this.identifier);
        parcel.writeInt(this.commandBytes.length);
        parcel.writeByteArray(this.commandBytes);
    }
}
