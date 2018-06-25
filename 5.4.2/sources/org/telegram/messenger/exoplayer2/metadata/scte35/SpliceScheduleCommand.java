package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class SpliceScheduleCommand extends SpliceCommand {
    public static final Creator<SpliceScheduleCommand> CREATOR = new C35111();
    public final List<Event> events;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.scte35.SpliceScheduleCommand$1 */
    static class C35111 implements Creator<SpliceScheduleCommand> {
        C35111() {
        }

        public SpliceScheduleCommand createFromParcel(Parcel parcel) {
            return new SpliceScheduleCommand(parcel);
        }

        public SpliceScheduleCommand[] newArray(int i) {
            return new SpliceScheduleCommand[i];
        }
    }

    public static final class ComponentSplice {
        public final int componentTag;
        public final long utcSpliceTime;

        private ComponentSplice(int i, long j) {
            this.componentTag = i;
            this.utcSpliceTime = j;
        }

        private static ComponentSplice createFromParcel(Parcel parcel) {
            return new ComponentSplice(parcel.readInt(), parcel.readLong());
        }

        private void writeToParcel(Parcel parcel) {
            parcel.writeInt(this.componentTag);
            parcel.writeLong(this.utcSpliceTime);
        }
    }

    public static final class Event {
        public final boolean autoReturn;
        public final int availNum;
        public final int availsExpected;
        public final long breakDuration;
        public final List<ComponentSplice> componentSpliceList;
        public final boolean outOfNetworkIndicator;
        public final boolean programSpliceFlag;
        public final boolean spliceEventCancelIndicator;
        public final long spliceEventId;
        public final int uniqueProgramId;
        public final long utcSpliceTime;

        private Event(long j, boolean z, boolean z2, boolean z3, List<ComponentSplice> list, long j2, boolean z4, long j3, int i, int i2, int i3) {
            this.spliceEventId = j;
            this.spliceEventCancelIndicator = z;
            this.outOfNetworkIndicator = z2;
            this.programSpliceFlag = z3;
            this.componentSpliceList = Collections.unmodifiableList(list);
            this.utcSpliceTime = j2;
            this.autoReturn = z4;
            this.breakDuration = j3;
            this.uniqueProgramId = i;
            this.availNum = i2;
            this.availsExpected = i3;
        }

        private Event(Parcel parcel) {
            boolean z = true;
            this.spliceEventId = parcel.readLong();
            this.spliceEventCancelIndicator = parcel.readByte() == (byte) 1;
            this.outOfNetworkIndicator = parcel.readByte() == (byte) 1;
            this.programSpliceFlag = parcel.readByte() == (byte) 1;
            int readInt = parcel.readInt();
            List arrayList = new ArrayList(readInt);
            for (int i = 0; i < readInt; i++) {
                arrayList.add(ComponentSplice.createFromParcel(parcel));
            }
            this.componentSpliceList = Collections.unmodifiableList(arrayList);
            this.utcSpliceTime = parcel.readLong();
            if (parcel.readByte() != (byte) 1) {
                z = false;
            }
            this.autoReturn = z;
            this.breakDuration = parcel.readLong();
            this.uniqueProgramId = parcel.readInt();
            this.availNum = parcel.readInt();
            this.availsExpected = parcel.readInt();
        }

        private static Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        private static Event parseFromSection(ParsableByteArray parsableByteArray) {
            long j;
            boolean z;
            long readUnsignedInt = parsableByteArray.readUnsignedInt();
            boolean z2 = (parsableByteArray.readUnsignedByte() & 128) != 0;
            boolean z3 = false;
            long j2 = C3446C.TIME_UNSET;
            List arrayList = new ArrayList();
            int i = 0;
            int i2 = 0;
            int i3 = 0;
            boolean z4 = false;
            long j3 = C3446C.TIME_UNSET;
            if (z2) {
                j = C3446C.TIME_UNSET;
                z4 = false;
                z = false;
            } else {
                ArrayList arrayList2;
                int readUnsignedByte = parsableByteArray.readUnsignedByte();
                boolean z5 = (readUnsignedByte & 128) != 0;
                boolean z6 = (readUnsignedByte & 64) != 0;
                Object obj = (readUnsignedByte & 32) != 0 ? 1 : null;
                long readUnsignedInt2 = z6 ? parsableByteArray.readUnsignedInt() : C3446C.TIME_UNSET;
                if (z6) {
                    arrayList2 = arrayList;
                } else {
                    int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
                    arrayList2 = new ArrayList(readUnsignedByte2);
                    for (readUnsignedByte = 0; readUnsignedByte < readUnsignedByte2; readUnsignedByte++) {
                        arrayList2.add(new ComponentSplice(parsableByteArray.readUnsignedByte(), parsableByteArray.readUnsignedInt()));
                    }
                }
                if (obj != null) {
                    long readUnsignedByte3 = (long) parsableByteArray.readUnsignedByte();
                    z4 = (128 & readUnsignedByte3) != 0;
                    j3 = ((readUnsignedByte3 & 1) << 32) | parsableByteArray.readUnsignedInt();
                }
                i = parsableByteArray.readUnsignedShort();
                i2 = parsableByteArray.readUnsignedByte();
                i3 = parsableByteArray.readUnsignedByte();
                arrayList = arrayList2;
                z3 = z6;
                j2 = readUnsignedInt2;
                j = j3;
                z = z4;
                z4 = z5;
            }
            return new Event(readUnsignedInt, z2, z4, z3, arrayList, j2, z, j, i, i2, i3);
        }

        private void writeToParcel(Parcel parcel) {
            int i = 1;
            parcel.writeLong(this.spliceEventId);
            parcel.writeByte((byte) (this.spliceEventCancelIndicator ? 1 : 0));
            parcel.writeByte((byte) (this.outOfNetworkIndicator ? 1 : 0));
            parcel.writeByte((byte) (this.programSpliceFlag ? 1 : 0));
            int size = this.componentSpliceList.size();
            parcel.writeInt(size);
            for (int i2 = 0; i2 < size; i2++) {
                ((ComponentSplice) this.componentSpliceList.get(i2)).writeToParcel(parcel);
            }
            parcel.writeLong(this.utcSpliceTime);
            if (!this.autoReturn) {
                i = 0;
            }
            parcel.writeByte((byte) i);
            parcel.writeLong(this.breakDuration);
            parcel.writeInt(this.uniqueProgramId);
            parcel.writeInt(this.availNum);
            parcel.writeInt(this.availsExpected);
        }
    }

    private SpliceScheduleCommand(Parcel parcel) {
        int readInt = parcel.readInt();
        List arrayList = new ArrayList(readInt);
        for (int i = 0; i < readInt; i++) {
            arrayList.add(Event.createFromParcel(parcel));
        }
        this.events = Collections.unmodifiableList(arrayList);
    }

    private SpliceScheduleCommand(List<Event> list) {
        this.events = Collections.unmodifiableList(list);
    }

    static SpliceScheduleCommand parseFromSection(ParsableByteArray parsableByteArray) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        List arrayList = new ArrayList(readUnsignedByte);
        for (int i = 0; i < readUnsignedByte; i++) {
            arrayList.add(Event.parseFromSection(parsableByteArray));
        }
        return new SpliceScheduleCommand(arrayList);
    }

    public void writeToParcel(Parcel parcel, int i) {
        int size = this.events.size();
        parcel.writeInt(size);
        for (int i2 = 0; i2 < size; i2++) {
            ((Event) this.events.get(i2)).writeToParcel(parcel);
        }
    }
}
