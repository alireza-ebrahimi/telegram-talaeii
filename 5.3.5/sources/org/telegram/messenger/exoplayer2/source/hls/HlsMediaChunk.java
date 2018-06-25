package org.telegram.messenger.exoplayer2.source.hls;

import android.text.TextUtils;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.C0907C;
import org.telegram.messenger.exoplayer2.Format;
import org.telegram.messenger.exoplayer2.extractor.DefaultExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.Extractor;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.extractor.mp3.Mp3Extractor;
import org.telegram.messenger.exoplayer2.extractor.mp4.FragmentedMp4Extractor;
import org.telegram.messenger.exoplayer2.extractor.ts.Ac3Extractor;
import org.telegram.messenger.exoplayer2.extractor.ts.AdtsExtractor;
import org.telegram.messenger.exoplayer2.extractor.ts.DefaultTsPayloadReaderFactory;
import org.telegram.messenger.exoplayer2.extractor.ts.TsExtractor;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.Metadata$Entry;
import org.telegram.messenger.exoplayer2.metadata.id3.Id3Decoder;
import org.telegram.messenger.exoplayer2.metadata.id3.PrivFrame;
import org.telegram.messenger.exoplayer2.source.chunk.MediaChunk;
import org.telegram.messenger.exoplayer2.source.hls.playlist.HlsMasterPlaylist.HlsUrl;
import org.telegram.messenger.exoplayer2.upstream.DataSource;
import org.telegram.messenger.exoplayer2.upstream.DataSpec;
import org.telegram.messenger.exoplayer2.util.MimeTypes;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.TimestampAdjuster;
import org.telegram.messenger.exoplayer2.util.Util;

final class HlsMediaChunk extends MediaChunk {
    private static final String AAC_FILE_EXTENSION = ".aac";
    private static final String AC3_FILE_EXTENSION = ".ac3";
    private static final String EC3_FILE_EXTENSION = ".ec3";
    private static final String M4_FILE_EXTENSION_PREFIX = ".m4";
    private static final String MP3_FILE_EXTENSION = ".mp3";
    private static final String MP4_FILE_EXTENSION = ".mp4";
    private static final String PRIV_TIMESTAMP_FRAME_OWNER = "com.apple.streaming.transportStreamTimestamp";
    private static final AtomicInteger UID_SOURCE = new AtomicInteger();
    private static final String VTT_FILE_EXTENSION = ".vtt";
    private static final String WEBVTT_FILE_EXTENSION = ".webvtt";
    private int bytesLoaded;
    public final int discontinuitySequenceNumber;
    private Extractor extractor;
    private HlsSampleStreamWrapper extractorOutput;
    public final HlsUrl hlsUrl;
    private final ParsableByteArray id3Data;
    private final Id3Decoder id3Decoder;
    private final DataSource initDataSource;
    private final DataSpec initDataSpec;
    private boolean initLoadCompleted;
    private int initSegmentBytesLoaded;
    private final boolean isEncrypted = (this.dataSource instanceof Aes128DataSource);
    private final boolean isMasterTimestampSource;
    private final boolean isPackedAudio;
    private final String lastPathSegment;
    private volatile boolean loadCanceled;
    private volatile boolean loadCompleted;
    private final List<Format> muxedCaptionFormats;
    private final boolean needNewExtractor;
    private final Extractor previousExtractor;
    private final boolean shouldSpliceIn;
    private final TimestampAdjuster timestampAdjuster;
    public final int uid;

    public HlsMediaChunk(DataSource dataSource, DataSpec dataSpec, DataSpec initDataSpec, HlsUrl hlsUrl, List<Format> muxedCaptionFormats, int trackSelectionReason, Object trackSelectionData, long startTimeUs, long endTimeUs, int chunkIndex, int discontinuitySequenceNumber, boolean isMasterTimestampSource, TimestampAdjuster timestampAdjuster, HlsMediaChunk previousChunk, byte[] encryptionKey, byte[] encryptionIv) {
        super(buildDataSource(dataSource, encryptionKey, encryptionIv), dataSpec, hlsUrl.format, trackSelectionReason, trackSelectionData, startTimeUs, endTimeUs, chunkIndex);
        this.discontinuitySequenceNumber = discontinuitySequenceNumber;
        this.initDataSpec = initDataSpec;
        this.hlsUrl = hlsUrl;
        this.muxedCaptionFormats = muxedCaptionFormats;
        this.isMasterTimestampSource = isMasterTimestampSource;
        this.timestampAdjuster = timestampAdjuster;
        this.lastPathSegment = dataSpec.uri.getLastPathSegment();
        boolean z = this.lastPathSegment.endsWith(AAC_FILE_EXTENSION) || this.lastPathSegment.endsWith(AC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(EC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(MP3_FILE_EXTENSION);
        this.isPackedAudio = z;
        if (previousChunk != null) {
            this.id3Decoder = previousChunk.id3Decoder;
            this.id3Data = previousChunk.id3Data;
            this.previousExtractor = previousChunk.extractor;
            this.shouldSpliceIn = previousChunk.hlsUrl != hlsUrl;
            z = previousChunk.discontinuitySequenceNumber != discontinuitySequenceNumber || this.shouldSpliceIn;
            this.needNewExtractor = z;
        } else {
            this.id3Decoder = this.isPackedAudio ? new Id3Decoder() : null;
            this.id3Data = this.isPackedAudio ? new ParsableByteArray(10) : null;
            this.previousExtractor = null;
            this.shouldSpliceIn = false;
            this.needNewExtractor = true;
        }
        this.initDataSource = dataSource;
        this.uid = UID_SOURCE.getAndIncrement();
    }

    public void init(HlsSampleStreamWrapper output) {
        this.extractorOutput = output;
        output.init(this.uid, this.shouldSpliceIn);
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public long bytesLoaded() {
        return (long) this.bytesLoaded;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public boolean isLoadCanceled() {
        return this.loadCanceled;
    }

    public void load() throws IOException, InterruptedException {
        if (this.extractor == null && !this.isPackedAudio) {
            this.extractor = createExtractor();
        }
        maybeLoadInitData();
        if (!this.loadCanceled) {
            loadMedia();
        }
    }

    private void maybeLoadInitData() throws IOException, InterruptedException {
        if (this.previousExtractor != this.extractor && !this.initLoadCompleted && this.initDataSpec != null) {
            DataSpec initSegmentDataSpec = this.initDataSpec.subrange((long) this.initSegmentBytesLoaded);
            ExtractorInput input;
            try {
                input = new DefaultExtractorInput(this.initDataSource, initSegmentDataSpec.absoluteStreamPosition, this.initDataSource.open(initSegmentDataSpec));
                int result = 0;
                while (result == 0) {
                    if (!this.loadCanceled) {
                        result = this.extractor.read(input, null);
                    }
                }
                this.initSegmentBytesLoaded = (int) (input.getPosition() - this.initDataSpec.absoluteStreamPosition);
                Util.closeQuietly(this.dataSource);
                this.initLoadCompleted = true;
            } catch (Throwable th) {
                Util.closeQuietly(this.dataSource);
            }
        }
    }

    private void loadMedia() throws IOException, InterruptedException {
        DataSpec loadDataSpec;
        boolean skipLoadedBytes;
        if (this.isEncrypted) {
            loadDataSpec = this.dataSpec;
            skipLoadedBytes = this.bytesLoaded != 0;
        } else {
            loadDataSpec = this.dataSpec.subrange((long) this.bytesLoaded);
            skipLoadedBytes = false;
        }
        if (!this.isMasterTimestampSource) {
            this.timestampAdjuster.waitUntilInitialized();
        } else if (this.timestampAdjuster.getFirstSampleTimestampUs() == Long.MAX_VALUE) {
            this.timestampAdjuster.setFirstSampleTimestampUs(this.startTimeUs);
        }
        ExtractorInput input;
        try {
            input = new DefaultExtractorInput(this.dataSource, loadDataSpec.absoluteStreamPosition, this.dataSource.open(loadDataSpec));
            if (this.extractor == null) {
                long id3Timestamp = peekId3PrivTimestamp(input);
                this.extractor = buildPackedAudioExtractor(id3Timestamp != C0907C.TIME_UNSET ? this.timestampAdjuster.adjustTsTimestamp(id3Timestamp) : this.startTimeUs);
            }
            if (skipLoadedBytes) {
                input.skipFully(this.bytesLoaded);
            }
            int result = 0;
            while (result == 0) {
                if (this.loadCanceled) {
                    break;
                }
                result = this.extractor.read(input, null);
            }
            this.bytesLoaded = (int) (input.getPosition() - this.dataSpec.absoluteStreamPosition);
            Util.closeQuietly(this.dataSource);
            this.loadCompleted = true;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
        }
    }

    private long peekId3PrivTimestamp(ExtractorInput input) throws IOException, InterruptedException {
        input.resetPeekPosition();
        if (!input.peekFully(this.id3Data.data, 0, 10, true)) {
            return C0907C.TIME_UNSET;
        }
        this.id3Data.reset(10);
        if (this.id3Data.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
            return C0907C.TIME_UNSET;
        }
        this.id3Data.skipBytes(3);
        int id3Size = this.id3Data.readSynchSafeInt();
        int requiredCapacity = id3Size + 10;
        if (requiredCapacity > this.id3Data.capacity()) {
            byte[] data = this.id3Data.data;
            this.id3Data.reset(requiredCapacity);
            System.arraycopy(data, 0, this.id3Data.data, 0, 10);
        }
        if (!input.peekFully(this.id3Data.data, 10, id3Size, true)) {
            return C0907C.TIME_UNSET;
        }
        Metadata metadata = this.id3Decoder.decode(this.id3Data.data, id3Size);
        if (metadata == null) {
            return C0907C.TIME_UNSET;
        }
        int metadataLength = metadata.length();
        for (int i = 0; i < metadataLength; i++) {
            Metadata$Entry frame = metadata.get(i);
            if (frame instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) frame;
                if (PRIV_TIMESTAMP_FRAME_OWNER.equals(privFrame.owner)) {
                    System.arraycopy(privFrame.privateData, 0, this.id3Data.data, 0, 8);
                    this.id3Data.reset(8);
                    return this.id3Data.readLong();
                }
            }
        }
        return C0907C.TIME_UNSET;
    }

    private static DataSource buildDataSource(DataSource dataSource, byte[] encryptionKey, byte[] encryptionIv) {
        return (encryptionKey == null || encryptionIv == null) ? dataSource : new Aes128DataSource(dataSource, encryptionKey, encryptionIv);
    }

    private Extractor createExtractor() {
        Extractor extractor;
        boolean usingNewExtractor = true;
        if (MimeTypes.TEXT_VTT.equals(this.hlsUrl.format.sampleMimeType) || this.lastPathSegment.endsWith(WEBVTT_FILE_EXTENSION) || this.lastPathSegment.endsWith(VTT_FILE_EXTENSION)) {
            extractor = new WebvttExtractor(this.trackFormat.language, this.timestampAdjuster);
        } else if (!this.needNewExtractor) {
            usingNewExtractor = false;
            extractor = this.previousExtractor;
        } else if (this.lastPathSegment.endsWith(MP4_FILE_EXTENSION) || this.lastPathSegment.startsWith(M4_FILE_EXTENSION_PREFIX, this.lastPathSegment.length() - 4)) {
            extractor = new FragmentedMp4Extractor(0, this.timestampAdjuster);
        } else {
            int esReaderFactoryFlags = 16;
            List<Format> closedCaptionFormats = this.muxedCaptionFormats;
            if (closedCaptionFormats != null) {
                esReaderFactoryFlags = 16 | 32;
            } else {
                closedCaptionFormats = Collections.emptyList();
            }
            String codecs = this.trackFormat.codecs;
            if (!TextUtils.isEmpty(codecs)) {
                if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(codecs))) {
                    esReaderFactoryFlags |= 2;
                }
                if (!"video/avc".equals(MimeTypes.getVideoMediaMimeType(codecs))) {
                    esReaderFactoryFlags |= 4;
                }
            }
            extractor = new TsExtractor(2, this.timestampAdjuster, new DefaultTsPayloadReaderFactory(esReaderFactoryFlags, closedCaptionFormats));
        }
        if (usingNewExtractor) {
            extractor.init(this.extractorOutput);
        }
        return extractor;
    }

    private Extractor buildPackedAudioExtractor(long startTimeUs) {
        Extractor extractor;
        if (this.lastPathSegment.endsWith(AAC_FILE_EXTENSION)) {
            extractor = new AdtsExtractor(startTimeUs);
        } else if (this.lastPathSegment.endsWith(AC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(EC3_FILE_EXTENSION)) {
            extractor = new Ac3Extractor(startTimeUs);
        } else if (this.lastPathSegment.endsWith(MP3_FILE_EXTENSION)) {
            extractor = new Mp3Extractor(0, startTimeUs);
        } else {
            throw new IllegalArgumentException("Unknown extension for audio file: " + this.lastPathSegment);
        }
        extractor.init(this.extractorOutput);
        return extractor;
    }
}
