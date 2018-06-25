package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;

public final class SpliceInsertCommand extends SpliceCommand {
    public static final Creator<SpliceInsertCommand> CREATOR = new C17351();
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
    static class C17351 implements Creator<SpliceInsertCommand> {
        C17351() {
        }

        public SpliceInsertCommand createFromParcel(Parcel in) {
            return new SpliceInsertCommand(in);
        }

        public SpliceInsertCommand[] newArray(int size) {
            return new SpliceInsertCommand[size];
        }
    }

    public static final class ComponentSplice {
        public final long componentSplicePlaybackPositionUs;
        public final long componentSplicePts;
        public final int componentTag;

        private ComponentSplice(int componentTag, long componentSplicePts, long componentSplicePlaybackPositionUs) {
            this.componentTag = componentTag;
            this.componentSplicePts = componentSplicePts;
            this.componentSplicePlaybackPositionUs = componentSplicePlaybackPositionUs;
        }

        public void writeToParcel(Parcel dest) {
            dest.writeInt(this.componentTag);
            dest.writeLong(this.componentSplicePts);
            dest.writeLong(this.componentSplicePlaybackPositionUs);
        }

        public static ComponentSplice createFromParcel(Parcel in) {
            return new ComponentSplice(in.readInt(), in.readLong(), in.readLong());
        }
    }

    private SpliceInsertCommand(long spliceEventId, boolean spliceEventCancelIndicator, boolean outOfNetworkIndicator, boolean programSpliceFlag, boolean spliceImmediateFlag, long programSplicePts, long programSplicePlaybackPositionUs, List<ComponentSplice> componentSpliceList, boolean autoReturn, long breakDuration, int uniqueProgramId, int availNum, int availsExpected) {
        this.spliceEventId = spliceEventId;
        this.spliceEventCancelIndicator = spliceEventCancelIndicator;
        this.outOfNetworkIndicator = outOfNetworkIndicator;
        this.programSpliceFlag = programSpliceFlag;
        this.spliceImmediateFlag = spliceImmediateFlag;
        this.programSplicePts = programSplicePts;
        this.programSplicePlaybackPositionUs = programSplicePlaybackPositionUs;
        this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
        this.autoReturn = autoReturn;
        this.breakDuration = breakDuration;
        this.uniqueProgramId = uniqueProgramId;
        this.availNum = availNum;
        this.availsExpected = availsExpected;
    }

    private SpliceInsertCommand(Parcel in) {
        boolean z;
        boolean z2 = true;
        this.spliceEventId = in.readLong();
        if (in.readByte() == (byte) 1) {
            z = true;
        } else {
            z = false;
        }
        this.spliceEventCancelIndicator = z;
        if (in.readByte() == (byte) 1) {
            z = true;
        } else {
            z = false;
        }
        this.outOfNetworkIndicator = z;
        if (in.readByte() == (byte) 1) {
            z = true;
        } else {
            z = false;
        }
        this.programSpliceFlag = z;
        if (in.readByte() == (byte) 1) {
            z = true;
        } else {
            z = false;
        }
        this.spliceImmediateFlag = z;
        this.programSplicePts = in.readLong();
        this.programSplicePlaybackPositionUs = in.readLong();
        int componentSpliceListSize = in.readInt();
        List<ComponentSplice> componentSpliceList = new ArrayList(componentSpliceListSize);
        for (int i = 0; i < componentSpliceListSize; i++) {
            componentSpliceList.add(ComponentSplice.createFromParcel(in));
        }
        this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
        if (in.readByte() != (byte) 1) {
            z2 = false;
        }
        this.autoReturn = z2;
        this.breakDuration = in.readLong();
        this.uniqueProgramId = in.readInt();
        this.availNum = in.readInt();
        this.availsExpected = in.readInt();
    }

    static SpliceInsertCommand parseFromSection(ParsableByteArray sectionData, long ptsAdjustment, TimestampAdjuster timestampAdjuster) {
        long spliceEventId = sectionData.readUnsignedInt();
        boolean spliceEventCancelIndicator = (sectionData.readUnsignedByte() & 128) != 0;
        boolean outOfNetworkIndicator = false;
        boolean programSpliceFlag = false;
        boolean spliceImmediateFlag = false;
        long programSplicePts = C0907C.TIME_UNSET;
        List<ComponentSplice> componentSplices = Collections.emptyList();
        int uniqueProgramId = 0;
        int availNum = 0;
        int availsExpected = 0;
        boolean autoReturn = false;
        long duration = C0907C.TIME_UNSET;
        if (!spliceEventCancelIndicator) {
            int headerByte = sectionData.readUnsignedByte();
            outOfNetworkIndicator = (headerByte & 128) != 0;
            programSpliceFlag = (headerByte & 64) != 0;
            boolean durationFlag = (headerByte & 32) != 0;
            spliceImmediateFlag = (headerByte & 16) != 0;
            if (programSpliceFlag && !spliceImmediateFlag) {
                programSplicePts = TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment);
            }
            if (!programSpliceFlag) {
                int componentCount = sectionData.readUnsignedByte();
                List<ComponentSplice> arrayList = new ArrayList(componentCount);
                for (int i = 0; i < componentCount; i++) {
                    int componentTag = sectionData.readUnsignedByte();
                    long componentSplicePts = C0907C.TIME_UNSET;
                    if (!spliceImmediateFlag) {
                        componentSplicePts = TimeSignalCommand.parseSpliceTime(sectionData, ptsAdjustment);
                    }
                    arrayList.add(new ComponentSplice(componentTag, componentSplicePts, timestampAdjuster.adjustTsTimestamp(componentSplicePts)));
                }
            }
            if (durationFlag) {
                long firstByte = (long) sectionData.readUnsignedByte();
                autoReturn = (128 & firstByte) != 0;
                duration = ((1 & firstByte) << 32) | sectionData.readUnsignedInt();
            }
            uniqueProgramId = sectionData.readUnsignedShort();
            availNum = sectionData.readUnsignedByte();
            availsExpected = sectionData.readUnsignedByte();
        }
        return new SpliceInsertCommand(spliceEventId, spliceEventCancelIndicator, outOfNetworkIndicator, programSpliceFlag, spliceImmediateFlag, programSplicePts, timestampAdjuster.adjustTsTimestamp(programSplicePts), componentSplices, autoReturn, duration, uniqueProgramId, availNum, availsExpected);
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        int i2 = 1;
        dest.writeLong(this.spliceEventId);
        if (this.spliceEventCancelIndicator) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.outOfNetworkIndicator) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.programSpliceFlag) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        if (this.spliceImmediateFlag) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeLong(this.programSplicePts);
        dest.writeLong(this.programSplicePlaybackPositionUs);
        int componentSpliceListSize = this.componentSpliceList.size();
        dest.writeInt(componentSpliceListSize);
        for (int i3 = 0; i3 < componentSpliceListSize; i3++) {
            ((ComponentSplice) this.componentSpliceList.get(i3)).writeToParcel(dest);
        }
        if (!this.autoReturn) {
            i2 = 0;
        }
        dest.writeByte((byte) i2);
        dest.writeLong(this.breakDuration);
        dest.writeInt(this.uniqueProgramId);
        dest.writeInt(this.availNum);
        dest.writeInt(this.availsExpected);
    }
}
