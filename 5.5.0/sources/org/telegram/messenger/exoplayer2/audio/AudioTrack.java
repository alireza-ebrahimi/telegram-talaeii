package org.telegram.messenger.exoplayer2.audio;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.media.AudioAttributes.Builder;
import android.media.AudioFormat;
import android.media.AudioTimestamp;
import android.os.ConditionVariable;
import android.os.SystemClock;
import android.util.Log;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.LinkedList;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.PlaybackParameters;
import org.telegram.messenger.exoplayer2.upstream.cache.CacheDataSink;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.Util;

public final class AudioTrack {
    private static final int BUFFER_MULTIPLICATION_FACTOR = 4;
    public static final long CURRENT_POSITION_NOT_SET = Long.MIN_VALUE;
    private static final int ERROR_BAD_VALUE = -2;
    private static final long MAX_AUDIO_TIMESTAMP_OFFSET_US = 5000000;
    private static final long MAX_BUFFER_DURATION_US = 750000;
    private static final long MAX_LATENCY_US = 5000000;
    private static final int MAX_PLAYHEAD_OFFSET_COUNT = 10;
    private static final long MIN_BUFFER_DURATION_US = 250000;
    private static final int MIN_PLAYHEAD_OFFSET_SAMPLE_INTERVAL_US = 30000;
    private static final int MIN_TIMESTAMP_SAMPLE_INTERVAL_US = 500000;
    private static final int MODE_STATIC = 0;
    private static final int MODE_STREAM = 1;
    private static final long PASSTHROUGH_BUFFER_DURATION_US = 250000;
    private static final int PLAYSTATE_PAUSED = 2;
    private static final int PLAYSTATE_PLAYING = 3;
    private static final int PLAYSTATE_STOPPED = 1;
    private static final int SONIC_MIN_BYTES_FOR_SPEEDUP = 1024;
    private static final int START_IN_SYNC = 1;
    private static final int START_NEED_SYNC = 2;
    private static final int START_NOT_SET = 0;
    private static final int STATE_INITIALIZED = 1;
    private static final String TAG = "AudioTrack";
    @SuppressLint({"InlinedApi"})
    private static final int WRITE_NON_BLOCKING = 1;
    public static boolean enablePreV21AudioSessionWorkaround = false;
    public static boolean failOnSpuriousAudioTimestamp = false;
    private AudioAttributes audioAttributes;
    private final AudioCapabilities audioCapabilities;
    private AudioProcessor[] audioProcessors;
    private int audioSessionId;
    private boolean audioTimestampSet;
    private android.media.AudioTrack audioTrack;
    private final AudioTrackUtil audioTrackUtil;
    private ByteBuffer avSyncHeader;
    private final AudioProcessor[] availableAudioProcessors;
    private int bufferSize;
    private long bufferSizeUs;
    private int bytesUntilNextAvSync;
    private int channelConfig;
    private final ChannelMappingAudioProcessor channelMappingAudioProcessor;
    private int drainingAudioProcessorIndex;
    private PlaybackParameters drainingPlaybackParameters;
    private int encoding;
    private int framesPerEncodedSample;
    private Method getLatencyMethod;
    private boolean handledEndOfStream;
    private boolean hasData;
    private ByteBuffer inputBuffer;
    private android.media.AudioTrack keepSessionIdAudioTrack;
    private long lastFeedElapsedRealtimeMs;
    private long lastPlayheadSampleTimeUs;
    private long lastTimestampSampleTimeUs;
    private long latencyUs;
    private final Listener listener;
    private int nextPlayheadOffsetIndex;
    private ByteBuffer outputBuffer;
    private ByteBuffer[] outputBuffers;
    private int outputEncoding;
    private int outputPcmFrameSize;
    private boolean passthrough;
    private int pcmFrameSize;
    private PlaybackParameters playbackParameters;
    private final LinkedList<PlaybackParametersCheckpoint> playbackParametersCheckpoints;
    private long playbackParametersOffsetUs;
    private long playbackParametersPositionUs;
    private int playheadOffsetCount;
    private final long[] playheadOffsets;
    private boolean playing;
    private byte[] preV21OutputBuffer;
    private int preV21OutputBufferOffset;
    private final ConditionVariable releasingConditionVariable = new ConditionVariable(true);
    private long resumeSystemTimeUs;
    private int sampleRate;
    private long smoothedPlayheadOffsetUs;
    private final SonicAudioProcessor sonicAudioProcessor;
    private int startMediaTimeState;
    private long startMediaTimeUs;
    private long submittedEncodedFrames;
    private long submittedPcmBytes;
    private boolean tunneling;
    private float volume;
    private long writtenEncodedFrames;
    private long writtenPcmBytes;

    private static class AudioTrackUtil {
        protected android.media.AudioTrack audioTrack;
        private long endPlaybackHeadPosition;
        private long lastRawPlaybackHeadPosition;
        private boolean needsPassthroughWorkaround;
        private long passthroughWorkaroundPauseOffset;
        private long rawPlaybackHeadWrapCount;
        private int sampleRate;
        private long stopPlaybackHeadPosition;
        private long stopTimestampUs;

        private AudioTrackUtil() {
        }

        public long getPlaybackHeadPosition() {
            if (this.stopTimestampUs != C3446C.TIME_UNSET) {
                return Math.min(this.endPlaybackHeadPosition, ((((SystemClock.elapsedRealtime() * 1000) - this.stopTimestampUs) * ((long) this.sampleRate)) / C3446C.MICROS_PER_SECOND) + this.stopPlaybackHeadPosition);
            }
            int playState = this.audioTrack.getPlayState();
            if (playState == 1) {
                return 0;
            }
            long playbackHeadPosition = 4294967295L & ((long) this.audioTrack.getPlaybackHeadPosition());
            if (this.needsPassthroughWorkaround) {
                if (playState == 2 && playbackHeadPosition == 0) {
                    this.passthroughWorkaroundPauseOffset = this.lastRawPlaybackHeadPosition;
                }
                playbackHeadPosition += this.passthroughWorkaroundPauseOffset;
            }
            if (this.lastRawPlaybackHeadPosition > playbackHeadPosition) {
                this.rawPlaybackHeadWrapCount++;
            }
            this.lastRawPlaybackHeadPosition = playbackHeadPosition;
            return playbackHeadPosition + (this.rawPlaybackHeadWrapCount << 32);
        }

        public long getPositionUs() {
            return (getPlaybackHeadPosition() * C3446C.MICROS_PER_SECOND) / ((long) this.sampleRate);
        }

        public long getTimestampFramePosition() {
            throw new UnsupportedOperationException();
        }

        public long getTimestampNanoTime() {
            throw new UnsupportedOperationException();
        }

        public void handleEndOfStream(long j) {
            this.stopPlaybackHeadPosition = getPlaybackHeadPosition();
            this.stopTimestampUs = SystemClock.elapsedRealtime() * 1000;
            this.endPlaybackHeadPosition = j;
            this.audioTrack.stop();
        }

        public void pause() {
            if (this.stopTimestampUs == C3446C.TIME_UNSET) {
                this.audioTrack.pause();
            }
        }

        public void reconfigure(android.media.AudioTrack audioTrack, boolean z) {
            this.audioTrack = audioTrack;
            this.needsPassthroughWorkaround = z;
            this.stopTimestampUs = C3446C.TIME_UNSET;
            this.lastRawPlaybackHeadPosition = 0;
            this.rawPlaybackHeadWrapCount = 0;
            this.passthroughWorkaroundPauseOffset = 0;
            if (audioTrack != null) {
                this.sampleRate = audioTrack.getSampleRate();
            }
        }

        public boolean updateTimestamp() {
            return false;
        }
    }

    @TargetApi(19)
    private static class AudioTrackUtilV19 extends AudioTrackUtil {
        private final AudioTimestamp audioTimestamp = new AudioTimestamp();
        private long lastRawTimestampFramePosition;
        private long lastTimestampFramePosition;
        private long rawTimestampFramePositionWrapCount;

        public AudioTrackUtilV19() {
            super();
        }

        public long getTimestampFramePosition() {
            return this.lastTimestampFramePosition;
        }

        public long getTimestampNanoTime() {
            return this.audioTimestamp.nanoTime;
        }

        public void reconfigure(android.media.AudioTrack audioTrack, boolean z) {
            super.reconfigure(audioTrack, z);
            this.rawTimestampFramePositionWrapCount = 0;
            this.lastRawTimestampFramePosition = 0;
            this.lastTimestampFramePosition = 0;
        }

        public boolean updateTimestamp() {
            boolean timestamp = this.audioTrack.getTimestamp(this.audioTimestamp);
            if (timestamp) {
                long j = this.audioTimestamp.framePosition;
                if (this.lastRawTimestampFramePosition > j) {
                    this.rawTimestampFramePositionWrapCount++;
                }
                this.lastRawTimestampFramePosition = j;
                this.lastTimestampFramePosition = j + (this.rawTimestampFramePositionWrapCount << 32);
            }
            return timestamp;
        }
    }

    public static final class ConfigurationException extends Exception {
        public ConfigurationException(String str) {
            super(str);
        }

        public ConfigurationException(Throwable th) {
            super(th);
        }
    }

    public static final class InitializationException extends Exception {
        public final int audioTrackState;

        public InitializationException(int i, int i2, int i3, int i4) {
            super("AudioTrack init failed: " + i + ", Config(" + i2 + ", " + i3 + ", " + i4 + ")");
            this.audioTrackState = i;
        }
    }

    public static final class InvalidAudioTrackTimestampException extends RuntimeException {
        public InvalidAudioTrackTimestampException(String str) {
            super(str);
        }
    }

    public interface Listener {
        void onAudioSessionId(int i);

        void onPositionDiscontinuity();

        void onUnderrun(int i, long j, long j2);
    }

    private static final class PlaybackParametersCheckpoint {
        private final long mediaTimeUs;
        private final PlaybackParameters playbackParameters;
        private final long positionUs;

        private PlaybackParametersCheckpoint(PlaybackParameters playbackParameters, long j, long j2) {
            this.playbackParameters = playbackParameters;
            this.mediaTimeUs = j;
            this.positionUs = j2;
        }
    }

    public static final class WriteException extends Exception {
        public final int errorCode;

        public WriteException(int i) {
            super("AudioTrack write failed: " + i);
            this.errorCode = i;
        }
    }

    public AudioTrack(AudioCapabilities audioCapabilities, AudioProcessor[] audioProcessorArr, Listener listener) {
        this.audioCapabilities = audioCapabilities;
        this.listener = listener;
        if (Util.SDK_INT >= 18) {
            try {
                this.getLatencyMethod = android.media.AudioTrack.class.getMethod("getLatency", (Class[]) null);
            } catch (NoSuchMethodException e) {
            }
        }
        if (Util.SDK_INT >= 19) {
            this.audioTrackUtil = new AudioTrackUtilV19();
        } else {
            this.audioTrackUtil = new AudioTrackUtil();
        }
        this.channelMappingAudioProcessor = new ChannelMappingAudioProcessor();
        this.sonicAudioProcessor = new SonicAudioProcessor();
        this.availableAudioProcessors = new AudioProcessor[(audioProcessorArr.length + 3)];
        this.availableAudioProcessors[0] = new ResamplingAudioProcessor();
        this.availableAudioProcessors[1] = this.channelMappingAudioProcessor;
        System.arraycopy(audioProcessorArr, 0, this.availableAudioProcessors, 2, audioProcessorArr.length);
        this.availableAudioProcessors[audioProcessorArr.length + 2] = this.sonicAudioProcessor;
        this.playheadOffsets = new long[10];
        this.volume = 1.0f;
        this.startMediaTimeState = 0;
        this.audioAttributes = AudioAttributes.DEFAULT;
        this.audioSessionId = 0;
        this.playbackParameters = PlaybackParameters.DEFAULT;
        this.drainingAudioProcessorIndex = -1;
        this.audioProcessors = new AudioProcessor[0];
        this.outputBuffers = new ByteBuffer[0];
        this.playbackParametersCheckpoints = new LinkedList();
    }

    private long applySpeedup(long j) {
        while (!this.playbackParametersCheckpoints.isEmpty() && j >= ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getFirst()).positionUs) {
            PlaybackParametersCheckpoint playbackParametersCheckpoint = (PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.remove();
            this.playbackParameters = playbackParametersCheckpoint.playbackParameters;
            this.playbackParametersPositionUs = playbackParametersCheckpoint.positionUs;
            this.playbackParametersOffsetUs = playbackParametersCheckpoint.mediaTimeUs - this.startMediaTimeUs;
        }
        if (this.playbackParameters.speed == 1.0f) {
            return (this.playbackParametersOffsetUs + j) - this.playbackParametersPositionUs;
        }
        if (!this.playbackParametersCheckpoints.isEmpty() || this.sonicAudioProcessor.getOutputByteCount() < 1024) {
            return this.playbackParametersOffsetUs + ((long) (((double) this.playbackParameters.speed) * ((double) (j - this.playbackParametersPositionUs))));
        }
        return Util.scaleLargeTimestamp(j - this.playbackParametersPositionUs, this.sonicAudioProcessor.getInputByteCount(), this.sonicAudioProcessor.getOutputByteCount()) + this.playbackParametersOffsetUs;
    }

    @TargetApi(21)
    private android.media.AudioTrack createAudioTrackV21() {
        return new android.media.AudioTrack(this.tunneling ? new Builder().setContentType(3).setFlags(16).setUsage(1).build() : this.audioAttributes.getAudioAttributesV21(), new AudioFormat.Builder().setChannelMask(this.channelConfig).setEncoding(this.outputEncoding).setSampleRate(this.sampleRate).build(), this.bufferSize, 1, this.audioSessionId != 0 ? this.audioSessionId : 0);
    }

    private boolean drainAudioProcessorsToEndOfStream() {
        boolean z;
        if (this.drainingAudioProcessorIndex == -1) {
            this.drainingAudioProcessorIndex = this.passthrough ? this.audioProcessors.length : 0;
            z = true;
        } else {
            z = false;
        }
        while (this.drainingAudioProcessorIndex < this.audioProcessors.length) {
            AudioProcessor audioProcessor = this.audioProcessors[this.drainingAudioProcessorIndex];
            if (z) {
                audioProcessor.queueEndOfStream();
            }
            processBuffers(C3446C.TIME_UNSET);
            if (!audioProcessor.isEnded()) {
                return false;
            }
            this.drainingAudioProcessorIndex++;
            z = true;
        }
        if (this.outputBuffer != null) {
            writeBuffer(this.outputBuffer, C3446C.TIME_UNSET);
            if (this.outputBuffer != null) {
                return false;
            }
        }
        this.drainingAudioProcessorIndex = -1;
        return true;
    }

    private long durationUsToFrames(long j) {
        return (((long) this.sampleRate) * j) / C3446C.MICROS_PER_SECOND;
    }

    private long framesToDurationUs(long j) {
        return (C3446C.MICROS_PER_SECOND * j) / ((long) this.sampleRate);
    }

    private static int getEncodingForMimeType(String str) {
        int i = -1;
        switch (str.hashCode()) {
            case -1095064472:
                if (str.equals(MimeTypes.AUDIO_DTS)) {
                    i = 2;
                    break;
                }
                break;
            case 187078296:
                if (str.equals(MimeTypes.AUDIO_AC3)) {
                    i = 0;
                    break;
                }
                break;
            case 1504578661:
                if (str.equals(MimeTypes.AUDIO_E_AC3)) {
                    i = 1;
                    break;
                }
                break;
            case 1505942594:
                if (str.equals(MimeTypes.AUDIO_DTS_HD)) {
                    i = 3;
                    break;
                }
                break;
        }
        switch (i) {
            case 0:
                return 5;
            case 1:
                return 6;
            case 2:
                return 7;
            case 3:
                return 8;
            default:
                return 0;
        }
    }

    private static int getFramesPerEncodedSample(int i, ByteBuffer byteBuffer) {
        if (i == 7 || i == 8) {
            return DtsUtil.parseDtsAudioSampleCount(byteBuffer);
        }
        if (i == 5) {
            return Ac3Util.getAc3SyncframeAudioSampleCount();
        }
        if (i == 6) {
            return Ac3Util.parseEAc3SyncframeAudioSampleCount(byteBuffer);
        }
        throw new IllegalStateException("Unexpected audio encoding: " + i);
    }

    private long getSubmittedFrames() {
        return this.passthrough ? this.submittedEncodedFrames : this.submittedPcmBytes / ((long) this.pcmFrameSize);
    }

    private long getWrittenFrames() {
        return this.passthrough ? this.writtenEncodedFrames : this.writtenPcmBytes / ((long) this.outputPcmFrameSize);
    }

    private boolean hasCurrentPositionUs() {
        return isInitialized() && this.startMediaTimeState != 0;
    }

    private void initialize() {
        this.releasingConditionVariable.block();
        this.audioTrack = initializeAudioTrack();
        int audioSessionId = this.audioTrack.getAudioSessionId();
        if (enablePreV21AudioSessionWorkaround && Util.SDK_INT < 21) {
            if (!(this.keepSessionIdAudioTrack == null || audioSessionId == this.keepSessionIdAudioTrack.getAudioSessionId())) {
                releaseKeepSessionIdAudioTrack();
            }
            if (this.keepSessionIdAudioTrack == null) {
                this.keepSessionIdAudioTrack = initializeKeepSessionIdAudioTrack(audioSessionId);
            }
        }
        if (this.audioSessionId != audioSessionId) {
            this.audioSessionId = audioSessionId;
            this.listener.onAudioSessionId(audioSessionId);
        }
        this.audioTrackUtil.reconfigure(this.audioTrack, needsPassthroughWorkarounds());
        setVolumeInternal();
        this.hasData = false;
    }

    private android.media.AudioTrack initializeAudioTrack() {
        android.media.AudioTrack createAudioTrackV21;
        int streamTypeForAudioUsage;
        if (Util.SDK_INT >= 21) {
            createAudioTrackV21 = createAudioTrackV21();
        } else {
            streamTypeForAudioUsage = Util.getStreamTypeForAudioUsage(this.audioAttributes.usage);
            createAudioTrackV21 = this.audioSessionId == 0 ? new android.media.AudioTrack(streamTypeForAudioUsage, this.sampleRate, this.channelConfig, this.outputEncoding, this.bufferSize, 1) : new android.media.AudioTrack(streamTypeForAudioUsage, this.sampleRate, this.channelConfig, this.outputEncoding, this.bufferSize, 1, this.audioSessionId);
        }
        streamTypeForAudioUsage = createAudioTrackV21.getState();
        if (streamTypeForAudioUsage == 1) {
            return createAudioTrackV21;
        }
        try {
            createAudioTrackV21.release();
        } catch (Exception e) {
        }
        throw new InitializationException(streamTypeForAudioUsage, this.sampleRate, this.channelConfig, this.bufferSize);
    }

    private android.media.AudioTrack initializeKeepSessionIdAudioTrack(int i) {
        return new android.media.AudioTrack(3, WearableStatusCodes.TARGET_NODE_NOT_CONNECTED, 4, 2, 2, 0, i);
    }

    private boolean isInitialized() {
        return this.audioTrack != null;
    }

    private void maybeSampleSyncParams() {
        long positionUs = this.audioTrackUtil.getPositionUs();
        if (positionUs != 0) {
            long nanoTime = System.nanoTime() / 1000;
            if (nanoTime - this.lastPlayheadSampleTimeUs >= 30000) {
                this.playheadOffsets[this.nextPlayheadOffsetIndex] = positionUs - nanoTime;
                this.nextPlayheadOffsetIndex = (this.nextPlayheadOffsetIndex + 1) % 10;
                if (this.playheadOffsetCount < 10) {
                    this.playheadOffsetCount++;
                }
                this.lastPlayheadSampleTimeUs = nanoTime;
                this.smoothedPlayheadOffsetUs = 0;
                for (int i = 0; i < this.playheadOffsetCount; i++) {
                    this.smoothedPlayheadOffsetUs += this.playheadOffsets[i] / ((long) this.playheadOffsetCount);
                }
            }
            if (!needsPassthroughWorkarounds() && nanoTime - this.lastTimestampSampleTimeUs >= 500000) {
                this.audioTimestampSet = this.audioTrackUtil.updateTimestamp();
                if (this.audioTimestampSet) {
                    long timestampNanoTime = this.audioTrackUtil.getTimestampNanoTime() / 1000;
                    long timestampFramePosition = this.audioTrackUtil.getTimestampFramePosition();
                    if (timestampNanoTime < this.resumeSystemTimeUs) {
                        this.audioTimestampSet = false;
                    } else if (Math.abs(timestampNanoTime - nanoTime) > 5000000) {
                        r0 = "Spurious audio timestamp (system clock mismatch): " + timestampFramePosition + ", " + timestampNanoTime + ", " + nanoTime + ", " + positionUs + ", " + getSubmittedFrames() + ", " + getWrittenFrames();
                        if (failOnSpuriousAudioTimestamp) {
                            throw new InvalidAudioTrackTimestampException(r0);
                        }
                        Log.w(TAG, r0);
                        this.audioTimestampSet = false;
                    } else if (Math.abs(framesToDurationUs(timestampFramePosition) - positionUs) > 5000000) {
                        r0 = "Spurious audio timestamp (frame position mismatch): " + timestampFramePosition + ", " + timestampNanoTime + ", " + nanoTime + ", " + positionUs + ", " + getSubmittedFrames() + ", " + getWrittenFrames();
                        if (failOnSpuriousAudioTimestamp) {
                            throw new InvalidAudioTrackTimestampException(r0);
                        }
                        Log.w(TAG, r0);
                        this.audioTimestampSet = false;
                    }
                }
                if (!(this.getLatencyMethod == null || this.passthrough)) {
                    try {
                        this.latencyUs = (((long) ((Integer) this.getLatencyMethod.invoke(this.audioTrack, (Object[]) null)).intValue()) * 1000) - this.bufferSizeUs;
                        this.latencyUs = Math.max(this.latencyUs, 0);
                        if (this.latencyUs > 5000000) {
                            Log.w(TAG, "Ignoring impossibly large audio latency: " + this.latencyUs);
                            this.latencyUs = 0;
                        }
                    } catch (Exception e) {
                        this.getLatencyMethod = null;
                    }
                }
                this.lastTimestampSampleTimeUs = nanoTime;
            }
        }
    }

    private boolean needsPassthroughWorkarounds() {
        return Util.SDK_INT < 23 && (this.outputEncoding == 5 || this.outputEncoding == 6);
    }

    private boolean overrideHasPendingData() {
        return needsPassthroughWorkarounds() && this.audioTrack.getPlayState() == 2 && this.audioTrack.getPlaybackHeadPosition() == 0;
    }

    private void processBuffers(long j) {
        int length = this.audioProcessors.length;
        int i = length;
        while (i >= 0) {
            ByteBuffer byteBuffer = i > 0 ? this.outputBuffers[i - 1] : this.inputBuffer != null ? this.inputBuffer : AudioProcessor.EMPTY_BUFFER;
            if (i == length) {
                writeBuffer(byteBuffer, j);
            } else {
                AudioProcessor audioProcessor = this.audioProcessors[i];
                audioProcessor.queueInput(byteBuffer);
                ByteBuffer output = audioProcessor.getOutput();
                this.outputBuffers[i] = output;
                if (output.hasRemaining()) {
                    i++;
                }
            }
            if (!byteBuffer.hasRemaining()) {
                i--;
            } else {
                return;
            }
        }
    }

    private void releaseKeepSessionIdAudioTrack() {
        if (this.keepSessionIdAudioTrack != null) {
            final android.media.AudioTrack audioTrack = this.keepSessionIdAudioTrack;
            this.keepSessionIdAudioTrack = null;
            new Thread() {
                public void run() {
                    audioTrack.release();
                }
            }.start();
        }
    }

    private void resetAudioProcessors() {
        int i;
        ArrayList arrayList = new ArrayList();
        for (AudioProcessor audioProcessor : this.availableAudioProcessors) {
            if (audioProcessor.isActive()) {
                arrayList.add(audioProcessor);
            } else {
                audioProcessor.flush();
            }
        }
        int size = arrayList.size();
        this.audioProcessors = (AudioProcessor[]) arrayList.toArray(new AudioProcessor[size]);
        this.outputBuffers = new ByteBuffer[size];
        for (i = 0; i < size; i++) {
            AudioProcessor audioProcessor2 = this.audioProcessors[i];
            audioProcessor2.flush();
            this.outputBuffers[i] = audioProcessor2.getOutput();
        }
    }

    private void resetSyncParams() {
        this.smoothedPlayheadOffsetUs = 0;
        this.playheadOffsetCount = 0;
        this.nextPlayheadOffsetIndex = 0;
        this.lastPlayheadSampleTimeUs = 0;
        this.audioTimestampSet = false;
        this.lastTimestampSampleTimeUs = 0;
    }

    private void setVolumeInternal() {
        if (!isInitialized()) {
            return;
        }
        if (Util.SDK_INT >= 21) {
            setVolumeInternalV21(this.audioTrack, this.volume);
        } else {
            setVolumeInternalV3(this.audioTrack, this.volume);
        }
    }

    @TargetApi(21)
    private static void setVolumeInternalV21(android.media.AudioTrack audioTrack, float f) {
        audioTrack.setVolume(f);
    }

    private static void setVolumeInternalV3(android.media.AudioTrack audioTrack, float f) {
        audioTrack.setStereoVolume(f, f);
    }

    private boolean writeBuffer(ByteBuffer byteBuffer, long j) {
        if (!byteBuffer.hasRemaining()) {
            return true;
        }
        int remaining;
        if (this.outputBuffer != null) {
            Assertions.checkArgument(this.outputBuffer == byteBuffer);
        } else {
            this.outputBuffer = byteBuffer;
            if (Util.SDK_INT < 21) {
                remaining = byteBuffer.remaining();
                if (this.preV21OutputBuffer == null || this.preV21OutputBuffer.length < remaining) {
                    this.preV21OutputBuffer = new byte[remaining];
                }
                int position = byteBuffer.position();
                byteBuffer.get(this.preV21OutputBuffer, 0, remaining);
                byteBuffer.position(position);
                this.preV21OutputBufferOffset = 0;
            }
        }
        int remaining2 = byteBuffer.remaining();
        if (Util.SDK_INT < 21) {
            remaining = this.bufferSize - ((int) (this.writtenPcmBytes - (this.audioTrackUtil.getPlaybackHeadPosition() * ((long) this.outputPcmFrameSize))));
            if (remaining > 0) {
                remaining = this.audioTrack.write(this.preV21OutputBuffer, this.preV21OutputBufferOffset, Math.min(remaining2, remaining));
                if (remaining > 0) {
                    this.preV21OutputBufferOffset += remaining;
                    byteBuffer.position(byteBuffer.position() + remaining);
                }
            } else {
                remaining = 0;
            }
        } else if (this.tunneling) {
            Assertions.checkState(j != C3446C.TIME_UNSET);
            remaining = writeNonBlockingWithAvSyncV21(this.audioTrack, byteBuffer, remaining2, j);
        } else {
            remaining = writeNonBlockingV21(this.audioTrack, byteBuffer, remaining2);
        }
        this.lastFeedElapsedRealtimeMs = SystemClock.elapsedRealtime();
        if (remaining < 0) {
            throw new WriteException(remaining);
        }
        if (!this.passthrough) {
            this.writtenPcmBytes += (long) remaining;
        }
        if (remaining != remaining2) {
            return false;
        }
        if (this.passthrough) {
            this.writtenEncodedFrames += (long) this.framesPerEncodedSample;
        }
        this.outputBuffer = null;
        return true;
    }

    @TargetApi(21)
    private static int writeNonBlockingV21(android.media.AudioTrack audioTrack, ByteBuffer byteBuffer, int i) {
        return audioTrack.write(byteBuffer, i, 1);
    }

    @TargetApi(21)
    private int writeNonBlockingWithAvSyncV21(android.media.AudioTrack audioTrack, ByteBuffer byteBuffer, int i, long j) {
        int write;
        if (this.avSyncHeader == null) {
            this.avSyncHeader = ByteBuffer.allocate(16);
            this.avSyncHeader.order(ByteOrder.BIG_ENDIAN);
            this.avSyncHeader.putInt(1431633921);
        }
        if (this.bytesUntilNextAvSync == 0) {
            this.avSyncHeader.putInt(4, i);
            this.avSyncHeader.putLong(8, 1000 * j);
            this.avSyncHeader.position(0);
            this.bytesUntilNextAvSync = i;
        }
        int remaining = this.avSyncHeader.remaining();
        if (remaining > 0) {
            write = audioTrack.write(this.avSyncHeader, remaining, 1);
            if (write < 0) {
                this.bytesUntilNextAvSync = 0;
                return write;
            } else if (write < remaining) {
                return 0;
            }
        }
        write = writeNonBlockingV21(audioTrack, byteBuffer, i);
        if (write < 0) {
            this.bytesUntilNextAvSync = 0;
            return write;
        }
        this.bytesUntilNextAvSync -= write;
        return write;
    }

    public void configure(String str, int i, int i2, int i3, int i4) {
        configure(str, i, i2, i3, i4, null);
    }

    public void configure(String str, int i, int i2, int i3, int i4, int[] iArr) {
        int i5;
        int i6;
        int i7;
        boolean z = !MimeTypes.AUDIO_RAW.equals(str);
        int encodingForMimeType = z ? getEncodingForMimeType(str) : i3;
        if (z) {
            i5 = 0;
        } else {
            this.pcmFrameSize = Util.getPcmFrameSize(i3, i);
            this.channelMappingAudioProcessor.setChannelMap(iArr);
            AudioProcessor[] audioProcessorArr = this.availableAudioProcessors;
            int length = audioProcessorArr.length;
            i5 = 0;
            i6 = 0;
            i7 = encodingForMimeType;
            encodingForMimeType = i;
            while (i5 < length) {
                AudioProcessor audioProcessor = audioProcessorArr[i5];
                try {
                    int configure = audioProcessor.configure(i2, encodingForMimeType, i7) | i6;
                    if (audioProcessor.isActive()) {
                        encodingForMimeType = audioProcessor.getOutputChannelCount();
                        i7 = audioProcessor.getOutputEncoding();
                    }
                    i5++;
                    i6 = configure;
                } catch (Throwable e) {
                    throw new ConfigurationException(e);
                }
            }
            if (i6 != 0) {
                resetAudioProcessors();
            }
            i5 = i6;
            i = encodingForMimeType;
            encodingForMimeType = i7;
        }
        switch (i) {
            case 1:
                i7 = 4;
                break;
            case 2:
                i7 = 12;
                break;
            case 3:
                i7 = 28;
                break;
            case 4:
                i7 = 204;
                break;
            case 5:
                i7 = 220;
                break;
            case 6:
                i7 = 252;
                break;
            case 7:
                i7 = 1276;
                break;
            case 8:
                i7 = C3446C.CHANNEL_OUT_7POINT1_SURROUND;
                break;
            default:
                throw new ConfigurationException("Unsupported channel count: " + i);
        }
        if (Util.SDK_INT <= 23 && "foster".equals(Util.DEVICE) && "NVIDIA".equals(Util.MANUFACTURER)) {
            switch (i) {
                case 3:
                case 5:
                    i7 = 252;
                    break;
                case 7:
                    i7 = C3446C.CHANNEL_OUT_7POINT1_SURROUND;
                    break;
            }
        }
        i6 = (Util.SDK_INT <= 25 && "fugu".equals(Util.DEVICE) && z && i == 1) ? 12 : i7;
        if (i5 != 0 || !isInitialized() || this.encoding != encodingForMimeType || this.sampleRate != i2 || this.channelConfig != i6) {
            reset();
            this.encoding = encodingForMimeType;
            this.passthrough = z;
            this.sampleRate = i2;
            this.channelConfig = i6;
            this.outputEncoding = z ? encodingForMimeType : 2;
            this.outputPcmFrameSize = Util.getPcmFrameSize(2, i);
            if (i4 != 0) {
                this.bufferSize = i4;
            } else if (!z) {
                encodingForMimeType = android.media.AudioTrack.getMinBufferSize(i2, i6, this.outputEncoding);
                Assertions.checkState(encodingForMimeType != -2);
                i6 = encodingForMimeType * 4;
                i7 = ((int) durationUsToFrames(250000)) * this.outputPcmFrameSize;
                encodingForMimeType = (int) Math.max((long) encodingForMimeType, durationUsToFrames(MAX_BUFFER_DURATION_US) * ((long) this.outputPcmFrameSize));
                if (i6 >= i7) {
                    i7 = i6 > encodingForMimeType ? encodingForMimeType : i6;
                }
                this.bufferSize = i7;
            } else if (this.outputEncoding == 5 || this.outputEncoding == 6) {
                this.bufferSize = CacheDataSink.DEFAULT_BUFFER_SIZE;
            } else {
                this.bufferSize = 49152;
            }
            this.bufferSizeUs = z ? C3446C.TIME_UNSET : framesToDurationUs((long) (this.bufferSize / this.outputPcmFrameSize));
            setPlaybackParameters(this.playbackParameters);
        }
    }

    public void disableTunneling() {
        if (this.tunneling) {
            this.tunneling = false;
            this.audioSessionId = 0;
            reset();
        }
    }

    public void enableTunnelingV21(int i) {
        Assertions.checkState(Util.SDK_INT >= 21);
        if (!this.tunneling || this.audioSessionId != i) {
            this.tunneling = true;
            this.audioSessionId = i;
            reset();
        }
    }

    public long getCurrentPositionUs(boolean z) {
        if (!hasCurrentPositionUs()) {
            return Long.MIN_VALUE;
        }
        if (this.audioTrack.getPlayState() == 3) {
            maybeSampleSyncParams();
        }
        long nanoTime = System.nanoTime() / 1000;
        if (this.audioTimestampSet) {
            nanoTime = framesToDurationUs(durationUsToFrames(nanoTime - (this.audioTrackUtil.getTimestampNanoTime() / 1000)) + this.audioTrackUtil.getTimestampFramePosition());
        } else {
            nanoTime = this.playheadOffsetCount == 0 ? this.audioTrackUtil.getPositionUs() : nanoTime + this.smoothedPlayheadOffsetUs;
            if (!z) {
                nanoTime -= this.latencyUs;
            }
        }
        return applySpeedup(nanoTime) + this.startMediaTimeUs;
    }

    public PlaybackParameters getPlaybackParameters() {
        return this.playbackParameters;
    }

    public boolean handleBuffer(ByteBuffer byteBuffer, long j) {
        boolean z = this.inputBuffer == null || byteBuffer == this.inputBuffer;
        Assertions.checkArgument(z);
        if (!isInitialized()) {
            initialize();
            if (this.playing) {
                play();
            }
        }
        if (needsPassthroughWorkarounds()) {
            if (this.audioTrack.getPlayState() == 2) {
                this.hasData = false;
                return false;
            } else if (this.audioTrack.getPlayState() == 1 && this.audioTrackUtil.getPlaybackHeadPosition() != 0) {
                return false;
            }
        }
        z = this.hasData;
        this.hasData = hasPendingData();
        if (!(!z || this.hasData || this.audioTrack.getPlayState() == 1)) {
            this.listener.onUnderrun(this.bufferSize, C3446C.usToMs(this.bufferSizeUs), SystemClock.elapsedRealtime() - this.lastFeedElapsedRealtimeMs);
        }
        if (this.inputBuffer == null) {
            if (!byteBuffer.hasRemaining()) {
                return true;
            }
            if (this.passthrough && this.framesPerEncodedSample == 0) {
                this.framesPerEncodedSample = getFramesPerEncodedSample(this.outputEncoding, byteBuffer);
            }
            if (this.drainingPlaybackParameters != null) {
                if (!drainAudioProcessorsToEndOfStream()) {
                    return false;
                }
                this.playbackParametersCheckpoints.add(new PlaybackParametersCheckpoint(this.drainingPlaybackParameters, Math.max(0, j), framesToDurationUs(getWrittenFrames())));
                this.drainingPlaybackParameters = null;
                resetAudioProcessors();
            }
            if (this.startMediaTimeState == 0) {
                this.startMediaTimeUs = Math.max(0, j);
                this.startMediaTimeState = 1;
            } else {
                long framesToDurationUs = this.startMediaTimeUs + framesToDurationUs(getSubmittedFrames());
                if (this.startMediaTimeState == 1 && Math.abs(framesToDurationUs - j) > 200000) {
                    Log.e(TAG, "Discontinuity detected [expected " + framesToDurationUs + ", got " + j + "]");
                    this.startMediaTimeState = 2;
                }
                if (this.startMediaTimeState == 2) {
                    this.startMediaTimeUs = (j - framesToDurationUs) + this.startMediaTimeUs;
                    this.startMediaTimeState = 1;
                    this.listener.onPositionDiscontinuity();
                }
            }
            if (this.passthrough) {
                this.submittedEncodedFrames += (long) this.framesPerEncodedSample;
            } else {
                this.submittedPcmBytes += (long) byteBuffer.remaining();
            }
            this.inputBuffer = byteBuffer;
        }
        if (this.passthrough) {
            writeBuffer(this.inputBuffer, j);
        } else {
            processBuffers(j);
        }
        if (this.inputBuffer.hasRemaining()) {
            return false;
        }
        this.inputBuffer = null;
        return true;
    }

    public void handleDiscontinuity() {
        if (this.startMediaTimeState == 1) {
            this.startMediaTimeState = 2;
        }
    }

    public boolean hasPendingData() {
        return isInitialized() && (getWrittenFrames() > this.audioTrackUtil.getPlaybackHeadPosition() || overrideHasPendingData());
    }

    public boolean isEnded() {
        return !isInitialized() || (this.handledEndOfStream && !hasPendingData());
    }

    public boolean isPassthroughSupported(String str) {
        return this.audioCapabilities != null && this.audioCapabilities.supportsEncoding(getEncodingForMimeType(str));
    }

    public void pause() {
        this.playing = false;
        if (isInitialized()) {
            resetSyncParams();
            this.audioTrackUtil.pause();
        }
    }

    public void play() {
        this.playing = true;
        if (isInitialized()) {
            this.resumeSystemTimeUs = System.nanoTime() / 1000;
            this.audioTrack.play();
        }
    }

    public void playToEndOfStream() {
        if (!this.handledEndOfStream && isInitialized() && drainAudioProcessorsToEndOfStream()) {
            this.audioTrackUtil.handleEndOfStream(getWrittenFrames());
            this.bytesUntilNextAvSync = 0;
            this.handledEndOfStream = true;
        }
    }

    public void release() {
        reset();
        releaseKeepSessionIdAudioTrack();
        for (AudioProcessor reset : this.availableAudioProcessors) {
            reset.reset();
        }
        this.audioSessionId = 0;
        this.playing = false;
    }

    public void reset() {
        if (isInitialized()) {
            this.submittedPcmBytes = 0;
            this.submittedEncodedFrames = 0;
            this.writtenPcmBytes = 0;
            this.writtenEncodedFrames = 0;
            this.framesPerEncodedSample = 0;
            if (this.drainingPlaybackParameters != null) {
                this.playbackParameters = this.drainingPlaybackParameters;
                this.drainingPlaybackParameters = null;
            } else if (!this.playbackParametersCheckpoints.isEmpty()) {
                this.playbackParameters = ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getLast()).playbackParameters;
            }
            this.playbackParametersCheckpoints.clear();
            this.playbackParametersOffsetUs = 0;
            this.playbackParametersPositionUs = 0;
            this.inputBuffer = null;
            this.outputBuffer = null;
            for (int i = 0; i < this.audioProcessors.length; i++) {
                AudioProcessor audioProcessor = this.audioProcessors[i];
                audioProcessor.flush();
                this.outputBuffers[i] = audioProcessor.getOutput();
            }
            this.handledEndOfStream = false;
            this.drainingAudioProcessorIndex = -1;
            this.avSyncHeader = null;
            this.bytesUntilNextAvSync = 0;
            this.startMediaTimeState = 0;
            this.latencyUs = 0;
            resetSyncParams();
            if (this.audioTrack.getPlayState() == 3) {
                this.audioTrack.pause();
            }
            final android.media.AudioTrack audioTrack = this.audioTrack;
            this.audioTrack = null;
            this.audioTrackUtil.reconfigure(null, false);
            this.releasingConditionVariable.close();
            new Thread() {
                public void run() {
                    try {
                        audioTrack.flush();
                        audioTrack.release();
                    } finally {
                        AudioTrack.this.releasingConditionVariable.open();
                    }
                }
            }.start();
        }
    }

    public void setAudioAttributes(AudioAttributes audioAttributes) {
        if (!this.audioAttributes.equals(audioAttributes)) {
            this.audioAttributes = audioAttributes;
            if (!this.tunneling) {
                reset();
                this.audioSessionId = 0;
            }
        }
    }

    public void setAudioSessionId(int i) {
        if (this.audioSessionId != i) {
            this.audioSessionId = i;
            reset();
        }
    }

    public PlaybackParameters setPlaybackParameters(PlaybackParameters playbackParameters) {
        if (this.passthrough) {
            this.playbackParameters = PlaybackParameters.DEFAULT;
            return this.playbackParameters;
        }
        PlaybackParameters playbackParameters2 = new PlaybackParameters(this.sonicAudioProcessor.setSpeed(playbackParameters.speed), this.sonicAudioProcessor.setPitch(playbackParameters.pitch));
        Object access$200 = this.drainingPlaybackParameters != null ? this.drainingPlaybackParameters : !this.playbackParametersCheckpoints.isEmpty() ? ((PlaybackParametersCheckpoint) this.playbackParametersCheckpoints.getLast()).playbackParameters : this.playbackParameters;
        if (!playbackParameters2.equals(access$200)) {
            if (isInitialized()) {
                this.drainingPlaybackParameters = playbackParameters2;
            } else {
                this.playbackParameters = playbackParameters2;
            }
        }
        return this.playbackParameters;
    }

    public void setVolume(float f) {
        if (this.volume != f) {
            this.volume = f;
            setVolumeInternal();
        }
    }
}
