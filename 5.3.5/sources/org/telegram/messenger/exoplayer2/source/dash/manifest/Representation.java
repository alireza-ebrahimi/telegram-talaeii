package org.telegram.messenger.exoplayer2.source.dash.manifest;

import android.net.Uri;
import java.util.Collections;
import java.util.List;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.dash.DashSegmentIndex;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.MultiSegmentBase;
import org.telegram.messenger.exoplayer2.source.dash.manifest.SegmentBase.SingleSegmentBase;

public abstract class Representation {
    public static final long REVISION_ID_DEFAULT = -1;
    public final String baseUrl;
    public final String contentId;
    public final Format format;
    public final List<Descriptor> inbandEventStreams;
    private final RangedUri initializationUri;
    public final long presentationTimeOffsetUs;
    public final long revisionId;

    public static class MultiSegmentRepresentation extends Representation implements DashSegmentIndex {
        private final MultiSegmentBase segmentBase;

        public MultiSegmentRepresentation(String contentId, long revisionId, Format format, String baseUrl, MultiSegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
            super(contentId, revisionId, format, baseUrl, segmentBase, inbandEventStreams);
            this.segmentBase = segmentBase;
        }

        public RangedUri getIndexUri() {
            return null;
        }

        public DashSegmentIndex getIndex() {
            return this;
        }

        public String getCacheKey() {
            return null;
        }

        public RangedUri getSegmentUrl(int segmentIndex) {
            return this.segmentBase.getSegmentUrl(this, segmentIndex);
        }

        public int getSegmentNum(long timeUs, long periodDurationUs) {
            return this.segmentBase.getSegmentNum(timeUs, periodDurationUs);
        }

        public long getTimeUs(int segmentIndex) {
            return this.segmentBase.getSegmentTimeUs(segmentIndex);
        }

        public long getDurationUs(int segmentIndex, long periodDurationUs) {
            return this.segmentBase.getSegmentDurationUs(segmentIndex, periodDurationUs);
        }

        public int getFirstSegmentNum() {
            return this.segmentBase.getFirstSegmentNum();
        }

        public int getSegmentCount(long periodDurationUs) {
            return this.segmentBase.getSegmentCount(periodDurationUs);
        }

        public boolean isExplicit() {
            return this.segmentBase.isExplicit();
        }
    }

    public static class SingleSegmentRepresentation extends Representation {
        private final String cacheKey;
        public final long contentLength;
        private final RangedUri indexUri;
        private final SingleSegmentIndex segmentIndex;
        public final Uri uri;

        public static SingleSegmentRepresentation newInstance(String contentId, long revisionId, Format format, String uri, long initializationStart, long initializationEnd, long indexStart, long indexEnd, List<Descriptor> inbandEventStreams, String customCacheKey, long contentLength) {
            long j = 1 + (indexEnd - indexStart);
            return new SingleSegmentRepresentation(contentId, revisionId, format, uri, new SingleSegmentBase(new RangedUri(null, initializationStart, (initializationEnd - initializationStart) + 1), 1, 0, indexStart, j), inbandEventStreams, customCacheKey, contentLength);
        }

        public SingleSegmentRepresentation(String contentId, long revisionId, Format format, String baseUrl, SingleSegmentBase segmentBase, List<Descriptor> inbandEventStreams, String customCacheKey, long contentLength) {
            super(contentId, revisionId, format, baseUrl, segmentBase, inbandEventStreams);
            this.uri = Uri.parse(baseUrl);
            this.indexUri = segmentBase.getIndex();
            if (customCacheKey == null) {
                customCacheKey = contentId != null ? contentId + "." + format.id + "." + revisionId : null;
            }
            this.cacheKey = customCacheKey;
            this.contentLength = contentLength;
            this.segmentIndex = this.indexUri != null ? null : new SingleSegmentIndex(new RangedUri(null, 0, contentLength));
        }

        public RangedUri getIndexUri() {
            return this.indexUri;
        }

        public DashSegmentIndex getIndex() {
            return this.segmentIndex;
        }

        public String getCacheKey() {
            return this.cacheKey;
        }
    }

    public abstract String getCacheKey();

    public abstract DashSegmentIndex getIndex();

    public abstract RangedUri getIndexUri();

    public static Representation newInstance(String contentId, long revisionId, Format format, String baseUrl, SegmentBase segmentBase) {
        return newInstance(contentId, revisionId, format, baseUrl, segmentBase, null);
    }

    public static Representation newInstance(String contentId, long revisionId, Format format, String baseUrl, SegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
        return newInstance(contentId, revisionId, format, baseUrl, segmentBase, inbandEventStreams, null);
    }

    public static Representation newInstance(String contentId, long revisionId, Format format, String baseUrl, SegmentBase segmentBase, List<Descriptor> inbandEventStreams, String customCacheKey) {
        if (segmentBase instanceof SingleSegmentBase) {
            return new SingleSegmentRepresentation(contentId, revisionId, format, baseUrl, (SingleSegmentBase) segmentBase, inbandEventStreams, customCacheKey, -1);
        } else if (segmentBase instanceof MultiSegmentBase) {
            return new MultiSegmentRepresentation(contentId, revisionId, format, baseUrl, (MultiSegmentBase) segmentBase, inbandEventStreams);
        } else {
            throw new IllegalArgumentException("segmentBase must be of type SingleSegmentBase or MultiSegmentBase");
        }
    }

    private Representation(String contentId, long revisionId, Format format, String baseUrl, SegmentBase segmentBase, List<Descriptor> inbandEventStreams) {
        List emptyList;
        this.contentId = contentId;
        this.revisionId = revisionId;
        this.format = format;
        this.baseUrl = baseUrl;
        if (inbandEventStreams == null) {
            emptyList = Collections.emptyList();
        } else {
            emptyList = Collections.unmodifiableList(inbandEventStreams);
        }
        this.inbandEventStreams = emptyList;
        this.initializationUri = segmentBase.getInitialization(this);
        this.presentationTimeOffsetUs = segmentBase.getPresentationTimeOffsetUs();
    }

    public RangedUri getInitializationUri() {
        return this.initializationUri;
    }
}
