package org.telegram.messenger.exoplayer2.extractor.wav;

import android.util.Log;
import com.google.android.gms.common.data.DataBufferSafeParcelable;
import org.telegram.messenger.exoplayer2.ParserException;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.util.Assertions;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class WavHeaderReader {
    private static final String TAG = "WavHeaderReader";
    private static final int TYPE_PCM = 1;
    private static final int TYPE_WAVE_FORMAT_EXTENSIBLE = 65534;

    private static final class ChunkHeader {
        public static final int SIZE_IN_BYTES = 8;
        public final int id;
        public final long size;

        private ChunkHeader(int i, long j) {
            this.id = i;
            this.size = j;
        }

        public static ChunkHeader peek(ExtractorInput extractorInput, ParsableByteArray parsableByteArray) {
            extractorInput.peekFully(parsableByteArray.data, 0, 8);
            parsableByteArray.setPosition(0);
            return new ChunkHeader(parsableByteArray.readInt(), parsableByteArray.readLittleEndianUnsignedInt());
        }
    }

    WavHeaderReader() {
    }

    public static WavHeader peek(ExtractorInput extractorInput) {
        Assertions.checkNotNull(extractorInput);
        ParsableByteArray parsableByteArray = new ParsableByteArray(16);
        if (ChunkHeader.peek(extractorInput, parsableByteArray).id != Util.getIntegerCodeForString("RIFF")) {
            return null;
        }
        extractorInput.peekFully(parsableByteArray.data, 0, 4);
        parsableByteArray.setPosition(0);
        int readInt = parsableByteArray.readInt();
        if (readInt != Util.getIntegerCodeForString("WAVE")) {
            Log.e(TAG, "Unsupported RIFF format: " + readInt);
            return null;
        }
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.id != Util.getIntegerCodeForString("fmt ")) {
            extractorInput.advancePeekPosition((int) peek.size);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        Assertions.checkState(peek.size >= 16);
        extractorInput.peekFully(parsableByteArray.data, 0, 16);
        parsableByteArray.setPosition(0);
        int readLittleEndianUnsignedShort = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort2 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedIntToInt = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedIntToInt2 = parsableByteArray.readLittleEndianUnsignedIntToInt();
        int readLittleEndianUnsignedShort3 = parsableByteArray.readLittleEndianUnsignedShort();
        int readLittleEndianUnsignedShort4 = parsableByteArray.readLittleEndianUnsignedShort();
        int i = (readLittleEndianUnsignedShort2 * readLittleEndianUnsignedShort4) / 8;
        if (readLittleEndianUnsignedShort3 != i) {
            throw new ParserException("Expected block alignment: " + i + "; got: " + readLittleEndianUnsignedShort3);
        }
        i = Util.getPcmEncoding(readLittleEndianUnsignedShort4);
        if (i == 0) {
            Log.e(TAG, "Unsupported WAV bit depth: " + readLittleEndianUnsignedShort4);
            return null;
        } else if (readLittleEndianUnsignedShort == 1 || readLittleEndianUnsignedShort == TYPE_WAVE_FORMAT_EXTENSIBLE) {
            extractorInput.advancePeekPosition(((int) peek.size) - 16);
            return new WavHeader(readLittleEndianUnsignedShort2, readLittleEndianUnsignedIntToInt, readLittleEndianUnsignedIntToInt2, readLittleEndianUnsignedShort3, readLittleEndianUnsignedShort4, i);
        } else {
            Log.e(TAG, "Unsupported WAV format type: " + readLittleEndianUnsignedShort);
            return null;
        }
    }

    public static void skipToData(ExtractorInput extractorInput, WavHeader wavHeader) {
        Assertions.checkNotNull(extractorInput);
        Assertions.checkNotNull(wavHeader);
        extractorInput.resetPeekPosition();
        ParsableByteArray parsableByteArray = new ParsableByteArray(8);
        ChunkHeader peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        while (peek.id != Util.getIntegerCodeForString(DataBufferSafeParcelable.DATA_FIELD)) {
            Log.w(TAG, "Ignoring unknown WAV chunk: " + peek.id);
            long j = 8 + peek.size;
            if (peek.id == Util.getIntegerCodeForString("RIFF")) {
                j = 12;
            }
            if (j > 2147483647L) {
                throw new ParserException("Chunk is too large (~2GB+) to skip; id: " + peek.id);
            }
            extractorInput.skipFully((int) j);
            peek = ChunkHeader.peek(extractorInput, parsableByteArray);
        }
        extractorInput.skipFully(8);
        wavHeader.setDataBounds(extractorInput.getPosition(), peek.size);
    }
}
