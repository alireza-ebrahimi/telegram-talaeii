package org.telegram.messenger.exoplayer2.source.hls;

import android.os.Handler;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.List;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.source.AdaptiveMediaSourceEventListener.EventDispatcher;
import org.telegram.messenger.exoplayer2.source.CompositeSequenceableLoader;
import org.telegram.messenger.exoplayer2.source.MediaPeriod;
import org.telegram.messenger.exoplayer2.source.SampleStream;
import org.telegram.messenger.exoplayer2.source.TrackGroup;
import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.source.hls.HlsSampleStreamWrapper.Callback;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistTracker;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsPlaylistTracker.PlaylistEventListener;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelection;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class HlsMediaPeriod implements MediaPeriod, Callback, PlaylistEventListener {
    private final Allocator allocator;
    private MediaPeriod.Callback callback;
    private final Handler continueLoadingHandler = new Handler();
    private final HlsDataSourceFactory dataSourceFactory;
    private HlsSampleStreamWrapper[] enabledSampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private final EventDispatcher eventDispatcher;
    private final int minLoadableRetryCount;
    private int pendingPrepareCount;
    private final HlsPlaylistTracker playlistTracker;
    private HlsSampleStreamWrapper[] sampleStreamWrappers = new HlsSampleStreamWrapper[0];
    private CompositeSequenceableLoader sequenceableLoader;
    private final IdentityHashMap<SampleStream, Integer> streamWrapperIndices = new IdentityHashMap();
    private final TimestampAdjusterProvider timestampAdjusterProvider = new TimestampAdjusterProvider();
    private TrackGroupArray trackGroups;

    public HlsMediaPeriod(HlsPlaylistTracker hlsPlaylistTracker, HlsDataSourceFactory hlsDataSourceFactory, int i, EventDispatcher eventDispatcher, Allocator allocator) {
        this.playlistTracker = hlsPlaylistTracker;
        this.dataSourceFactory = hlsDataSourceFactory;
        this.minLoadableRetryCount = i;
        this.eventDispatcher = eventDispatcher;
        this.allocator = allocator;
    }

    private void buildAndPrepareSampleStreamWrappers(long j) {
        int i;
        List list;
        HlsMasterPlaylist masterPlaylist = this.playlistTracker.getMasterPlaylist();
        List arrayList = new ArrayList(masterPlaylist.variants);
        ArrayList arrayList2 = new ArrayList();
        Collection arrayList3 = new ArrayList();
        for (i = 0; i < arrayList.size(); i++) {
            HlsUrl hlsUrl = (HlsUrl) arrayList.get(i);
            if (hlsUrl.format.height > 0 || variantHasExplicitCodecWithPrefix(hlsUrl, "avc")) {
                arrayList2.add(hlsUrl);
            } else if (variantHasExplicitCodecWithPrefix(hlsUrl, "mp4a")) {
                arrayList3.add(hlsUrl);
            }
        }
        if (arrayList2.isEmpty()) {
            if (arrayList3.size() < arrayList.size()) {
                arrayList.removeAll(arrayList3);
            }
            list = arrayList;
        } else {
            list = arrayList2;
        }
        List list2 = masterPlaylist.audios;
        List list3 = masterPlaylist.subtitles;
        this.sampleStreamWrappers = new HlsSampleStreamWrapper[((list2.size() + 1) + list3.size())];
        this.pendingPrepareCount = this.sampleStreamWrappers.length;
        Assertions.checkArgument(!list.isEmpty());
        HlsUrl[] hlsUrlArr = new HlsUrl[list.size()];
        list.toArray(hlsUrlArr);
        HlsSampleStreamWrapper buildSampleStreamWrapper = buildSampleStreamWrapper(0, hlsUrlArr, masterPlaylist.muxedAudioFormat, masterPlaylist.muxedCaptionFormats, j);
        this.sampleStreamWrappers[0] = buildSampleStreamWrapper;
        buildSampleStreamWrapper.setIsTimestampMaster(true);
        buildSampleStreamWrapper.continuePreparing();
        int i2 = 0;
        int i3 = 1;
        while (i2 < list2.size()) {
            buildSampleStreamWrapper = buildSampleStreamWrapper(1, new HlsUrl[]{(HlsUrl) list2.get(i2)}, null, Collections.emptyList(), j);
            i = i3 + 1;
            this.sampleStreamWrappers[i3] = buildSampleStreamWrapper;
            buildSampleStreamWrapper.continuePreparing();
            i2++;
            i3 = i;
        }
        i2 = 0;
        while (i2 < list3.size()) {
            HlsSampleStreamWrapper buildSampleStreamWrapper2 = buildSampleStreamWrapper(3, new HlsUrl[]{(HlsUrl) list3.get(i2)}, null, Collections.emptyList(), j);
            buildSampleStreamWrapper2.prepareSingleTrack(hlsUrl.format);
            i = i3 + 1;
            this.sampleStreamWrappers[i3] = buildSampleStreamWrapper2;
            i2++;
            i3 = i;
        }
        this.enabledSampleStreamWrappers = this.sampleStreamWrappers;
    }

    private HlsSampleStreamWrapper buildSampleStreamWrapper(int i, HlsUrl[] hlsUrlArr, Format format, List<Format> list, long j) {
        return new HlsSampleStreamWrapper(i, this, new HlsChunkSource(this.playlistTracker, hlsUrlArr, this.dataSourceFactory, this.timestampAdjusterProvider, list), this.allocator, j, format, this.minLoadableRetryCount, this.eventDispatcher);
    }

    private void continuePreparingOrLoading() {
        if (this.trackGroups != null) {
            this.callback.onContinueLoadingRequested(this);
            return;
        }
        for (HlsSampleStreamWrapper continuePreparing : this.sampleStreamWrappers) {
            continuePreparing.continuePreparing();
        }
    }

    private static boolean variantHasExplicitCodecWithPrefix(HlsUrl hlsUrl, String str) {
        Object obj = hlsUrl.format.codecs;
        if (TextUtils.isEmpty(obj)) {
            return false;
        }
        for (String startsWith : obj.split("(\\s*,\\s*)|(\\s*$)")) {
            if (startsWith.startsWith(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean continueLoading(long j) {
        return this.sequenceableLoader.continueLoading(j);
    }

    public void discardBuffer(long j) {
        for (HlsSampleStreamWrapper discardBuffer : this.enabledSampleStreamWrappers) {
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
        for (HlsSampleStreamWrapper maybeThrowPrepareError : this.sampleStreamWrappers) {
            maybeThrowPrepareError.maybeThrowPrepareError();
        }
    }

    public void onContinueLoadingRequested(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        if (this.trackGroups != null) {
            this.callback.onContinueLoadingRequested(this);
        }
    }

    public void onPlaylistBlacklisted(HlsUrl hlsUrl, long j) {
        for (HlsSampleStreamWrapper onPlaylistBlacklisted : this.sampleStreamWrappers) {
            onPlaylistBlacklisted.onPlaylistBlacklisted(hlsUrl, j);
        }
        continuePreparingOrLoading();
    }

    public void onPlaylistChanged() {
        continuePreparingOrLoading();
    }

    public void onPlaylistRefreshRequired(HlsUrl hlsUrl) {
        this.playlistTracker.refreshPlaylist(hlsUrl);
    }

    public void onPrepared() {
        int i = this.pendingPrepareCount - 1;
        this.pendingPrepareCount = i;
        if (i <= 0) {
            int i2 = 0;
            for (HlsSampleStreamWrapper trackGroups : this.sampleStreamWrappers) {
                i2 += trackGroups.getTrackGroups().length;
            }
            TrackGroup[] trackGroupArr = new TrackGroup[i2];
            HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = this.sampleStreamWrappers;
            int length = hlsSampleStreamWrapperArr.length;
            int i3 = 0;
            i = 0;
            while (i3 < length) {
                HlsSampleStreamWrapper hlsSampleStreamWrapper = hlsSampleStreamWrapperArr[i3];
                int i4 = hlsSampleStreamWrapper.getTrackGroups().length;
                i2 = i;
                i = 0;
                while (i < i4) {
                    int i5 = i2 + 1;
                    trackGroupArr[i2] = hlsSampleStreamWrapper.getTrackGroups().get(i);
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

    public void prepare(MediaPeriod.Callback callback, long j) {
        this.callback = callback;
        this.playlistTracker.addListener(this);
        buildAndPrepareSampleStreamWrappers(j);
    }

    public long readDiscontinuity() {
        return C3446C.TIME_UNSET;
    }

    public void release() {
        this.playlistTracker.removeListener(this);
        this.continueLoadingHandler.removeCallbacksAndMessages(null);
        for (HlsSampleStreamWrapper release : this.sampleStreamWrappers) {
            release.release();
        }
    }

    public long seekToUs(long j) {
        if (this.enabledSampleStreamWrappers.length > 0) {
            boolean seekToUs = this.enabledSampleStreamWrappers[0].seekToUs(j, false);
            for (int i = 1; i < this.enabledSampleStreamWrappers.length; i++) {
                this.enabledSampleStreamWrappers[i].seekToUs(j, seekToUs);
            }
            if (seekToUs) {
                this.timestampAdjusterProvider.reset();
            }
        }
        return j;
    }

    public long selectTracks(TrackSelection[] trackSelectionArr, boolean[] zArr, SampleStream[] sampleStreamArr, boolean[] zArr2, long j) {
        int i;
        int i2;
        int[] iArr = new int[trackSelectionArr.length];
        int[] iArr2 = new int[trackSelectionArr.length];
        for (i = 0; i < trackSelectionArr.length; i++) {
            iArr[i] = sampleStreamArr[i] == null ? -1 : ((Integer) this.streamWrapperIndices.get(sampleStreamArr[i])).intValue();
            iArr2[i] = -1;
            if (trackSelectionArr[i] != null) {
                TrackGroup trackGroup = trackSelectionArr[i].getTrackGroup();
                for (i2 = 0; i2 < this.sampleStreamWrappers.length; i2++) {
                    if (this.sampleStreamWrappers[i2].getTrackGroups().indexOf(trackGroup) != -1) {
                        iArr2[i] = i2;
                        break;
                    }
                }
            }
        }
        boolean z = false;
        this.streamWrapperIndices.clear();
        Object obj = new SampleStream[trackSelectionArr.length];
        SampleStream[] sampleStreamArr2 = new SampleStream[trackSelectionArr.length];
        TrackSelection[] trackSelectionArr2 = new TrackSelection[trackSelectionArr.length];
        HlsSampleStreamWrapper[] hlsSampleStreamWrapperArr = new HlsSampleStreamWrapper[this.sampleStreamWrappers.length];
        i2 = 0;
        int i3 = 0;
        while (i2 < this.sampleStreamWrappers.length) {
            int i4;
            i = 0;
            while (i < trackSelectionArr.length) {
                sampleStreamArr2[i] = iArr[i] == i2 ? sampleStreamArr[i] : null;
                trackSelectionArr2[i] = iArr2[i] == i2 ? trackSelectionArr[i] : null;
                i++;
            }
            HlsSampleStreamWrapper hlsSampleStreamWrapper = this.sampleStreamWrappers[i2];
            boolean selectTracks = hlsSampleStreamWrapper.selectTracks(trackSelectionArr2, zArr, sampleStreamArr2, zArr2, j, z);
            Object obj2 = null;
            for (i4 = 0; i4 < trackSelectionArr.length; i4++) {
                if (iArr2[i4] == i2) {
                    Assertions.checkState(sampleStreamArr2[i4] != null);
                    obj[i4] = sampleStreamArr2[i4];
                    obj2 = 1;
                    this.streamWrapperIndices.put(sampleStreamArr2[i4], Integer.valueOf(i2));
                } else if (iArr[i4] == i2) {
                    Assertions.checkState(sampleStreamArr2[i4] == null);
                }
            }
            if (obj2 != null) {
                hlsSampleStreamWrapperArr[i3] = hlsSampleStreamWrapper;
                i4 = i3 + 1;
                if (i3 == 0) {
                    hlsSampleStreamWrapper.setIsTimestampMaster(true);
                    if (selectTracks || this.enabledSampleStreamWrappers.length == 0 || hlsSampleStreamWrapper != this.enabledSampleStreamWrappers[0]) {
                        this.timestampAdjusterProvider.reset();
                        z = true;
                        i = i4;
                    }
                } else {
                    hlsSampleStreamWrapper.setIsTimestampMaster(false);
                }
                i = i4;
            } else {
                i = i3;
            }
            i2++;
            i3 = i;
        }
        System.arraycopy(obj, 0, sampleStreamArr, 0, obj.length);
        this.enabledSampleStreamWrappers = (HlsSampleStreamWrapper[]) Arrays.copyOf(hlsSampleStreamWrapperArr, i3);
        this.sequenceableLoader = new CompositeSequenceableLoader(this.enabledSampleStreamWrappers);
        return j;
    }
}
