package org.telegram.messenger.exoplayer2.extractor.mkv;

import android.support.v4.media.session.PlaybackStateCompat;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;

final class Sniffer {
    private static final int ID_EBML = 440786851;
    private static final int SEARCH_LENGTH = 1024;
    private int peekLength;
    private final ParsableByteArray scratch = new ParsableByteArray(8);

    public boolean sniff(ExtractorInput input) throws IOException, InterruptedException {
        long j;
        long inputLength = input.getLength();
        if (inputLength == -1 || inputLength > PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID) {
            j = PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID;
        } else {
            j = inputLength;
        }
        int bytesToSearch = (int) j;
        input.peekFully(this.scratch.data, 0, 4);
        long tag = this.scratch.readUnsignedInt();
        this.peekLength = 4;
        while (tag != 440786851) {
            int i = this.peekLength + 1;
            this.peekLength = i;
            if (i == bytesToSearch) {
                return false;
            }
            input.peekFully(this.scratch.data, 0, 1);
            tag = ((tag << 8) & -256) | ((long) (this.scratch.data[0] & 255));
        }
        long headerSize = readUint(input);
        long headerStart = (long) this.peekLength;
        if (headerSize == Long.MIN_VALUE || (inputLength != -1 && headerStart + headerSize >= inputLength)) {
            return false;
        }
        while (((long) this.peekLength) < headerStart + headerSize) {
            if (readUint(input) == Long.MIN_VALUE) {
                return false;
            }
            long size = readUint(input);
            if (size < 0 || size > 2147483647L) {
                return false;
            }
            if (size != 0) {
                input.advancePeekPosition((int) size);
                this.peekLength = (int) (((long) this.peekLength) + size);
            }
        }
        return ((long) this.peekLength) == headerStart + headerSize;
    }

    private long readUint(ExtractorInput input) throws IOException, InterruptedException {
        input.peekFully(this.scratch.data, 0, 1);
        int value = this.scratch.data[0] & 255;
        if (value == 0) {
            return Long.MIN_VALUE;
        }
        int mask = 128;
        int length = 0;
        while ((value & mask) == 0) {
            mask >>= 1;
            length++;
        }
        value &= mask ^ -1;
        input.peekFully(this.scratch.data, 1, length);
        for (int i = 0; i < length; i++) {
            value = (value << 8) + (this.scratch.data[i + 1] & 255);
        }
        this.peekLength += length + 1;
        return (long) value;
    }
}
