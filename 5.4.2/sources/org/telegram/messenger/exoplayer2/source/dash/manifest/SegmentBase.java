package org.telegram.messenger.exoplayer2.source.dash.manifest;

import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.util.Util;

public abstract class SegmentBase {
    final RangedUri initialization;
    final long presentationTimeOffset;
    final long timescale;

    public static abstract class MultiSegmentBase extends SegmentBase {
        final long duration;
        final List<SegmentTimelineElement> segmentTimeline;
        final int startNumber;

        public MultiSegmentBase(RangedUri rangedUri, long j, long j2, int i, long j3, List<SegmentTimelineElement> list) {
            super(rangedUri, j, j2);
            this.startNumber = i;
            this.duration = j3;
            this.segmentTimeline = list;
        }

        public int getFirstSegmentNum() {
            return this.startNumber;
        }

        public abstract int getSegmentCount(long j);

        public final long getSegmentDurationUs(int i, long j) {
            if (this.segmentTimeline != null) {
                return (((SegmentTimelineElement) this.segmentTimeline.get(i - this.startNumber)).duration * C3446C.MICROS_PER_SECOND) / this.timescale;
            }
            int segmentCount = getSegmentCount(j);
            return (segmentCount == -1 || i != (segmentCount + getFirstSegmentNum()) - 1) ? (this.duration * C3446C.MICROS_PER_SECOND) / this.timescale : j - getSegmentTimeUs(i);
        }

        public int getSegmentNum(long j, long j2) {
            int firstSegmentNum = getFirstSegmentNum();
            int segmentCount = getSegmentCount(j2);
            if (segmentCount == 0) {
                return firstSegmentNum;
            }
            int i;
            if (this.segmentTimeline == null) {
                i = this.startNumber + ((int) (j / ((this.duration * C3446C.MICROS_PER_SECOND) / this.timescale)));
                return i >= firstSegmentNum ? segmentCount == -1 ? i : Math.min(i, (firstSegmentNum + segmentCount) - 1) : firstSegmentNum;
            } else {
                i = (firstSegmentNum + segmentCount) - 1;
                segmentCount = firstSegmentNum;
                while (segmentCount <= i) {
                    int i2 = ((i - segmentCount) / 2) + segmentCount;
                    long segmentTimeUs = getSegmentTimeUs(i2);
                    if (segmentTimeUs < j) {
                        segmentCount = i2 + 1;
                    } else if (segmentTimeUs <= j) {
                        return i2;
                    } else {
                        i = i2 - 1;
                    }
                }
                if (segmentCount != firstSegmentNum) {
                    segmentCount = i;
                }
                return segmentCount;
            }
        }

        public final long getSegmentTimeUs(int i) {
            return Util.scaleLargeTimestamp(this.segmentTimeline != null ? ((SegmentTimelineElement) this.segmentTimeline.get(i - this.startNumber)).startTime - this.presentationTimeOffset : ((long) (i - this.startNumber)) * this.duration, C3446C.MICROS_PER_SECOND, this.timescale);
        }

        public abstract RangedUri getSegmentUrl(Representation representation, int i);

        public boolean isExplicit() {
            return this.segmentTimeline != null;
        }
    }

    public static class SegmentList extends MultiSegmentBase {
        final List<RangedUri> mediaSegments;

        public SegmentList(RangedUri rangedUri, long j, long j2, int i, long j3, List<SegmentTimelineElement> list, List<RangedUri> list2) {
            super(rangedUri, j, j2, i, j3, list);
            this.mediaSegments = list2;
        }

        public int getSegmentCount(long j) {
            return this.mediaSegments.size();
        }

        public RangedUri getSegmentUrl(Representation representation, int i) {
            return (RangedUri) this.mediaSegments.get(i - this.startNumber);
        }

        public boolean isExplicit() {
            return true;
        }
    }

    public static class SegmentTemplate extends MultiSegmentBase {
        final UrlTemplate initializationTemplate;
        final UrlTemplate mediaTemplate;

        public SegmentTemplate(RangedUri rangedUri, long j, long j2, int i, long j3, List<SegmentTimelineElement> list, UrlTemplate urlTemplate, UrlTemplate urlTemplate2) {
            super(rangedUri, j, j2, i, j3, list);
            this.initializationTemplate = urlTemplate;
            this.mediaTemplate = urlTemplate2;
        }

        public RangedUri getInitialization(Representation representation) {
            return this.initializationTemplate != null ? new RangedUri(this.initializationTemplate.buildUri(representation.format.id, 0, representation.format.bitrate, 0), 0, -1) : super.getInitialization(representation);
        }

        public int getSegmentCount(long j) {
            return this.segmentTimeline != null ? this.segmentTimeline.size() : j != C3446C.TIME_UNSET ? (int) Util.ceilDivide(j, (this.duration * C3446C.MICROS_PER_SECOND) / this.timescale) : -1;
        }

        public RangedUri getSegmentUrl(Representation representation, int i) {
            return new RangedUri(this.mediaTemplate.buildUri(representation.format.id, i, representation.format.bitrate, this.segmentTimeline != null ? ((SegmentTimelineElement) this.segmentTimeline.get(i - this.startNumber)).startTime : ((long) (i - this.startNumber)) * this.duration), 0, -1);
        }
    }

    public static class SegmentTimelineElement {
        final long duration;
        final long startTime;

        public SegmentTimelineElement(long j, long j2) {
            this.startTime = j;
            this.duration = j2;
        }
    }

    public static class SingleSegmentBase extends SegmentBase {
        final long indexLength;
        final long indexStart;

        public SingleSegmentBase() {
            this(null, 1, 0, 0, 0);
        }

        public SingleSegmentBase(RangedUri rangedUri, long j, long j2, long j3, long j4) {
            super(rangedUri, j, j2);
            this.indexStart = j3;
            this.indexLength = j4;
        }

        public RangedUri getIndex() {
            return this.indexLength <= 0 ? null : new RangedUri(null, this.indexStart, this.indexLength);
        }
    }

    public SegmentBase(RangedUri rangedUri, long j, long j2) {
        this.initialization = rangedUri;
        this.timescale = j;
        this.presentationTimeOffset = j2;
    }

    public RangedUri getInitialization(Representation representation) {
        return this.initialization;
    }

    public long getPresentationTimeOffsetUs() {
        return Util.scaleLargeTimestamp(this.presentationTimeOffset, C3446C.MICROS_PER_SECOND, this.timescale);
    }
}
