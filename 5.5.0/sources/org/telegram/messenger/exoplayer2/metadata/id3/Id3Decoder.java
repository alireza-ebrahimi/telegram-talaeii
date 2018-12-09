package org.telegram.messenger.exoplayer2.metadata.id3;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.telegram.messenger.exoplayer2.C3446C;
import org.telegram.messenger.exoplayer2.metadata.Metadata;
import org.telegram.messenger.exoplayer2.metadata.MetadataDecoder;
import org.telegram.messenger.exoplayer2.metadata.MetadataInputBuffer;
import org.telegram.messenger.exoplayer2.util.ParsableByteArray;
import org.telegram.messenger.exoplayer2.util.Util;

public final class Id3Decoder implements MetadataDecoder {
    private static final int FRAME_FLAG_V3_HAS_GROUP_IDENTIFIER = 32;
    private static final int FRAME_FLAG_V3_IS_COMPRESSED = 128;
    private static final int FRAME_FLAG_V3_IS_ENCRYPTED = 64;
    private static final int FRAME_FLAG_V4_HAS_DATA_LENGTH = 1;
    private static final int FRAME_FLAG_V4_HAS_GROUP_IDENTIFIER = 64;
    private static final int FRAME_FLAG_V4_IS_COMPRESSED = 8;
    private static final int FRAME_FLAG_V4_IS_ENCRYPTED = 4;
    private static final int FRAME_FLAG_V4_IS_UNSYNCHRONIZED = 2;
    public static final int ID3_HEADER_LENGTH = 10;
    public static final int ID3_TAG = Util.getIntegerCodeForString("ID3");
    private static final int ID3_TEXT_ENCODING_ISO_8859_1 = 0;
    private static final int ID3_TEXT_ENCODING_UTF_16 = 1;
    private static final int ID3_TEXT_ENCODING_UTF_16BE = 2;
    private static final int ID3_TEXT_ENCODING_UTF_8 = 3;
    private static final String TAG = "Id3Decoder";
    private final FramePredicate framePredicate;

    public interface FramePredicate {
        boolean evaluate(int i, int i2, int i3, int i4, int i5);
    }

    private static final class Id3Header {
        private final int framesSize;
        private final boolean isUnsynchronized;
        private final int majorVersion;

        public Id3Header(int i, boolean z, int i2) {
            this.majorVersion = i;
            this.isUnsynchronized = z;
            this.framesSize = i2;
        }
    }

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(FramePredicate framePredicate) {
        this.framePredicate = framePredicate;
    }

    private static byte[] copyOfRangeIfValid(byte[] bArr, int i, int i2) {
        return i2 <= i ? new byte[0] : Arrays.copyOfRange(bArr, i, i2);
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray parsableByteArray, int i, int i2) {
        String str;
        int i3 = 2;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[(i - 1)];
        parsableByteArray.readBytes(bArr, 0, i - 1);
        if (i2 == 2) {
            str = "image/" + Util.toLowerInvariant(new String(bArr, 0, 3, "ISO-8859-1"));
            if (str.equals("image/jpg")) {
                str = "image/jpeg";
            }
        } else {
            i3 = indexOfZeroByte(bArr, 0);
            str = Util.toLowerInvariant(new String(bArr, 0, i3, "ISO-8859-1"));
            if (str.indexOf(47) == -1) {
                str = "image/" + str;
            }
        }
        int i4 = bArr[i3 + 1] & 255;
        i3 += 2;
        int indexOfEos = indexOfEos(bArr, i3, readUnsignedByte);
        return new ApicFrame(str, new String(bArr, i3, indexOfEos - i3, charsetName), i4, copyOfRangeIfValid(bArr, delimiterLength(readUnsignedByte) + indexOfEos, bArr.length));
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray parsableByteArray, int i, String str) {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new BinaryFrame(str, bArr);
    }

    private static ChapterFrame decodeChapterFrame(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, FramePredicate framePredicate) {
        int position = parsableByteArray.getPosition();
        int indexOfZeroByte = indexOfZeroByte(parsableByteArray.data, position);
        String str = new String(parsableByteArray.data, position, indexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(indexOfZeroByte + 1);
        indexOfZeroByte = parsableByteArray.readInt();
        int readInt = parsableByteArray.readInt();
        long readUnsignedInt = parsableByteArray.readUnsignedInt();
        if (readUnsignedInt == 4294967295L) {
            readUnsignedInt = -1;
        }
        long readUnsignedInt2 = parsableByteArray.readUnsignedInt();
        if (readUnsignedInt2 == 4294967295L) {
            readUnsignedInt2 = -1;
        }
        ArrayList arrayList = new ArrayList();
        position += i;
        while (parsableByteArray.getPosition() < position) {
            Id3Frame decodeFrame = decodeFrame(i2, parsableByteArray, z, i3, framePredicate);
            if (decodeFrame != null) {
                arrayList.add(decodeFrame);
            }
        }
        Id3Frame[] id3FrameArr = new Id3Frame[arrayList.size()];
        arrayList.toArray(id3FrameArr);
        return new ChapterFrame(str, indexOfZeroByte, readInt, readUnsignedInt, readUnsignedInt2, id3FrameArr);
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray parsableByteArray, int i, int i2, boolean z, int i3, FramePredicate framePredicate) {
        int position = parsableByteArray.getPosition();
        int indexOfZeroByte = indexOfZeroByte(parsableByteArray.data, position);
        String str = new String(parsableByteArray.data, position, indexOfZeroByte - position, "ISO-8859-1");
        parsableByteArray.setPosition(indexOfZeroByte + 1);
        indexOfZeroByte = parsableByteArray.readUnsignedByte();
        boolean z2 = (indexOfZeroByte & 2) != 0;
        boolean z3 = (indexOfZeroByte & 1) != 0;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String[] strArr = new String[readUnsignedByte];
        for (indexOfZeroByte = 0; indexOfZeroByte < readUnsignedByte; indexOfZeroByte++) {
            int position2 = parsableByteArray.getPosition();
            int indexOfZeroByte2 = indexOfZeroByte(parsableByteArray.data, position2);
            strArr[indexOfZeroByte] = new String(parsableByteArray.data, position2, indexOfZeroByte2 - position2, "ISO-8859-1");
            parsableByteArray.setPosition(indexOfZeroByte2 + 1);
        }
        ArrayList arrayList = new ArrayList();
        position += i;
        while (parsableByteArray.getPosition() < position) {
            Id3Frame decodeFrame = decodeFrame(i2, parsableByteArray, z, i3, framePredicate);
            if (decodeFrame != null) {
                arrayList.add(decodeFrame);
            }
        }
        Id3Frame[] id3FrameArr = new Id3Frame[arrayList.size()];
        arrayList.toArray(id3FrameArr);
        return new ChapterTocFrame(str, z2, z3, strArr, id3FrameArr);
    }

    private static CommentFrame decodeCommentFrame(ParsableByteArray parsableByteArray, int i) {
        if (i < 4) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[3];
        parsableByteArray.readBytes(bArr, 0, 3);
        String str = new String(bArr, 0, 3);
        bArr = new byte[(i - 4)];
        parsableByteArray.readBytes(bArr, 0, i - 4);
        int indexOfEos = indexOfEos(bArr, 0, readUnsignedByte);
        String str2 = new String(bArr, 0, indexOfEos, charsetName);
        indexOfEos += delimiterLength(readUnsignedByte);
        return new CommentFrame(str, str2, indexOfEos < bArr.length ? new String(bArr, indexOfEos, indexOfEos(bArr, indexOfEos, readUnsignedByte) - indexOfEos, charsetName) : TtmlNode.ANONYMOUS_REGION_ID);
    }

    private static Id3Frame decodeFrame(int i, ParsableByteArray parsableByteArray, boolean z, int i2, FramePredicate framePredicate) {
        int readUnsignedIntToInt;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        int readUnsignedByte3 = parsableByteArray.readUnsignedByte();
        int readUnsignedByte4 = i >= 3 ? parsableByteArray.readUnsignedByte() : 0;
        if (i == 4) {
            readUnsignedIntToInt = parsableByteArray.readUnsignedIntToInt();
            if (!z) {
                readUnsignedIntToInt = (((readUnsignedIntToInt & 255) | (((readUnsignedIntToInt >> 8) & 255) << 7)) | (((readUnsignedIntToInt >> 16) & 255) << 14)) | (((readUnsignedIntToInt >> 24) & 255) << 21);
            }
        } else {
            readUnsignedIntToInt = i == 3 ? parsableByteArray.readUnsignedIntToInt() : parsableByteArray.readUnsignedInt24();
        }
        int readUnsignedShort = i >= 3 ? parsableByteArray.readUnsignedShort() : 0;
        if (readUnsignedByte == 0 && readUnsignedByte2 == 0 && readUnsignedByte3 == 0 && readUnsignedByte4 == 0 && readUnsignedIntToInt == 0 && readUnsignedShort == 0) {
            parsableByteArray.setPosition(parsableByteArray.limit());
            return null;
        }
        int position = parsableByteArray.getPosition() + readUnsignedIntToInt;
        if (position > parsableByteArray.limit()) {
            Log.w(TAG, "Frame size exceeds remaining tag data");
            parsableByteArray.setPosition(parsableByteArray.limit());
            return null;
        } else if (framePredicate == null || framePredicate.evaluate(i, readUnsignedByte, readUnsignedByte2, readUnsignedByte3, readUnsignedByte4)) {
            Object obj = null;
            Object obj2 = null;
            Object obj3 = null;
            Object obj4 = null;
            Object obj5 = null;
            if (i == 3) {
                obj4 = (readUnsignedShort & 128) != 0 ? 1 : null;
                obj2 = (readUnsignedShort & 64) != 0 ? 1 : null;
                obj5 = (readUnsignedShort & 32) != 0 ? 1 : null;
                obj = obj4;
            } else if (i == 4) {
                obj4 = (readUnsignedShort & 64) != 0 ? 1 : null;
                obj = (readUnsignedShort & 8) != 0 ? 1 : null;
                obj2 = (readUnsignedShort & 4) != 0 ? 1 : null;
                obj3 = (readUnsignedShort & 2) != 0 ? 1 : null;
                Object obj6 = obj4;
                obj4 = (readUnsignedShort & 1) != 0 ? 1 : null;
                obj5 = obj6;
            }
            if (obj == null && r8 == null) {
                Id3Frame decodeTxxxFrame;
                if (obj5 != null) {
                    readUnsignedIntToInt--;
                    parsableByteArray.skipBytes(1);
                }
                if (obj4 != null) {
                    readUnsignedIntToInt -= 4;
                    parsableByteArray.skipBytes(4);
                }
                if (obj3 != null) {
                    readUnsignedIntToInt = removeUnsynchronization(parsableByteArray, readUnsignedIntToInt);
                }
                if (readUnsignedByte == 84 && readUnsignedByte2 == 88 && readUnsignedByte3 == 88 && (i == 2 || readUnsignedByte4 == 88)) {
                    try {
                        decodeTxxxFrame = decodeTxxxFrame(parsableByteArray, readUnsignedIntToInt);
                    } catch (UnsupportedEncodingException e) {
                        Log.w(TAG, "Unsupported character encoding");
                        parsableByteArray.setPosition(position);
                        return null;
                    } catch (Throwable th) {
                        parsableByteArray.setPosition(position);
                        throw th;
                    }
                }
                decodeTxxxFrame = readUnsignedByte == 84 ? decodeTextInformationFrame(parsableByteArray, readUnsignedIntToInt, getFrameId(i, readUnsignedByte, readUnsignedByte2, readUnsignedByte3, readUnsignedByte4)) : (readUnsignedByte == 87 && readUnsignedByte2 == 88 && readUnsignedByte3 == 88 && (i == 2 || readUnsignedByte4 == 88)) ? decodeWxxxFrame(parsableByteArray, readUnsignedIntToInt) : readUnsignedByte == 87 ? decodeUrlLinkFrame(parsableByteArray, readUnsignedIntToInt, getFrameId(i, readUnsignedByte, readUnsignedByte2, readUnsignedByte3, readUnsignedByte4)) : (readUnsignedByte == 80 && readUnsignedByte2 == 82 && readUnsignedByte3 == 73 && readUnsignedByte4 == 86) ? decodePrivFrame(parsableByteArray, readUnsignedIntToInt) : (readUnsignedByte == 71 && readUnsignedByte2 == 69 && readUnsignedByte3 == 79 && (readUnsignedByte4 == 66 || i == 2)) ? decodeGeobFrame(parsableByteArray, readUnsignedIntToInt) : (i != 2 ? !(readUnsignedByte == 65 && readUnsignedByte2 == 80 && readUnsignedByte3 == 73 && readUnsignedByte4 == 67) : !(readUnsignedByte == 80 && readUnsignedByte2 == 73 && readUnsignedByte3 == 67)) ? (readUnsignedByte == 67 && readUnsignedByte2 == 79 && readUnsignedByte3 == 77 && (readUnsignedByte4 == 77 || i == 2)) ? decodeCommentFrame(parsableByteArray, readUnsignedIntToInt) : (readUnsignedByte == 67 && readUnsignedByte2 == 72 && readUnsignedByte3 == 65 && readUnsignedByte4 == 80) ? decodeChapterFrame(parsableByteArray, readUnsignedIntToInt, i, z, i2, framePredicate) : (readUnsignedByte == 67 && readUnsignedByte2 == 84 && readUnsignedByte3 == 79 && readUnsignedByte4 == 67) ? decodeChapterTOCFrame(parsableByteArray, readUnsignedIntToInt, i, z, i2, framePredicate) : decodeBinaryFrame(parsableByteArray, readUnsignedIntToInt, getFrameId(i, readUnsignedByte, readUnsignedByte2, readUnsignedByte3, readUnsignedByte4)) : decodeApicFrame(parsableByteArray, readUnsignedIntToInt, i);
                if (decodeTxxxFrame == null) {
                    Log.w(TAG, "Failed to decode frame: id=" + getFrameId(i, readUnsignedByte, readUnsignedByte2, readUnsignedByte3, readUnsignedByte4) + ", frameSize=" + readUnsignedIntToInt);
                }
                parsableByteArray.setPosition(position);
                return decodeTxxxFrame;
            }
            Log.w(TAG, "Skipping unsupported compressed or encrypted frame");
            parsableByteArray.setPosition(position);
            return null;
        } else {
            parsableByteArray.setPosition(position);
            return null;
        }
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray parsableByteArray, int i) {
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[(i - 1)];
        parsableByteArray.readBytes(bArr, 0, i - 1);
        int indexOfZeroByte = indexOfZeroByte(bArr, 0);
        String str = new String(bArr, 0, indexOfZeroByte, "ISO-8859-1");
        indexOfZeroByte++;
        int indexOfEos = indexOfEos(bArr, indexOfZeroByte, readUnsignedByte);
        String str2 = new String(bArr, indexOfZeroByte, indexOfEos - indexOfZeroByte, charsetName);
        indexOfZeroByte = delimiterLength(readUnsignedByte) + indexOfEos;
        indexOfEos = indexOfEos(bArr, indexOfZeroByte, readUnsignedByte);
        return new GeobFrame(str, str2, new String(bArr, indexOfZeroByte, indexOfEos - indexOfZeroByte, charsetName), copyOfRangeIfValid(bArr, delimiterLength(readUnsignedByte) + indexOfEos, bArr.length));
    }

    private static Id3Header decodeHeader(ParsableByteArray parsableByteArray) {
        if (parsableByteArray.bytesLeft() < 10) {
            Log.w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int readUnsignedInt24 = parsableByteArray.readUnsignedInt24();
        if (readUnsignedInt24 != ID3_TAG) {
            Log.w(TAG, "Unexpected first three bytes of ID3 tag header: " + readUnsignedInt24);
            return null;
        }
        int i;
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        parsableByteArray.skipBytes(1);
        int readUnsignedByte2 = parsableByteArray.readUnsignedByte();
        readUnsignedInt24 = parsableByteArray.readSynchSafeInt();
        if (readUnsignedByte == 2) {
            if (((readUnsignedByte2 & 64) != 0 ? 1 : 0) != 0) {
                Log.w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
            i = readUnsignedInt24;
        } else if (readUnsignedByte == 3) {
            if (((readUnsignedByte2 & 64) != 0 ? 1 : 0) != 0) {
                i = parsableByteArray.readInt();
                parsableByteArray.skipBytes(i);
                readUnsignedInt24 -= i + 4;
            }
            i = readUnsignedInt24;
        } else if (readUnsignedByte == 4) {
            if (((readUnsignedByte2 & 64) != 0 ? 1 : 0) != 0) {
                i = parsableByteArray.readSynchSafeInt();
                parsableByteArray.skipBytes(i - 4);
                readUnsignedInt24 -= i;
            }
            if (((readUnsignedByte2 & 16) != 0 ? 1 : 0) != 0) {
                readUnsignedInt24 -= 10;
            }
            i = readUnsignedInt24;
        } else {
            Log.w(TAG, "Skipped ID3 tag with unsupported majorVersion=" + readUnsignedByte);
            return null;
        }
        boolean z = readUnsignedByte < 4 && (readUnsignedByte2 & 128) != 0;
        return new Id3Header(readUnsignedByte, z, i);
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray parsableByteArray, int i) {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        int indexOfZeroByte = indexOfZeroByte(bArr, 0);
        return new PrivFrame(new String(bArr, 0, indexOfZeroByte, "ISO-8859-1"), copyOfRangeIfValid(bArr, indexOfZeroByte + 1, bArr.length));
    }

    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray parsableByteArray, int i, String str) {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[(i - 1)];
        parsableByteArray.readBytes(bArr, 0, i - 1);
        return new TextInformationFrame(str, null, new String(bArr, 0, indexOfEos(bArr, 0, readUnsignedByte), charsetName));
    }

    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray parsableByteArray, int i) {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[(i - 1)];
        parsableByteArray.readBytes(bArr, 0, i - 1);
        int indexOfEos = indexOfEos(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, indexOfEos, charsetName);
        indexOfEos += delimiterLength(readUnsignedByte);
        return new TextInformationFrame("TXXX", str, indexOfEos < bArr.length ? new String(bArr, indexOfEos, indexOfEos(bArr, indexOfEos, readUnsignedByte) - indexOfEos, charsetName) : TtmlNode.ANONYMOUS_REGION_ID);
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray parsableByteArray, int i, String str) {
        byte[] bArr = new byte[i];
        parsableByteArray.readBytes(bArr, 0, i);
        return new UrlLinkFrame(str, null, new String(bArr, 0, indexOfZeroByte(bArr, 0), "ISO-8859-1"));
    }

    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray parsableByteArray, int i) {
        if (i < 1) {
            return null;
        }
        int readUnsignedByte = parsableByteArray.readUnsignedByte();
        String charsetName = getCharsetName(readUnsignedByte);
        byte[] bArr = new byte[(i - 1)];
        parsableByteArray.readBytes(bArr, 0, i - 1);
        int indexOfEos = indexOfEos(bArr, 0, readUnsignedByte);
        String str = new String(bArr, 0, indexOfEos, charsetName);
        int delimiterLength = indexOfEos + delimiterLength(readUnsignedByte);
        return new UrlLinkFrame("WXXX", str, delimiterLength < bArr.length ? new String(bArr, delimiterLength, indexOfZeroByte(bArr, delimiterLength) - delimiterLength, "ISO-8859-1") : TtmlNode.ANONYMOUS_REGION_ID);
    }

    private static int delimiterLength(int i) {
        return (i == 0 || i == 3) ? 1 : 2;
    }

    private static String getCharsetName(int i) {
        switch (i) {
            case 0:
                return "ISO-8859-1";
            case 1:
                return C3446C.UTF16_NAME;
            case 2:
                return "UTF-16BE";
            case 3:
                return C3446C.UTF8_NAME;
            default:
                return "ISO-8859-1";
        }
    }

    private static String getFrameId(int i, int i2, int i3, int i4, int i5) {
        if (i == 2) {
            return String.format(Locale.US, "%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4)});
        }
        return String.format(Locale.US, "%c%c%c%c", new Object[]{Integer.valueOf(i2), Integer.valueOf(i3), Integer.valueOf(i4), Integer.valueOf(i5)});
    }

    private static int indexOfEos(byte[] bArr, int i, int i2) {
        int indexOfZeroByte = indexOfZeroByte(bArr, i);
        if (i2 == 0 || i2 == 3) {
            return indexOfZeroByte;
        }
        while (indexOfZeroByte < bArr.length - 1) {
            if (indexOfZeroByte % 2 == 0 && bArr[indexOfZeroByte + 1] == (byte) 0) {
                return indexOfZeroByte;
            }
            indexOfZeroByte = indexOfZeroByte(bArr, indexOfZeroByte + 1);
        }
        return bArr.length;
    }

    private static int indexOfZeroByte(byte[] bArr, int i) {
        while (i < bArr.length) {
            if (bArr[i] == (byte) 0) {
                return i;
            }
            i++;
        }
        return bArr.length;
    }

    private static int removeUnsynchronization(ParsableByteArray parsableByteArray, int i) {
        Object obj = parsableByteArray.data;
        int position = parsableByteArray.getPosition();
        int i2 = i;
        while (position + 1 < i2) {
            if ((obj[position] & 255) == 255 && obj[position + 1] == (byte) 0) {
                System.arraycopy(obj, position + 2, obj, position + 1, (i2 - position) - 2);
                i2--;
            }
            position++;
        }
        return i2;
    }

    private static boolean validateFrames(ParsableByteArray parsableByteArray, int i, int i2, boolean z) {
        int position = parsableByteArray.getPosition();
        while (parsableByteArray.bytesLeft() >= i2) {
            int readUnsignedShort;
            int readInt;
            long j;
            long readUnsignedInt;
            if (i >= 3) {
                readUnsignedInt = parsableByteArray.readUnsignedInt();
                readUnsignedShort = parsableByteArray.readUnsignedShort();
                readInt = parsableByteArray.readInt();
                j = readUnsignedInt;
            } else {
                readUnsignedInt = (long) parsableByteArray.readUnsignedInt24();
                readUnsignedShort = 0;
                readInt = parsableByteArray.readUnsignedInt24();
                j = readUnsignedInt;
            }
            if (readInt == 0 && j == 0 && readUnsignedShort == 0) {
                parsableByteArray.setPosition(position);
                return true;
            }
            long j2;
            Object obj;
            Object obj2;
            if (i != 4 || z) {
                j2 = j;
            } else if ((8421504 & j) != 0) {
                parsableByteArray.setPosition(position);
                return false;
            } else {
                j2 = (((j >> 24) & 255) << 21) | (((255 & j) | (((j >> 8) & 255) << 7)) | (((j >> 16) & 255) << 14));
            }
            Object obj3;
            if (i == 4) {
                obj = (readUnsignedShort & 64) != 0 ? 1 : null;
                obj3 = (readUnsignedShort & 1) != 0 ? 1 : null;
                obj2 = obj;
                obj = obj3;
            } else if (i == 3) {
                obj = (readUnsignedShort & 32) != 0 ? 1 : null;
                obj3 = (readUnsignedShort & 128) != 0 ? 1 : null;
                obj2 = obj;
                obj = obj3;
            } else {
                obj2 = null;
                obj = null;
            }
            int i3 = 0;
            if (obj2 != null) {
                i3 = 1;
            }
            if (obj != null) {
                i3 += 4;
            }
            if (j2 < ((long) i3)) {
                parsableByteArray.setPosition(position);
                return false;
            } else if (((long) parsableByteArray.bytesLeft()) < j2) {
                return false;
            } else {
                try {
                    parsableByteArray.skipBytes((int) j2);
                } finally {
                    parsableByteArray.setPosition(position);
                }
            }
        }
        parsableByteArray.setPosition(position);
        return true;
    }

    public Metadata decode(MetadataInputBuffer metadataInputBuffer) {
        ByteBuffer byteBuffer = metadataInputBuffer.data;
        return decode(byteBuffer.array(), byteBuffer.limit());
    }

    public Metadata decode(byte[] bArr, int i) {
        List arrayList = new ArrayList();
        ParsableByteArray parsableByteArray = new ParsableByteArray(bArr, i);
        Id3Header decodeHeader = decodeHeader(parsableByteArray);
        if (decodeHeader == null) {
            return null;
        }
        boolean z;
        int position = parsableByteArray.getPosition();
        int i2 = decodeHeader.majorVersion == 2 ? 6 : 10;
        int access$100 = decodeHeader.framesSize;
        if (decodeHeader.isUnsynchronized) {
            access$100 = removeUnsynchronization(parsableByteArray, decodeHeader.framesSize);
        }
        parsableByteArray.setLimit(access$100 + position);
        if (validateFrames(parsableByteArray, decodeHeader.majorVersion, i2, false)) {
            z = false;
        } else if (decodeHeader.majorVersion == 4 && validateFrames(parsableByteArray, 4, i2, true)) {
            z = true;
        } else {
            Log.w(TAG, "Failed to validate ID3 tag with majorVersion=" + decodeHeader.majorVersion);
            return null;
        }
        while (parsableByteArray.bytesLeft() >= i2) {
            Id3Frame decodeFrame = decodeFrame(decodeHeader.majorVersion, parsableByteArray, z, i2, this.framePredicate);
            if (decodeFrame != null) {
                arrayList.add(decodeFrame);
            }
        }
        return new Metadata(arrayList);
    }
}
