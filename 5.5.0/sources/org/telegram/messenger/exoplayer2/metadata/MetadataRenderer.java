package org.telegram.messenger.exoplayer2.metadata;

import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import java.util.Arrays;
import org.telegram.messenger.exoplayer2.BaseRenderer;
import org.telegram.messenger.exoplayer2.ExoPlaybackException;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.FormatHolder;
import org.telegram.messenger.exoplayer2.util.Assertions;

public final class MetadataRenderer extends BaseRenderer implements Callback {
    private static final int MAX_PENDING_METADATA_COUNT = 5;
    private static final int MSG_INVOKE_RENDERER = 0;
    private final MetadataInputBuffer buffer;
    private MetadataDecoder decoder;
    private final MetadataDecoderFactory decoderFactory;
    private final FormatHolder formatHolder;
    private boolean inputStreamEnded;
    private final Output output;
    private final Handler outputHandler;
    private final Metadata[] pendingMetadata;
    private int pendingMetadataCount;
    private int pendingMetadataIndex;
    private final long[] pendingMetadataTimestamps;

    public interface Output {
        void onMetadata(Metadata metadata);
    }

    public MetadataRenderer(Output output, Looper looper) {
        this(output, looper, MetadataDecoderFactory.DEFAULT);
    }

    public MetadataRenderer(Output output, Looper looper, MetadataDecoderFactory metadataDecoderFactory) {
        super(4);
        this.output = (Output) Assertions.checkNotNull(output);
        this.outputHandler = looper == null ? null : new Handler(looper, this);
        this.decoderFactory = (MetadataDecoderFactory) Assertions.checkNotNull(metadataDecoderFactory);
        this.formatHolder = new FormatHolder();
        this.buffer = new MetadataInputBuffer();
        this.pendingMetadata = new Metadata[5];
        this.pendingMetadataTimestamps = new long[5];
    }

    private void flushPendingMetadata() {
        Arrays.fill(this.pendingMetadata, null);
        this.pendingMetadataIndex = 0;
        this.pendingMetadataCount = 0;
    }

    private void invokeRenderer(Metadata metadata) {
        if (this.outputHandler != null) {
            this.outputHandler.obtainMessage(0, metadata).sendToTarget();
        } else {
            invokeRendererInternal(metadata);
        }
    }

    private void invokeRendererInternal(Metadata metadata) {
        this.output.onMetadata(metadata);
    }

    public boolean handleMessage(Message message) {
        switch (message.what) {
            case 0:
                invokeRendererInternal((Metadata) message.obj);
                return true;
            default:
                throw new IllegalStateException();
        }
    }

    public boolean isEnded() {
        return this.inputStreamEnded;
    }

    public boolean isReady() {
        return true;
    }

    protected void onDisabled() {
        flushPendingMetadata();
        this.decoder = null;
    }

    protected void onPositionReset(long j, boolean z) {
        flushPendingMetadata();
        this.inputStreamEnded = false;
    }

    protected void onStreamChanged(Format[] formatArr, long j) {
        this.decoder = this.decoderFactory.createDecoder(formatArr[0]);
    }

    public void render(long j, long j2) {
        if (!this.inputStreamEnded && this.pendingMetadataCount < 5) {
            this.buffer.clear();
            if (readSource(this.formatHolder, this.buffer, false) == -4) {
                if (this.buffer.isEndOfStream()) {
                    this.inputStreamEnded = true;
                } else if (!this.buffer.isDecodeOnly()) {
                    this.buffer.subsampleOffsetUs = this.formatHolder.format.subsampleOffsetUs;
                    this.buffer.flip();
                    try {
                        int i = (this.pendingMetadataIndex + this.pendingMetadataCount) % 5;
                        this.pendingMetadata[i] = this.decoder.decode(this.buffer);
                        this.pendingMetadataTimestamps[i] = this.buffer.timeUs;
                        this.pendingMetadataCount++;
                    } catch (Exception e) {
                        throw ExoPlaybackException.createForRenderer(e, getIndex());
                    }
                }
            }
        }
        if (this.pendingMetadataCount > 0 && this.pendingMetadataTimestamps[this.pendingMetadataIndex] <= j) {
            invokeRenderer(this.pendingMetadata[this.pendingMetadataIndex]);
            this.pendingMetadata[this.pendingMetadataIndex] = null;
            this.pendingMetadataIndex = (this.pendingMetadataIndex + 1) % 5;
            this.pendingMetadataCount--;
        }
    }

    public int supportsFormat(Format format) {
        return this.decoderFactory.supportsFormat(format) ? 4 : 0;
    }
}
