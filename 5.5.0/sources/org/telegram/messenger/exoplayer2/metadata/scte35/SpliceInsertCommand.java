package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public final class SpliceInsertCommand extends SpliceCommand {
    public static final Creator<SpliceInsertCommand> CREATOR = new C35091();
    public final boolean autoReturn;
    public final int availNum;
    public final int availsExpected;
    public final long breakDuration;
    public final List<ComponentSplice> componentSpliceList;
    public final boolean outOfNetworkIndicator;
    public final boolean programSpliceFlag;
    public final long programSplicePlaybackPositionUs;
    public final long programSplicePts;
    public final boolean spliceEventCancelIndicator;
    public final long spliceEventId;
    public final boolean spliceImmediateFlag;
    public final int uniqueProgramId;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.scte35.SpliceInsertCommand$1 */
    static class C35091 implements Creator<SpliceInsertCommand> {
        C35091() {
        }

        public SpliceInsertCommand createFromParcel(Parcel parcel) {
            return new SpliceInsertCommand(parcel);
        }

        public SpliceInsertCommand[] newArray(int i) {
            return new SpliceInsertCommand[i];
        }
    }

    public static final class ComponentSplice {
        public final long componentSplicePlaybackPositionUs;
        public final long componentSplicePts;
        public final int componentTag;

        private ComponentSplice(int i, long j, long j2) {
            this.componentTag = i;
            this.componentSplicePts = j;
            this.componentSplicePlaybackPositionUs = j2;
        }

        public static ComponentSplice createFromParcel(Parcel parcel) {
            return new ComponentSplice(parcel.readInt(), parcel.readLong(), parcel.readLong());
        }

        public void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.componentTag);
            parcel.writeLong(this.componentSplicePts);
            parcel.writeLong(this.componentSplicePlaybackPositionUs);
        }
    }

    private SpliceInsertCommand(long j, boolean z, boolean z2, boolean z3, boolean z4, long j2, long j3, List<ComponentSplice> list, boolean z5, long j4, int i, int i2, int i3) {
        this.spliceEventId = j;
        this.spliceEventCancelIndicator = z;
        this.outOfNetworkIndicator = z2;
        this.programSpliceFlag = z3;
        this.spliceImmediateFlag = z4;
        this.programSplicePts = j2;
        this.programSplicePlaybackPositionUs = j3;
        this.componentSpliceList = Collections.unmodifiableList(list);
        this.autoReturn = z5;
        this.breakDuration = j4;
        this.uniqueProgramId = i;
        this.availNum = i2;
        this.availsExpected = i3;
    }

    private SpliceInsertCommand(Parcel parcel) {
        boolean z = true;
        this.spliceEventId = parcel.readLong();
        this.spliceEventCancelIndicator = parcel.readByte() == (byte) 1;
        this.outOfNetworkIndicator = parcel.readByte() == (byte) 1;
        this.programSpliceFlag = parcel.readByte() == (byte) 1;
        this.spliceImmediateFlag = parcel.readByte() == (byte) 1;
        this.programSplicePts = parcel.readLong();
        this.programSplicePlaybackPositionUs = parcel.readLong();
        int readInt = parcel.readInt();
        List arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(ComponentSplice.createFromParcel(parcel));
        }
        this.componentSpliceList = Collections.unmodifiableList(arrayList);
        if (parcel.readByte() != (byte) 1) {
            z = false;
        }
        this.autoReturn = z;
        this.breakDuration = parcel.readLong();
        this.uniqueProgramId = parcel.readInt();
        this.availNum = parcel.readInt();
        this.availsExpected = parcel.readInt();
    }

    static SpliceInsertCommand parseFromSection(ParsableByteArray parsableByteArray, long j, TimestampAdjuster timestampAdjuster) {
        long j2;
        boolean z;
        long j3;
        boolean z2;
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        boolean z3 = (parsableByteArray.readUnsignedByte() & 128) != 0;
        boolean z4 = false;
        boolean z5 = false;
        List emptyList = Collections.emptyList();
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        if (z3) {
            j2 = C3446C.TIME_UNSET;
            z = false;
            j3 = C3446C.TIME_UNSET;
            z2 = false;
        } else {
            long j4;
            boolean z6;
            long j5;
            int readUnsignedByte = parsableByteArray.readUnsignedByte();
            boolean z7 = (readUnsignedByte & 128) != 0;
            boolean z8 = (readUnsignedByte & 64) != 0;
            Object obj = (readUnsignedByte & 32) != 0 ? 1 : null;
            z = (readUnsignedByte & 16) != 0;
            long parseSpliceTime = (!z8 || z) ? -9223372036854775807L : TimeSignalCommand.parseSpliceTime(parsableByteArray, j);
            if (!z8) {
                i3 = parsableByteArray.readUnsignedByte();
                emptyList = new ArrayList(i3);
                for (i = 0; i < i3; i++) {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    j4 = C3446C.TIME_UNSET;
                    if (!z) {
                        j4 = TimeSignalCommand.parseSpliceTime(parsableByteArray, j);
                    }
                    emptyList.add(new ComponentSplice(readUnsignedByte2, j4, timestampAdjuster.adjustTsTimestamp(j4)));
                }
            }
            if (obj != null) {
                j4 = (long) parsableByteArray.readUnsignedByte();
                long readUnsignedInt2 = ((j4 & 1) << 32) | parsableByteArray.readUnsignedInt();
                z6 = (128 & j4) != 0;
                j5 = readUnsignedInt2;
            } else {
                j5 = -9223372036854775807L;
                z6 = false;
            }
            i = parsableByteArray.readUnsignedShort();
            i2 = parsableByteArray.readUnsignedByte();
            i3 = parsableByteArray.readUnsignedByte();
            j3 = parseSpliceTime;
            z2 = z;
            z5 = z8;
            z4 = z7;
            j2 = j5;
            z = z6;
        }
        return new SpliceInsertCommand(readUnsignedInt, z3, z4, z5, z2, j3, timestampAdjuster.adjustTsTimestamp(j3), emptyList, z, j2, i, i2, i3);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int i2 = 1;
        parcel.writeLong(this.spliceEventId);
        parcel.writeByte((byte) (this.spliceEventCancelIndicator ? 1 : 0));
        parcel.writeByte((byte) (this.outOfNetworkIndicator ? 1 : 0));
        parcel.writeByte((byte) (this.programSpliceFlag ? 1 : 0));
        parcel.writeByte((byte) (this.spliceImmediateFlag ? 1 : 0));
        parcel.writeLong(this.programSplicePts);
        parcel.writeLong(this.programSplicePlaybackPositionUs);
        int size = this.componentSpliceList.size();
        parcel.writeInt(size);
        for (int i3 = 0; i3 < size; i3++) {
            ((ComponentSplice) this.componentSpliceList.get(i3)).writeToParcel(parcel);
        }
        if (!this.autoReturn) {
            i2 = 0;
        }
        parcel.writeByte((byte) i2);
        parcel.writeLong(this.breakDuration);
        parcel.writeInt(this.uniqueProgramId);
        parcel.writeInt(this.availNum);
        parcel.writeInt(this.availsExpected);
    }
}
