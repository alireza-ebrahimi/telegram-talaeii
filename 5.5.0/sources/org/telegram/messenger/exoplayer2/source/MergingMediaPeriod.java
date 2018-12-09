package org.telegram.messenger.exoplayer2.source;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.source.MediaPeriod.Callback;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.util.Assertions;

final class MergingMediaPeriod implements MediaPeriod, Callback {
    private Callback callback;
    private MediaPeriod[] enabledPeriods;
    private int pendingChildPrepareCount;
    public final MediaPeriod[] periods;
    private SequenceableLoader sequenceableLoader;
    private final IdentityHashMap<SampleStream, Integer> streamPeriodIndices = new IdentityHashMap();
    private TrackGroupArray trackGroups;

    public MergingMediaPeriod(MediaPeriod... mediaPeriodArr) {
        this.periods = mediaPeriodArr;
    }

    public boolean continueLoading(long j) {
        return this.sequenceableLoader.continueLoading(j);
    }

    public void discardBuffer(long j) {
        for (MediaPeriod discardBuffer : this.enabledPeriods) {
            discardBuffer.discardBuffer(j);
        }
    }

    public long getBufferedPositionUs() {
        return this.sequenceableLoader.getBufferedPositionUs();
    }

    public long getNextLoadPositionUs() {
        return this.sequenceableLoader.getNextLoadPositionUs();
    }

    public TrackGroupArray getTrackGroups() {
        return this.trackGroups;
    }

    public void maybeThrowPrepareError() {
        for (MediaPeriod maybeThrowPrepareError : this.periods) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public void onContinueLoadingRequested(MediaPeriod mediaPeriod) {
        if (this.trackGroups != null) {
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public void onPrepared(MediaPeriod mediaPeriod) {
        int i = this.pendingChildPrepareCount - 1;
        this.pendingChildPrepareCount = i;
        if (i <= 0) {
            int i2 = 0;
            for (MediaPeriod trackGroups : this.periods) {
                i2 += trackGroups.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            MediaPeriod[] mediaPeriodArr = this.periods;
            int length = mediaPeriodArr.length;
            int i3 = 0;
            i = 0;
            while (i3 < length) {
                TrackGroupArray trackGroups2 = mediaPeriodArr[i3].getTrackGroups();
                int i4 = trackGroups2.length;
                i2 = i;
                i = 0;
                while (i < i4) {
                    int i5 = i2 + 1;
                    trackGroupArr[i2] = trackGroups2.get(i);
                    i++;
                    i2 = i5;
                }
                i3++;
                i = i2;
            }
            this.trackGroups = new TrackGroupArray(trackGroupArr);
            this.callback.onPrepared(this);
        }
    }

    public void prepare(Callback callback, long j) {
        this.callback = callback;
        this.pendingChildPrepareCount = this.periods.length;
        for (MediaPeriod prepare : this.periods) {
            prepare.prepare(this, j);
        }
    }

    public long readDiscontinuity() {
        int i;
        long readDiscontinuity = this.periods[0].readDiscontinuity();
        for (i = 1; i < this.periods.length; i++) {
            if (this.periods[i].readDiscontinuity() != C3446C.TIME_UNSET) {
                throw new IllegalStateException("Child reported discontinuity");
            }
        }
        if (readDiscontinuity != C3446C.TIME_UNSET) {
            MediaPeriod[] mediaPeriodArr = this.enabledPeriods;
            int length = mediaPeriodArr.length;
            i = 0;
            while (i < length) {
                MediaPeriod mediaPeriod = mediaPeriodArr[i];
                if (mediaPeriod == this.periods[0] || mediaPeriod.seekToUs(readDiscontinuity) == readDiscontinuity) {
                    i++;
                } else {
                    throw new IllegalStateException("Children seeked to different positions");
                }
            }
        }
        return readDiscontinuity;
    }

    public long seekToUs(long j) {
        long seekToUs = this.enabledPeriods[0].seekToUs(j);
        for (int i = 1; i < this.enabledPeriods.length; i++) {
            if (this.enabledPeriods[i].seekToUs(seekToUs) != seekToUs) {
                throw new IllegalStateException("Children seeked to different positions");
            }
        }
        return seekToUs;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int i;
        int i2;
        int[] iArr = new int[trackSelectionArr.length];
        int[] iArr2 = new int[trackSelectionArr.length];
        for (i = 0; i < trackSelectionArr.length; i++) {
            iArr[i] = sampleStreamArr[i] == null ? -1 : ((Integer) this.streamPeriodIndices.get(sampleStreamArr[i])).intValue();
            iArr2[i] = -1;
            if (trackSelectionArr[i] != null) {
                TrackGroup trackGroup = trackSelectionArr[i].getTrackGroup();
                for (i2 = 0; i2 < this.periods.length; i2++) {
                    if (this.periods[i2].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i] = i2;
                        break;
                    }
                }
            }
        }
        this.streamPeriodIndices.clear();
        Object obj = new SampleStream[trackSelectionArr.length];
        SampleStream[] sampleStreamArr2 = new SampleStream[trackSelectionArr.length];
        TrackSelection[] trackSelectionArr2 = new TrackSelection[trackSelectionArr.length];
        ArrayList arrayList = new ArrayList(this.periods.length);
        i2 = 0;
        long j2 = j;
        while (i2 < this.periods.length) {
            i = 0;
            while (i < trackSelectionArr.length) {
                sampleStreamArr2[i] = iArr[i] == i2 ? sampleStreamArr[i] : null;
                trackSelectionArr2[i] = iArr2[i] == i2 ? trackSelectionArr[i] : null;
                i++;
            }
            long selectTracks = this.periods[i2].selectTracks(trackSelectionArr2, zArr, sampleStreamArr2, zArr2, j2);
            if (i2 == 0) {
                j2 = selectTracks;
            } else if (selectTracks != j2) {
                throw new IllegalStateException("Children enabled at different positions");
            }
            Object obj2 = null;
            for (i = 0; i < trackSelectionArr.length; i++) {
                if (iArr2[i] == i2) {
                    Assertions.checkState(sampleStreamArr2[i] != null);
                    obj[i] = sampleStreamArr2[i];
                    obj2 = 1;
                    this.streamPeriodIndices.put(sampleStreamArr2[i], Integer.valueOf(i2));
                } else if (iArr[i] == i2) {
                    Assertions.checkState(sampleStreamArr2[i] == null);
                }
            }
            if (obj2 != null) {
                arrayList.add(this.periods[i2]);
            }
            i2++;
        }
        System.arraycopy(obj, 0, sampleStreamArr, 0, obj.length);
        this.enabledPeriods = new MediaPeriod[arrayList.size()];
        arrayList.toArray(this.enabledPeriods);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.enabledPeriods);
        return j2;
    }
}
