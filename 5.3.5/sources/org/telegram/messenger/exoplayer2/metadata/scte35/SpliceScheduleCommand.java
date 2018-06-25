package org.telegram.messenger.exoplayer2.metadata.scte35;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

public final class SpliceScheduleCommand extends SpliceCommand {
    public static final Creator<SpliceScheduleCommand> CREATOR = new C17371();
    public final List<Event> events;

    /* renamed from: org.telegram.messenger.exoplayer2.metadata.scte35.SpliceScheduleCommand$1 */
    static class C17371 implements Creator<SpliceScheduleCommand> {
        C17371() {
        }

        public SpliceScheduleCommand createFromParcel(Parcel in) {
            return new SpliceScheduleCommand(in);
        }

        public SpliceScheduleCommand[] newArray(int size) {
            return new SpliceScheduleCommand[size];
        }
    }

    public static final class ComponentSplice {
        public final int componentTag;
        public final long utcSpliceTime;

        private ComponentSplice(int componentTag, long utcSpliceTime) {
            this.componentTag = componentTag;
            this.utcSpliceTime = utcSpliceTime;
        }

        private static ComponentSplice createFromParcel(Parcel in) {
            return new ComponentSplice(in.readInt(), in.readLong());
        }

        private void writeToParcel(Parcel dest) {
            dest.writeInt(this.componentTag);
            dest.writeLong(this.utcSpliceTime);
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

        private Event(long spliceEventId, boolean spliceEventCancelIndicator, boolean outOfNetworkIndicator, boolean programSpliceFlag, List<ComponentSplice> componentSpliceList, long utcSpliceTime, boolean autoReturn, long breakDuration, int uniqueProgramId, int availNum, int availsExpected) {
            this.spliceEventId = spliceEventId;
            this.spliceEventCancelIndicator = spliceEventCancelIndicator;
            this.outOfNetworkIndicator = outOfNetworkIndicator;
            this.programSpliceFlag = programSpliceFlag;
            this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
            this.utcSpliceTime = utcSpliceTime;
            this.autoReturn = autoReturn;
            this.breakDuration = breakDuration;
            this.uniqueProgramId = uniqueProgramId;
            this.availNum = availNum;
            this.availsExpected = availsExpected;
        }

        private Event(Parcel in) {
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
            int componentSpliceListLength = in.readInt();
            ArrayList<ComponentSplice> componentSpliceList = new ArrayList(componentSpliceListLength);
            for (int i = 0; i < componentSpliceListLength; i++) {
                componentSpliceList.add(ComponentSplice.createFromParcel(in));
            }
            this.componentSpliceList = Collections.unmodifiableList(componentSpliceList);
            this.utcSpliceTime = in.readLong();
            if (in.readByte() != (byte) 1) {
                z2 = false;
            }
            this.autoReturn = z2;
            this.breakDuration = in.readLong();
            this.uniqueProgramId = in.readInt();
            this.availNum = in.readInt();
            this.availsExpected = in.readInt();
        }

        private static Event parseFromSection(ParsableByteArray sectionData) {
            long spliceEventId = sectionData.readUnsignedInt();
            boolean spliceEventCancelIndicator = (sectionData.readUnsignedByte() & 128) != 0;
            boolean outOfNetworkIndicator = false;
            boolean programSpliceFlag = false;
            long utcSpliceTime = C0907C.TIME_UNSET;
            ArrayList<ComponentSplice> componentSplices = new ArrayList();
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
                if (programSpliceFlag) {
                    utcSpliceTime = sectionData.readUnsignedInt();
                }
                if (!programSpliceFlag) {
                    int componentCount = sectionData.readUnsignedByte();
                    componentSplices = new ArrayList(componentCount);
                    for (int i = 0; i < componentCount; i++) {
                        componentSplices.add(new ComponentSplice(sectionData.readUnsignedByte(), sectionData.readUnsignedInt()));
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
            return new Event(spliceEventId, spliceEventCancelIndicator, outOfNetworkIndicator, programSpliceFlag, componentSplices, utcSpliceTime, autoReturn, duration, uniqueProgramId, availNum, availsExpected);
        }

        private void writeToParcel(Parcel dest) {
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
            int componentSpliceListSize = this.componentSpliceList.size();
            dest.writeInt(componentSpliceListSize);
            for (int i3 = 0; i3 < componentSpliceListSize; i3++) {
                ((ComponentSplice) this.componentSpliceList.get(i3)).writeToParcel(dest);
            }
            dest.writeLong(this.utcSpliceTime);
            if (!this.autoReturn) {
                i2 = 0;
            }
            dest.writeByte((byte) i2);
            dest.writeLong(this.breakDuration);
            dest.writeInt(this.uniqueProgramId);
            dest.writeInt(this.availNum);
            dest.writeInt(this.availsExpected);
        }

        private static Event createFromParcel(Parcel in) {
            return new Event(in);
        }
    }

    private SpliceScheduleCommand(List<Event> events) {
        this.events = Collections.unmodifiableList(events);
    }

    private SpliceScheduleCommand(Parcel in) {
        int eventsSize = in.readInt();
        ArrayList<Event> events = new ArrayList(eventsSize);
        for (int i = 0; i < eventsSize; i++) {
            events.add(Event.createFromParcel(in));
        }
        this.events = Collections.unmodifiableList(events);
    }

    static SpliceScheduleCommand parseFromSection(ParsableByteArray sectionData) {
        int spliceCount = sectionData.readUnsignedByte();
        List events = new ArrayList(spliceCount);
        for (int i = 0; i < spliceCount; i++) {
            events.add(Event.parseFromSection(sectionData));
        }
        return new SpliceScheduleCommand(events);
    }

    public void writeToParcel(Parcel dest, int flags) {
        int eventsSize = this.events.size();
        dest.writeInt(eventsSize);
        for (int i = 0; i < eventsSize; i++) {
            ((Event) this.events.get(i)).writeToParcel(dest);
        }
    }
}
