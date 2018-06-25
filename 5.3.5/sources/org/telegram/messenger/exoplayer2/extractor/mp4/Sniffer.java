package org.telegram.messenger.exoplayer2.extractor.mp4;

import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.boxes.sampleentry.VisualSampleEntry;
import java.io.IOException;
import org.telegram.messenger.exoplayer2.extractor.ExtractorInput;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

final class Sniffer {
    private static final int[] COMPATIBLE_BRANDS = new int[]{Util.getIntegerCodeForString("isom"), Util.getIntegerCodeForString("iso2"), Util.getIntegerCodeForString("iso3"), Util.getIntegerCodeForString("iso4"), Util.getIntegerCodeForString("iso5"), Util.getIntegerCodeForString("iso6"), Util.getIntegerCodeForString(VisualSampleEntry.TYPE3), Util.getIntegerCodeForString(VisualSampleEntry.TYPE6), Util.getIntegerCodeForString(VisualSampleEntry.TYPE7), Util.getIntegerCodeForString("mp41"), Util.getIntegerCodeForString("mp42"), Util.getIntegerCodeForString("3g2a"), Util.getIntegerCodeForString("3g2b"), Util.getIntegerCodeForString("3gr6"), Util.getIntegerCodeForString("3gs6"), Util.getIntegerCodeForString("3ge6"), Util.getIntegerCodeForString("3gg6"), Util.getIntegerCodeForString("M4V "), Util.getIntegerCodeForString("M4A "), Util.getIntegerCodeForString("f4v "), Util.getIntegerCodeForString("kddi"), Util.getIntegerCodeForString("M4VP"), Util.getIntegerCodeForString("qt  "), Util.getIntegerCodeForString("MSNV")};
    private static final int SEARCH_LENGTH = 4096;

    public static boolean sniffFragmented(ExtractorInput input) throws IOException, InterruptedException {
        return sniffInternal(input, true);
    }

    public static boolean sniffUnfragmented(ExtractorInput input) throws IOException, InterruptedException {
        return sniffInternal(input, false);
    }

    private static boolean sniffInternal(ExtractorInput input, boolean fragmented) throws IOException, InterruptedException {
        long inputLength = input.getLength();
        if (inputLength == -1 || inputLength > PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM) {
            inputLength = PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM;
        }
        int bytesToSearch = (int) inputLength;
        ParsableByteArray buffer = new ParsableByteArray(64);
        int bytesSearched = 0;
        boolean foundGoodFileType = false;
        boolean isFragmented = false;
        while (bytesSearched < bytesToSearch) {
            int headerSize = 8;
            buffer.reset(8);
            input.peekFully(buffer.data, 0, 8);
            long atomSize = buffer.readUnsignedInt();
            int atomType = buffer.readInt();
            if (atomSize == 1) {
                headerSize = 16;
                input.peekFully(buffer.data, 8, 8);
                buffer.setLimit(16);
                atomSize = buffer.readUnsignedLongToLong();
            } else if (atomSize == 0) {
                long endPosition = input.getLength();
                if (endPosition != -1) {
                    atomSize = (endPosition - input.getPosition()) + ((long) 8);
                }
            }
            if (atomSize >= ((long) headerSize)) {
                bytesSearched += headerSize;
                if (atomType != Atom.TYPE_moov) {
                    if (atomType != Atom.TYPE_moof && atomType != Atom.TYPE_mvex) {
                        if ((((long) bytesSearched) + atomSize) - ((long) headerSize) >= ((long) bytesToSearch)) {
                            break;
                        }
                        int atomDataSize = (int) (atomSize - ((long) headerSize));
                        bytesSearched += atomDataSize;
                        if (atomType == Atom.TYPE_ftyp) {
                            if (atomDataSize < 8) {
                                return false;
                            }
                            buffer.reset(atomDataSize);
                            input.peekFully(buffer.data, 0, atomDataSize);
                            int brandsCount = atomDataSize / 4;
                            for (int i = 0; i < brandsCount; i++) {
                                if (i == 1) {
                                    buffer.skipBytes(4);
                                } else if (isCompatibleBrand(buffer.readInt())) {
                                    foundGoodFileType = true;
                                    break;
                                }
                            }
                            if (!foundGoodFileType) {
                                return false;
                            }
                        } else if (atomDataSize != 0) {
                            input.advancePeekPosition(atomDataSize);
                        }
                    } else {
                        isFragmented = true;
                        break;
                    }
                }
            } else {
                return false;
            }
        }
        if (foundGoodFileType && fragmented == isFragmented) {
            return true;
        }
        return false;
    }

    private static boolean isCompatibleBrand(int brand) {
        if ((brand >>> 8) == Util.getIntegerCodeForString("3gp")) {
            return true;
        }
        for (int compatibleBrand : COMPATIBLE_BRANDS) {
            if (compatibleBrand == brand) {
                return true;
            }
        }
        return false;
    }

    private Sniffer() {
    }
}
