package org.telegram.messenger.exoplayer2.source;

import android.net.Uri;
import android.os.Handler;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.decoder.DecoderInputBuffer;
import org.telegram.messenger.exoplayer2.source.SingleSampleMediaSource.EventListener;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSource.Factory;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.upstream.Loader;
import org.telegram.messenger.exoplayer2.upstream.Loader.Callback;
import org.telegram.messenger.exoplayer2.upstream.Loader.Loadable;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.Util;

final class SingleSampleMediaPeriod implements MediaPeriod, Callback<SourceLoadable> {
    private static final int INITIAL_SAMPLE_SIZE = 1024;
    private final Factory dataSourceFactory;
    private final Handler eventHandler;
    private final EventListener eventListener;
    private final int eventSourceId;
    final Format format;
    final Loader loader = new Loader("Loader:SingleSampleMediaPeriod");
    boolean loadingFinished;
    private final int minLoadableRetryCount;
    byte[] sampleData;
    int sampleSize;
    private final ArrayList<SampleStreamImpl> sampleStreams = new ArrayList();
    private final TrackGroupArray tracks;
    private final Uri uri;

    private final class SampleStreamImpl implements SampleStream {
        private static final int STREAM_STATE_END_OF_STREAM = 2;
        private static final int STREAM_STATE_SEND_FORMAT = 0;
        private static final int STREAM_STATE_SEND_SAMPLE = 1;
        private int streamState;

        private SampleStreamImpl() {
        }

        public boolean isReady() {
            return SingleSampleMediaPeriod.this.loadingFinished;
        }

        public void maybeThrowError() {
            SingleSampleMediaPeriod.this.loader.maybeThrowError();
        }

        public int readData(FormatHolder formatHolder, DecoderInputBuffer decoderInputBuffer, boolean z) {
            if (this.streamState == 2) {
                decoderInputBuffer.addFlag(4);
                return -4;
            } else if (z || this.streamState == 0) {
                formatHolder.format = SingleSampleMediaPeriod.this.format;
                this.streamState = 1;
                return -5;
            } else {
                Assertions.checkState(this.streamState == 1);
                if (!SingleSampleMediaPeriod.this.loadingFinished) {
                    return -3;
                }
                decoderInputBuffer.timeUs = 0;
                decoderInputBuffer.addFlag(1);
                decoderInputBuffer.ensureSpaceForWrite(SingleSampleMediaPeriod.this.sampleSize);
                decoderInputBuffer.data.put(SingleSampleMediaPeriod.this.sampleData, 0, SingleSampleMediaPeriod.this.sampleSize);
                this.streamState = 2;
                return -4;
            }
        }

        public void seekToUs(long j) {
            if (this.streamState == 2) {
                this.streamState = 1;
            }
        }

        public void skipData(long j) {
            if (j > 0) {
                this.streamState = 2;
            }
        }
    }

    static final class SourceLoadable implements Loadable {
        private final DataSource dataSource;
        private byte[] sampleData;
        private int sampleSize;
        private final Uri uri;

        public SourceLoadable(Uri uri, DataSource dataSource) {
            this.uri = uri;
            this.dataSource = dataSource;
        }

        public void cancelLoad() {
        }

        public boolean isLoadCanceled() {
            return false;
        }

        public void load() {
            int i = 0;
            this.sampleSize = 0;
            try {
                this.dataSource.open(new DataSpec(this.uri));
                while (i != -1) {
                    this.sampleSize = i + this.sampleSize;
                    if (this.sampleData == null) {
                        this.sampleData = new byte[1024];
                    } else if (this.sampleSize == this.sampleData.length) {
                        this.sampleData = Arrays.copyOf(this.sampleData, this.sampleData.length * 2);
                    }
                    i = this.dataSource.read(this.sampleData, this.sampleSize, this.sampleData.length - this.sampleSize);
                }
            } finally {
                Util.closeQuietly(this.dataSource);
            }
        }
    }

    public SingleSampleMediaPeriod(Uri uri, Factory factory, Format format, int i, Handler handler, EventListener eventListener, int i2) {
        this.uri = uri;
        this.dataSourceFactory = factory;
        this.format = format;
        this.minLoadableRetryCount = i;
        this.eventHandler = handler;
        this.eventListener = eventListener;
        this.eventSourceId = i2;
        TrackGroup[] trackGroupArr = new TrackGroup[1];
        trackGroupArr[0] = new TrackGroup(format);
        this.tracks = new TrackGroupArray(trackGroupArr);
    }

    private void notifyLoadError(final IOException iOException) {
        if (this.eventHandler != null && this.eventListener != null) {
            this.eventHandler.post(new Runnable() {
                public void run() {
                    SingleSampleMediaPeriod.this.eventListener.onLoadError(SingleSampleMediaPeriod.this.eventSourceId, iOException);
                }
            });
        }
    }

    public boolean continueLoading(long j) {
        if (this.loadingFinished || this.loader.isLoading()) {
            return false;
        }
        this.loader.startLoading(new SourceLoadable(this.uri, this.dataSourceFactory.createDataSource()), this, this.minLoadableRetryCount);
        return true;
    }

    public void discardBuffer(long j) {
    }

    public long getBufferedPositionUs() {
        return this.loadingFinished ? Long.MIN_VALUE : 0;
    }

    public long getNextLoadPositionUs() {
        return (this.loadingFinished || this.loader.isLoading()) ? Long.MIN_VALUE : 0;
    }

    public TrackGroupArray getTrackGroups() {
        return this.tracks;
    }

    public void maybeThrowPrepareError() {
        this.loader.maybeThrowError();
    }

    public void onLoadCanceled(SourceLoadable sourceLoadable, long j, long j2, boolean z) {
    }

    public void onLoadCompleted(SourceLoadable sourceLoadable, long j, long j2) {
        this.sampleSize = sourceLoadable.sampleSize;
        this.sampleData = sourceLoadable.sampleData;
        this.loadingFinished = true;
    }

    public int onLoadError(SourceLoadable sourceLoadable, long j, long j2, IOException iOException) {
        notifyLoadError(iOException);
        return 0;
    }

    public void prepare(MediaPeriod.Callback callback, long j) {
        callback.onPrepared(this);
    }

    public long readDiscontinuity() {
        return C3446C.TIME_UNSET;
    }

    public void release() {
        this.loader.release();
    }

    public long seekToUs(long j) {
        for (int i = 0; i < this.sampleStreams.size(); i++) {
            ((SampleStreamImpl) this.sampleStreams.get(i)).seekToUs(j);
        }
        return j;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int i = 0;
        while (i < trackSelectionArr.length) {
            if (sampleStreamArr[i] != null && (trackSelectionArr[i] == null || !zArr[i])) {
                this.sampleStreams.remove(sampleStreamArr[i]);
                sampleStreamArr[i] = null;
            }
            if (sampleStreamArr[i] == null && trackSelectionArr[i] != null) {
                SampleStreamImpl sampleStreamImpl = new SampleStreamImpl();
                this.sampleStreams.add(sampleStreamImpl);
                sampleStreamArr[i] = sampleStreamImpl;
                zArr2[i] = true;
            }
            i++;
        }
        return j;
    }
}
