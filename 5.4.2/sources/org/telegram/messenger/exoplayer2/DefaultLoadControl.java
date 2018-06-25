package org.telegram.messenger.exoplayer2;

import org.telegram.messenger.exoplayer2.source.TrackGroupArray;
import org.telegram.messenger.exoplayer2.trackselection.TrackSelectionArray;
import org.telegram.messenger.exoplayer2.upstream.Allocator;
import org.telegram.messenger.exoplayer2.upstream.DefaultAllocator;
import org.telegram.messenger.exoplayer2.util.PriorityTaskManager;
import org.telegram.messenger.exoplayer2.util.Util;

public final class DefaultLoadControl implements LoadControl {
    private static final int ABOVE_HIGH_WATERMARK = 0;
    private static final int BELOW_LOW_WATERMARK = 2;
    private static final int BETWEEN_WATERMARKS = 1;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_AFTER_REBUFFER_MS = 5000;
    public static final int DEFAULT_BUFFER_FOR_PLAYBACK_MS = 2500;
    public static final int DEFAULT_MAX_BUFFER_MS = 30000;
    public static final int DEFAULT_MIN_BUFFER_MS = 15000;
    private final DefaultAllocator allocator;
    private final long bufferForPlaybackAfterRebufferUs;
    private final long bufferForPlaybackUs;
    private boolean isBuffering;
    private final long maxBufferUs;
    private final long minBufferUs;
    private final PriorityTaskManager priorityTaskManager;
    private int targetBufferSize;

    public DefaultLoadControl() {
        this(new DefaultAllocator(true, C3446C.DEFAULT_BUFFER_SEGMENT_SIZE));
    }

    public DefaultLoadControl(DefaultAllocator defaultAllocator) {
        this(defaultAllocator, DEFAULT_MIN_BUFFER_MS, DEFAULT_MAX_BUFFER_MS, 2500, DefaultRenderersFactory.DEFAULT_ALLOWED_VIDEO_JOINING_TIME_MS);
    }

    public DefaultLoadControl(DefaultAllocator defaultAllocator, int i, int i2, long j, long j2) {
        this(defaultAllocator, i, i2, j, j2, null);
    }

    public DefaultLoadControl(DefaultAllocator defaultAllocator, int i, int i2, long j, long j2, PriorityTaskManager priorityTaskManager) {
        this.allocator = defaultAllocator;
        this.minBufferUs = ((long) i) * 1000;
        this.maxBufferUs = ((long) i2) * 1000;
        this.bufferForPlaybackUs = j * 1000;
        this.bufferForPlaybackAfterRebufferUs = j2 * 1000;
        this.priorityTaskManager = priorityTaskManager;
    }

    private int getBufferTimeState(long j) {
        return j > this.maxBufferUs ? 0 : j < this.minBufferUs ? 2 : 1;
    }

    private void reset(boolean z) {
        this.targetBufferSize = 0;
        if (this.priorityTaskManager != null && this.isBuffering) {
            this.priorityTaskManager.remove(0);
        }
        this.isBuffering = false;
        if (z) {
            this.allocator.reset();
        }
    }

    public Allocator getAllocator() {
        return this.allocator;
    }

    public void onPrepared() {
        reset(false);
    }

    public void onReleased() {
        reset(true);
    }

    public void onStopped() {
        reset(true);
    }

    public void onTracksSelected(Renderer[] rendererArr, TrackGroupArray trackGroupArray, TrackSelectionArray trackSelectionArray) {
        int i = 0;
        this.targetBufferSize = 0;
        while (i < rendererArr.length) {
            if (trackSelectionArray.get(i) != null) {
                this.targetBufferSize += Util.getDefaultBufferSize(rendererArr[i].getTrackType());
            }
            i++;
        }
        this.allocator.setTargetBufferSize(this.targetBufferSize);
    }

    public boolean shouldContinueLoading(long j) {
        boolean z = true;
        int bufferTimeState = getBufferTimeState(j);
        boolean z2 = this.allocator.getTotalBytesAllocated() >= this.targetBufferSize;
        boolean z3 = this.isBuffering;
        if (!(bufferTimeState == 2 || (bufferTimeState == 1 && this.isBuffering && !z2))) {
            z = false;
        }
        this.isBuffering = z;
        if (!(this.priorityTaskManager == null || this.isBuffering == z3)) {
            if (this.isBuffering) {
                this.priorityTaskManager.add(0);
            } else {
                this.priorityTaskManager.remove(0);
            }
        }
        return this.isBuffering;
    }

    public boolean shouldStartPlayback(long j, boolean z) {
        long j2 = z ? this.bufferForPlaybackAfterRebufferUs : this.bufferForPlaybackUs;
        return j2 <= 0 || j >= j2;
    }
}
