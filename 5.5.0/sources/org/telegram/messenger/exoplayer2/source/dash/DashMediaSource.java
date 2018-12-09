package org.telegram.messenger.exoplayer2.source.dash;

import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.util.SparseArray;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.DefaultRenderersFactory;
import org.telegram.messenger.exoplayer2.ExoPlayer;
import org.telegram.messenger.exoplayer2.ExoPlayerLibraryInfo;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.Timeline;
import org.telegram.messenger.exoplayer2.Timeline.Window;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.MediaSource;
import org.telegram.messenger.exoplayer2.source.MediaSource.Listener;
import org.telegram.messenger.exoplayer2.source.MediaSource.MediaPeriodId;
import org.telegram.messenger.exoplayer2.source.dash.DashChunkSource.Factory;
import org.telegram.messenger.exoplayer2.source.dash.manifest.AdaptationSet;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifest;
import org.telegram.messenger.exoplayer2.source.dash.manifest.DashManifestParser;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Period;
import org.telegram.messenger.exoplayer2.source.dash.manifest.Representation;
import org.telegram.messenger.exoplayer2.source.dash.manifest.UtcTimingElement;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.Loader;
import org.telegram.messenger.exoplayer2.upstream.Loader.Callback;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower;
import org.telegram.messenger.exoplayer2.upstream.LoaderErrorThrower.Dummy;
import org.telegram.messenger.exoplayer2.upstream.ParsingLoadable;
import org.telegram.messenger.exoplayer2.upstream.ParsingLoadable.Parser;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DashMediaSource implements MediaSource {
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_FIXED_MS = 30000;
    public static final long DEFAULT_LIVE_PRESENTATION_DELAY_PREFER_MANIFEST_MS = -1;
    public static final int DEFAULT_MIN_LOADABLE_RETRY_COUNT = 3;
    private static final long MIN_LIVE_DEFAULT_START_POSITION_US = 5000000;
    private static final int NOTIFY_MANIFEST_INTERVAL_MS = 5000;
    private static final String TAG = "DashMediaSource";
    private final Factory chunkSourceFactory;
    private DataSource dataSource;
    private long elapsedRealtimeOffsetMs;
    private final EventDispatcher eventDispatcher;
    private int firstPeriodId;
    private Handler handler;
    private final long livePresentationDelayMs;
    private Loader loader;
    private LoaderErrorThrower loaderErrorThrower;
    private DashManifest manifest;
    private final ManifestCallback manifestCallback;
    private final DataSource.Factory manifestDataSourceFactory;
    private long manifestLoadEndTimestamp;
    private long manifestLoadStartTimestamp;
    private final Parser<? extends DashManifest> manifestParser;
    private Uri manifestUri;
    private final Object manifestUriLock;
    private final int minLoadableRetryCount;
    private final SparseArray<DashMediaPeriod> periodsById;
    private final Runnable refreshManifestRunnable;
    private final boolean sideloadedManifest;
    private final Runnable simulateManifestRefreshRunnable;
    private Listener sourceListener;

    /* renamed from: org.telegram.messenger.exoplayer2.source.dash.DashMediaSource$1 */
    class C35271 implements Runnable {
        C35271() {
        }

        public void run() {
            DashMediaSource.this.startLoadingManifest();
        }
    }

    /* renamed from: org.telegram.messenger.exoplayer2.source.dash.DashMediaSource$2 */
    class C35282 implements Runnable {
        C35282() {
        }

        public void run() {
            DashMediaSource.this.processManifest(false);
        }
    }

    private static final class DashTimeline extends Timeline {
        private final int firstPeriodId;
        private final DashManifest manifest;
        private final long offsetInFirstPeriodUs;
        private final long presentationStartTimeMs;
        private final long windowDefaultStartPositionUs;
        private final long windowDurationUs;
        private final long windowStartTimeMs;

        public DashTimeline(long j, long j2, int i, long j3, long j4, long j5, DashManifest dashManifest) {
            this.presentationStartTimeMs = j;
            this.windowStartTimeMs = j2;
            this.firstPeriodId = i;
            this.offsetInFirstPeriodUs = j3;
            this.windowDurationUs = j4;
            this.windowDefaultStartPositionUs = j5;
            this.manifest = dashManifest;
        }

        private long getAdjustedWindowDefaultStartPositionUs(long j) {
            long j2 = this.windowDefaultStartPositionUs;
            if (!this.manifest.dynamic) {
                return j2;
            }
            if (j > 0) {
                j2 += j;
                if (j2 > this.windowDurationUs) {
                    return C3446C.TIME_UNSET;
                }
            }
            long j3 = j2;
            long j4 = this.offsetInFirstPeriodUs + j3;
            long periodDurationUs = this.manifest.getPeriodDurationUs(0);
            int i = 0;
            while (i < this.manifest.getPeriodCount() - 1 && j4 >= periodDurationUs) {
                periodDurationUs = j4 - periodDurationUs;
                int i2 = i + 1;
                long periodDurationUs2 = this.manifest.getPeriodDurationUs(i2);
                i = i2;
                j4 = periodDurationUs;
                periodDurationUs = periodDurationUs2;
            }
            Period period = this.manifest.getPeriod(i);
            int adaptationSetIndex = period.getAdaptationSetIndex(2);
            if (adaptationSetIndex == -1) {
                return j3;
            }
            DashSegmentIndex index = ((Representation) ((AdaptationSet) period.adaptationSets.get(adaptationSetIndex)).representations.get(0)).getIndex();
            return (index == null || index.getSegmentCount(periodDurationUs) == 0) ? j3 : (index.getTimeUs(index.getSegmentNum(j4, periodDurationUs)) + j3) - j4;
        }

        public int getIndexOfPeriod(Object obj) {
            if (!(obj instanceof Integer)) {
                return -1;
            }
            int intValue = ((Integer) obj).intValue();
            return (intValue < this.firstPeriodId || intValue >= this.firstPeriodId + getPeriodCount()) ? -1 : intValue - this.firstPeriodId;
        }

        public Timeline.Period getPeriod(int i, Timeline.Period period, boolean z) {
            Object obj = null;
            Assertions.checkIndex(i, 0, this.manifest.getPeriodCount());
            Object obj2 = z ? this.manifest.getPeriod(i).id : null;
            if (z) {
                obj = Integer.valueOf(this.firstPeriodId + Assertions.checkIndex(i, 0, this.manifest.getPeriodCount()));
            }
            return period.set(obj2, obj, 0, this.manifest.getPeriodDurationUs(i), C3446C.msToUs(this.manifest.getPeriod(i).startMs - this.manifest.getPeriod(0).startMs) - this.offsetInFirstPeriodUs);
        }

        public int getPeriodCount() {
            return this.manifest.getPeriodCount();
        }

        public Window getWindow(int i, Window window, boolean z, long j) {
            Assertions.checkIndex(i, 0, 1);
            return window.set(null, this.presentationStartTimeMs, this.windowStartTimeMs, true, this.manifest.dynamic, getAdjustedWindowDefaultStartPositionUs(j), this.windowDurationUs, 0, this.manifest.getPeriodCount() - 1, this.offsetInFirstPeriodUs);
        }

        public int getWindowCount() {
            return 1;
        }
    }

    private static final class Iso8601Parser implements Parser<Long> {
        private Iso8601Parser() {
        }

        public Long parse(Uri uri, InputStream inputStream) {
            String readLine = new BufferedReader(new InputStreamReader(inputStream)).readLine();
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
                simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                return Long.valueOf(simpleDateFormat.parse(readLine).getTime());
            } catch (Throwable e) {
                throw new ParserException(e);
            }
        }
    }

    private final class ManifestCallback implements Callback<ParsingLoadable<DashManifest>> {
        private ManifestCallback() {
        }

        public void onLoadCanceled(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public void onLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onManifestLoadCompleted(parsingLoadable, j, j2);
        }

        public int onLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException) {
            return DashMediaSource.this.onManifestLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    private static final class PeriodSeekInfo {
        public final long availableEndTimeUs;
        public final long availableStartTimeUs;
        public final boolean isIndexExplicit;

        private PeriodSeekInfo(boolean z, long j, long j2) {
            this.isIndexExplicit = z;
            this.availableStartTimeUs = j;
            this.availableEndTimeUs = j2;
        }

        public static PeriodSeekInfo createPeriodSeekInfo(Period period, long j) {
            int size = period.adaptationSets.size();
            long j2 = 0;
            long j3 = Long.MAX_VALUE;
            boolean z = false;
            Object obj = null;
            int i = 0;
            while (i < size) {
                DashSegmentIndex index = ((Representation) ((AdaptationSet) period.adaptationSets.get(i)).representations.get(0)).getIndex();
                if (index == null) {
                    return new PeriodSeekInfo(true, 0, j);
                }
                Object obj2;
                boolean isExplicit = index.isExplicit() | z;
                int segmentCount = index.getSegmentCount(j);
                if (segmentCount == 0) {
                    obj2 = 1;
                    j2 = 0;
                    j3 = 0;
                } else {
                    if (obj == null) {
                        int firstSegmentNum = index.getFirstSegmentNum();
                        j2 = Math.max(j2, index.getTimeUs(firstSegmentNum));
                        if (segmentCount != -1) {
                            segmentCount = (segmentCount + firstSegmentNum) - 1;
                            j3 = Math.min(j3, index.getDurationUs(segmentCount, j) + index.getTimeUs(segmentCount));
                            obj2 = obj;
                        }
                    }
                    obj2 = obj;
                }
                i++;
                obj = obj2;
                z = isExplicit;
            }
            return new PeriodSeekInfo(z, j2, j3);
        }
    }

    private final class UtcTimestampCallback implements Callback<ParsingLoadable<Long>> {
        private UtcTimestampCallback() {
        }

        public void onLoadCanceled(ParsingLoadable<Long> parsingLoadable, long j, long j2, boolean z) {
            DashMediaSource.this.onLoadCanceled(parsingLoadable, j, j2);
        }

        public void onLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
            DashMediaSource.this.onUtcTimestampLoadCompleted(parsingLoadable, j, j2);
        }

        public int onLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
            return DashMediaSource.this.onUtcTimestampLoadError(parsingLoadable, j, j2, iOException);
        }
    }

    private static final class XsDateTimeParser implements Parser<Long> {
        private XsDateTimeParser() {
        }

        public Long parse(Uri uri, InputStream inputStream) {
            return Long.valueOf(Util.parseXsDateTime(new BufferedReader(new InputStreamReader(inputStream)).readLine()));
        }
    }

    static {
        ExoPlayerLibraryInfo.registerModule("goog.exo.dash");
    }

    public DashMediaSource(Uri uri, DataSource.Factory factory, Factory factory2, int i, long j, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(uri, factory, new DashManifestParser(), factory2, i, j, handler, adaptiveMediaSourceEventListener);
    }

    public DashMediaSource(Uri uri, DataSource.Factory factory, Factory factory2, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(uri, factory, factory2, 3, -1, handler, adaptiveMediaSourceEventListener);
    }

    public DashMediaSource(Uri uri, DataSource.Factory factory, Parser<? extends DashManifest> parser, Factory factory2, int i, long j, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(null, uri, factory, parser, factory2, i, j, handler, adaptiveMediaSourceEventListener);
    }

    private DashMediaSource(DashManifest dashManifest, Uri uri, DataSource.Factory factory, Parser<? extends DashManifest> parser, Factory factory2, int i, long j, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        boolean z = true;
        this.manifest = dashManifest;
        this.manifestUri = uri;
        this.manifestDataSourceFactory = factory;
        this.manifestParser = parser;
        this.chunkSourceFactory = factory2;
        this.minLoadableRetryCount = i;
        this.livePresentationDelayMs = j;
        this.sideloadedManifest = dashManifest != null;
        this.eventDispatcher = new EventDispatcher(handler, adaptiveMediaSourceEventListener);
        this.manifestUriLock = new Object();
        this.periodsById = new SparseArray();
        if (this.sideloadedManifest) {
            if (dashManifest.dynamic) {
                z = false;
            }
            Assertions.checkState(z);
            this.manifestCallback = null;
            this.refreshManifestRunnable = null;
            this.simulateManifestRefreshRunnable = null;
            return;
        }
        this.manifestCallback = new ManifestCallback();
        this.refreshManifestRunnable = new C35271();
        this.simulateManifestRefreshRunnable = new C35282();
    }

    public DashMediaSource(DashManifest dashManifest, Factory factory, int i, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(dashManifest, null, null, null, factory, i, -1, handler, adaptiveMediaSourceEventListener);
    }

    public DashMediaSource(DashManifest dashManifest, Factory factory, Handler handler, AdaptiveMediaSourceEventListener adaptiveMediaSourceEventListener) {
        this(dashManifest, factory, 3, handler, adaptiveMediaSourceEventListener);
    }

    private long getNowUnixTimeUs() {
        return this.elapsedRealtimeOffsetMs != 0 ? C3446C.msToUs(SystemClock.elapsedRealtime() + this.elapsedRealtimeOffsetMs) : C3446C.msToUs(System.currentTimeMillis());
    }

    private void onUtcTimestampResolutionError(IOException iOException) {
        Log.e(TAG, "Failed to resolve UtcTiming element.", iOException);
        processManifest(true);
    }

    private void onUtcTimestampResolved(long j) {
        this.elapsedRealtimeOffsetMs = j;
        processManifest(true);
    }

    private void processManifest(boolean z) {
        long j;
        Object obj;
        for (int i = 0; i < this.periodsById.size(); i++) {
            int keyAt = this.periodsById.keyAt(i);
            if (keyAt >= this.firstPeriodId) {
                ((DashMediaPeriod) this.periodsById.valueAt(i)).updateManifest(this.manifest, keyAt - this.firstPeriodId);
            }
        }
        int periodCount = this.manifest.getPeriodCount() - 1;
        PeriodSeekInfo createPeriodSeekInfo = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(0), this.manifest.getPeriodDurationUs(0));
        PeriodSeekInfo createPeriodSeekInfo2 = PeriodSeekInfo.createPeriodSeekInfo(this.manifest.getPeriod(periodCount), this.manifest.getPeriodDurationUs(periodCount));
        long j2 = createPeriodSeekInfo.availableStartTimeUs;
        long j3 = createPeriodSeekInfo2.availableEndTimeUs;
        if (!this.manifest.dynamic || createPeriodSeekInfo2.isIndexExplicit) {
            j = j2;
            obj = null;
            j2 = j3;
        } else {
            j3 = Math.min((getNowUnixTimeUs() - C3446C.msToUs(this.manifest.availabilityStartTime)) - C3446C.msToUs(this.manifest.getPeriod(periodCount).startMs), j3);
            if (this.manifest.timeShiftBufferDepth != C3446C.TIME_UNSET) {
                j = j3 - C3446C.msToUs(this.manifest.timeShiftBufferDepth);
                int i2 = periodCount;
                while (j < 0 && i2 > 0) {
                    i2--;
                    j += this.manifest.getPeriodDurationUs(i2);
                }
                j2 = i2 == 0 ? Math.max(j2, j) : this.manifest.getPeriodDurationUs(0);
            }
            j = j2;
            obj = 1;
            j2 = j3;
        }
        long j4 = j2 - j;
        for (int i3 = 0; i3 < this.manifest.getPeriodCount() - 1; i3++) {
            j4 += this.manifest.getPeriodDurationUs(i3);
        }
        long j5 = 0;
        if (this.manifest.dynamic) {
            j2 = this.livePresentationDelayMs;
            if (j2 == -1) {
                j2 = this.manifest.suggestedPresentationDelay != C3446C.TIME_UNSET ? this.manifest.suggestedPresentationDelay : 30000;
            }
            j5 = j4 - C3446C.msToUs(j2);
            if (j5 < MIN_LIVE_DEFAULT_START_POSITION_US) {
                j5 = Math.min(MIN_LIVE_DEFAULT_START_POSITION_US, j4 / 2);
            }
        }
        this.sourceListener.onSourceInfoRefreshed(new DashTimeline(this.manifest.availabilityStartTime, (this.manifest.availabilityStartTime + this.manifest.getPeriod(0).startMs) + C3446C.usToMs(j), this.firstPeriodId, j, j4, j5, this.manifest), this.manifest);
        if (!this.sideloadedManifest) {
            this.handler.removeCallbacks(this.simulateManifestRefreshRunnable);
            if (obj != null) {
                this.handler.postDelayed(this.simulateManifestRefreshRunnable, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
            }
            if (z) {
                scheduleManifestRefresh();
            }
        }
    }

    private void resolveUtcTimingElement(UtcTimingElement utcTimingElement) {
        String str = utcTimingElement.schemeIdUri;
        if (Util.areEqual(str, "urn:mpeg:dash:utc:direct:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:direct:2012")) {
            resolveUtcTimingElementDirect(utcTimingElement);
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-iso:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new Iso8601Parser());
        } else if (Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2014") || Util.areEqual(str, "urn:mpeg:dash:utc:http-xsdate:2012")) {
            resolveUtcTimingElementHttp(utcTimingElement, new XsDateTimeParser());
        } else {
            onUtcTimestampResolutionError(new IOException("Unsupported UTC timing scheme"));
        }
    }

    private void resolveUtcTimingElementDirect(UtcTimingElement utcTimingElement) {
        try {
            onUtcTimestampResolved(Util.parseXsDateTime(utcTimingElement.value) - this.manifestLoadEndTimestamp);
        } catch (IOException e) {
            onUtcTimestampResolutionError(e);
        }
    }

    private void resolveUtcTimingElementHttp(UtcTimingElement utcTimingElement, Parser<Long> parser) {
        startLoading(new ParsingLoadable(this.dataSource, Uri.parse(utcTimingElement.value), 5, parser), new UtcTimestampCallback(), 1);
    }

    private void scheduleManifestRefresh() {
        if (this.manifest.dynamic) {
            long j = this.manifest.minUpdatePeriod;
            if (j == 0) {
                j = DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS;
            }
            this.handler.postDelayed(this.refreshManifestRunnable, Math.max(0, (j + this.manifestLoadStartTimestamp) - SystemClock.elapsedRealtime()));
        }
    }

    private <T> void startLoading(ParsingLoadable<T> parsingLoadable, Callback<ParsingLoadable<T>> callback, int i) {
        this.eventDispatcher.loadStarted(parsingLoadable.dataSpec, parsingLoadable.type, this.loader.startLoading(parsingLoadable, callback, i));
    }

    private void startLoadingManifest() {
        Uri uri;
        synchronized (this.manifestUriLock) {
            uri = this.manifestUri;
        }
        startLoading(new ParsingLoadable(this.dataSource, uri, 4, this.manifestParser), this.manifestCallback, this.minLoadableRetryCount);
    }

    public MediaPeriod createPeriod(MediaPeriodId mediaPeriodId, Allocator allocator) {
        int i = mediaPeriodId.periodIndex;
        MediaPeriod dashMediaPeriod = new DashMediaPeriod(this.firstPeriodId + i, this.manifest, i, this.chunkSourceFactory, this.minLoadableRetryCount, this.eventDispatcher.copyWithMediaTimeOffsetMs(this.manifest.getPeriod(i).startMs), this.elapsedRealtimeOffsetMs, this.loaderErrorThrower, allocator);
        this.periodsById.put(dashMediaPeriod.id, dashMediaPeriod);
        return dashMediaPeriod;
    }

    public void maybeThrowSourceInfoRefreshError() {
        this.loaderErrorThrower.maybeThrowError();
    }

    void onLoadCanceled(ParsingLoadable<?> parsingLoadable, long j, long j2) {
        this.eventDispatcher.loadCanceled(parsingLoadable.dataSpec, parsingLoadable.type, j, j2, parsingLoadable.bytesLoaded());
    }

    void onManifestLoadCompleted(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2) {
        this.eventDispatcher.loadCompleted(parsingLoadable.dataSpec, parsingLoadable.type, j, j2, parsingLoadable.bytesLoaded());
        DashManifest dashManifest = (DashManifest) parsingLoadable.getResult();
        int periodCount = this.manifest == null ? 0 : this.manifest.getPeriodCount();
        int i = 0;
        long j3 = dashManifest.getPeriod(0).startMs;
        while (i < periodCount && this.manifest.getPeriod(i).startMs < j3) {
            i++;
        }
        if (periodCount - i > dashManifest.getPeriodCount()) {
            Log.w(TAG, "Out of sync manifest");
            scheduleManifestRefresh();
            return;
        }
        this.manifest = dashManifest;
        this.manifestLoadStartTimestamp = j - j2;
        this.manifestLoadEndTimestamp = j;
        if (this.manifest.location != null) {
            synchronized (this.manifestUriLock) {
                if (parsingLoadable.dataSpec.uri == this.manifestUri) {
                    this.manifestUri = this.manifest.location;
                }
            }
        }
        if (periodCount != 0) {
            this.firstPeriodId += i;
            processManifest(true);
        } else if (this.manifest.utcTiming != null) {
            resolveUtcTimingElement(this.manifest.utcTiming);
        } else {
            processManifest(true);
        }
    }

    int onManifestLoadError(ParsingLoadable<DashManifest> parsingLoadable, long j, long j2, IOException iOException) {
        boolean z = iOException instanceof ParserException;
        this.eventDispatcher.loadError(parsingLoadable.dataSpec, parsingLoadable.type, j, j2, parsingLoadable.bytesLoaded(), iOException, z);
        return z ? 3 : 0;
    }

    void onUtcTimestampLoadCompleted(ParsingLoadable<Long> parsingLoadable, long j, long j2) {
        this.eventDispatcher.loadCompleted(parsingLoadable.dataSpec, parsingLoadable.type, j, j2, parsingLoadable.bytesLoaded());
        onUtcTimestampResolved(((Long) parsingLoadable.getResult()).longValue() - j);
    }

    int onUtcTimestampLoadError(ParsingLoadable<Long> parsingLoadable, long j, long j2, IOException iOException) {
        this.eventDispatcher.loadError(parsingLoadable.dataSpec, parsingLoadable.type, j, j2, parsingLoadable.bytesLoaded(), iOException, true);
        onUtcTimestampResolutionError(iOException);
        return 2;
    }

    public void prepareSource(ExoPlayer exoPlayer, boolean z, Listener listener) {
        this.sourceListener = listener;
        if (this.sideloadedManifest) {
            this.loaderErrorThrower = new Dummy();
            processManifest(false);
            return;
        }
        this.dataSource = this.manifestDataSourceFactory.createDataSource();
        this.loader = new Loader("Loader:DashMediaSource");
        this.loaderErrorThrower = this.loader;
        this.handler = new Handler();
        startLoadingManifest();
    }

    public void releasePeriod(MediaPeriod mediaPeriod) {
        DashMediaPeriod dashMediaPeriod = (DashMediaPeriod) mediaPeriod;
        dashMediaPeriod.release();
        this.periodsById.remove(dashMediaPeriod.id);
    }

    public void releaseSource() {
        this.dataSource = null;
        this.loaderErrorThrower = null;
        if (this.loader != null) {
            this.loader.release();
            this.loader = null;
        }
        this.manifestLoadStartTimestamp = 0;
        this.manifestLoadEndTimestamp = 0;
        this.manifest = null;
        if (this.handler != null) {
            this.handler.removeCallbacksAndMessages(null);
            this.handler = null;
        }
        this.elapsedRealtimeOffsetMs = 0;
        this.periodsById.clear();
    }

    public void replaceManifestUri(Uri uri) {
        synchronized (this.manifestUriLock) {
            this.manifestUri = uri;
        }
    }
}
