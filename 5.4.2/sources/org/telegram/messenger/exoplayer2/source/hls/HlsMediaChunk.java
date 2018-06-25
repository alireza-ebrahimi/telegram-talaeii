package org.telegram.messenger.exoplayer2.source.hls;

import android.text.TextUtils;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import org.telegram.messenger.exoplayer2.C3446C;
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
import org.telegram.messenger.exoplayer2.metadata.Metadata.Entry;
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

    public HlsMediaChunk(DataSource dataSource, DataSpec dataSpec, DataSpec dataSpec2, HlsUrl hlsUrl, List<Format> list, int i, Object obj, long j, long j2, int i2, int i3, boolean z, TimestampAdjuster timestampAdjuster, HlsMediaChunk hlsMediaChunk, byte[] bArr, byte[] bArr2) {
        super(buildDataSource(dataSource, bArr, bArr2), dataSpec, hlsUrl.format, i, obj, j, j2, i2);
        this.discontinuitySequenceNumber = i3;
        this.initDataSpec = dataSpec2;
        this.hlsUrl = hlsUrl;
        this.muxedCaptionFormats = list;
        this.isMasterTimestampSource = z;
        this.timestampAdjuster = timestampAdjuster;
        this.lastPathSegment = dataSpec.uri.getLastPathSegment();
        boolean z2 = this.lastPathSegment.endsWith(AAC_FILE_EXTENSION) || this.lastPathSegment.endsWith(AC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(EC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(MP3_FILE_EXTENSION);
        this.isPackedAudio = z2;
        if (hlsMediaChunk != null) {
            this.id3Decoder = hlsMediaChunk.id3Decoder;
            this.id3Data = hlsMediaChunk.id3Data;
            this.previousExtractor = hlsMediaChunk.extractor;
            this.shouldSpliceIn = hlsMediaChunk.hlsUrl != hlsUrl;
            z2 = hlsMediaChunk.discontinuitySequenceNumber != i3 || this.shouldSpliceIn;
            this.needNewExtractor = z2;
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

    private static DataSource buildDataSource(DataSource dataSource, byte[] bArr, byte[] bArr2) {
        return (bArr == null || bArr2 == null) ? dataSource : new Aes128DataSource(dataSource, bArr, bArr2);
    }

    private Extractor buildPackedAudioExtractor(long j) {
        Extractor adtsExtractor;
        if (this.lastPathSegment.endsWith(AAC_FILE_EXTENSION)) {
            adtsExtractor = new AdtsExtractor(j);
        } else if (this.lastPathSegment.endsWith(AC3_FILE_EXTENSION) || this.lastPathSegment.endsWith(EC3_FILE_EXTENSION)) {
            adtsExtractor = new Ac3Extractor(j);
        } else if (this.lastPathSegment.endsWith(MP3_FILE_EXTENSION)) {
            adtsExtractor = new Mp3Extractor(0, j);
        } else {
            throw new IllegalArgumentException("Unknown extension for audio file: " + this.lastPathSegment);
        }
        adtsExtractor.init(this.extractorOutput);
        return adtsExtractor;
    }

    private Extractor createExtractor() {
        Extractor webvttExtractor;
        int i = 0;
        if (MimeTypes.TEXT_VTT.equals(this.hlsUrl.format.sampleMimeType) || this.lastPathSegment.endsWith(WEBVTT_FILE_EXTENSION) || this.lastPathSegment.endsWith(VTT_FILE_EXTENSION)) {
            webvttExtractor = new WebvttExtractor(this.trackFormat.language, this.timestampAdjuster);
            i = 1;
        } else if (!this.needNewExtractor) {
            webvttExtractor = this.previousExtractor;
        } else if (this.lastPathSegment.endsWith(MP4_FILE_EXTENSION) || this.lastPathSegment.startsWith(M4_FILE_EXTENSION_PREFIX, this.lastPathSegment.length() - 4)) {
            webvttExtractor = new FragmentedMp4Extractor(0, this.timestampAdjuster);
            i = 1;
        } else {
            int i2 = 16;
            List list = this.muxedCaptionFormats;
            if (list != null) {
                i2 = 48;
            } else {
                list = Collections.emptyList();
            }
            String str = this.trackFormat.codecs;
            if (!TextUtils.isEmpty(str)) {
                if (!MimeTypes.AUDIO_AAC.equals(MimeTypes.getAudioMediaMimeType(str))) {
                    i2 |= 2;
                }
                if (!"video/avc".equals(MimeTypes.getVideoMediaMimeType(str))) {
                    i2 |= 4;
                }
            }
            i = 1;
            Object tsExtractor = new TsExtractor(2, this.timestampAdjuster, new DefaultTsPayloadReaderFactory(i2, list));
        }
        if (i != 0) {
            webvttExtractor.init(this.extractorOutput);
        }
        return webvttExtractor;
    }

    private void loadMedia() {
        boolean z;
        DataSpec dataSpec;
        int i = 0;
        if (this.isEncrypted) {
            z = this.bytesLoaded != 0;
            dataSpec = this.dataSpec;
        } else {
            z = false;
            dataSpec = this.dataSpec.subrange((long) this.bytesLoaded);
        }
        if (!this.isMasterTimestampSource) {
            this.timestampAdjuster.waitUntilInitialized();
        } else if (this.timestampAdjuster.getFirstSampleTimestampUs() == Long.MAX_VALUE) {
            this.timestampAdjuster.setFirstSampleTimestampUs(this.startTimeUs);
        }
        ExtractorInput defaultExtractorInput;
        try {
            defaultExtractorInput = new DefaultExtractorInput(this.dataSource, dataSpec.absoluteStreamPosition, this.dataSource.open(dataSpec));
            if (this.extractor == null) {
                long peekId3PrivTimestamp = peekId3PrivTimestamp(defaultExtractorInput);
                this.extractor = buildPackedAudioExtractor(peekId3PrivTimestamp != C3446C.TIME_UNSET ? this.timestampAdjuster.adjustTsTimestamp(peekId3PrivTimestamp) : this.startTimeUs);
            }
            if (z) {
                defaultExtractorInput.skipFully(this.bytesLoaded);
            }
            while (i == 0) {
                if (this.loadCanceled) {
                    break;
                }
                i = this.extractor.read(defaultExtractorInput, null);
            }
            this.bytesLoaded = (int) (defaultExtractorInput.getPosition() - this.dataSpec.absoluteStreamPosition);
            Util.closeQuietly(this.dataSource);
            this.loadCompleted = true;
        } catch (Throwable th) {
            Util.closeQuietly(this.dataSource);
        }
    }

    private void maybeLoadInitData() {
        if (this.previousExtractor != this.extractor && !this.initLoadCompleted && this.initDataSpec != null) {
            DataSpec subrange = this.initDataSpec.subrange((long) this.initSegmentBytesLoaded);
            ExtractorInput defaultExtractorInput;
            try {
                defaultExtractorInput = new DefaultExtractorInput(this.initDataSource, subrange.absoluteStreamPosition, this.initDataSource.open(subrange));
                int i = 0;
                while (i == 0) {
                    if (!this.loadCanceled) {
                        i = this.extractor.read(defaultExtractorInput, null);
                    }
                }
                this.initSegmentBytesLoaded = (int) (defaultExtractorInput.getPosition() - this.initDataSpec.absoluteStreamPosition);
                Util.closeQuietly(this.dataSource);
                this.initLoadCompleted = true;
            } catch (Throwable th) {
                Util.closeQuietly(this.dataSource);
            }
        }
    }

    private long peekId3PrivTimestamp(ExtractorInput extractorInput) {
        extractorInput.resetPeekPosition();
        if (!extractorInput.peekFully(this.id3Data.data, 0, 10, true)) {
            return C3446C.TIME_UNSET;
        }
        this.id3Data.reset(10);
        if (this.id3Data.readUnsignedInt24() != Id3Decoder.ID3_TAG) {
            return C3446C.TIME_UNSET;
        }
        this.id3Data.skipBytes(3);
        int readSynchSafeInt = this.id3Data.readSynchSafeInt();
        int i = readSynchSafeInt + 10;
        if (i > this.id3Data.capacity()) {
            Object obj = this.id3Data.data;
            this.id3Data.reset(i);
            System.arraycopy(obj, 0, this.id3Data.data, 0, 10);
        }
        if (!extractorInput.peekFully(this.id3Data.data, 10, readSynchSafeInt, true)) {
            return C3446C.TIME_UNSET;
        }
        Metadata decode = this.id3Decoder.decode(this.id3Data.data, readSynchSafeInt);
        if (decode == null) {
            return C3446C.TIME_UNSET;
        }
        int length = decode.length();
        for (i = 0; i < length; i++) {
            Entry entry = decode.get(i);
            if (entry instanceof PrivFrame) {
                PrivFrame privFrame = (PrivFrame) entry;
                if (PRIV_TIMESTAMP_FRAME_OWNER.equals(privFrame.owner)) {
                    System.arraycopy(privFrame.privateData, 0, this.id3Data.data, 0, 8);
                    this.id3Data.reset(8);
                    return this.id3Data.readLong();
                }
            }
        }
        return C3446C.TIME_UNSET;
    }

    public long bytesLoaded() {
        return (long) this.bytesLoaded;
    }

    public void cancelLoad() {
        this.loadCanceled = true;
    }

    public void init(HlsSampleStreamWrapper hlsSampleStreamWrapper) {
        this.extractorOutput = hlsSampleStreamWrapper;
        hlsSampleStreamWrapper.init(this.uid, this.shouldSpliceIn);
    }

    public boolean isLoadCanceled() {
        return this.loadCanceled;
    }

    public boolean isLoadCompleted() {
        return this.loadCompleted;
    }

    public void load() {
        if (this.extractor == null && !this.isPackedAudio) {
            this.extractor = createExtractor();
        }
        maybeLoadInitData();
        if (!this.loadCanceled) {
            loadMedia();
        }
    }
}
