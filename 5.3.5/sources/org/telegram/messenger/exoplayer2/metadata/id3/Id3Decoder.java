package org.telegram.messenger.exoplayer2.metadata.id3;

import android.util.Log;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import org.apache.commons.lang3.CharEncoding;
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

    public Id3Decoder() {
        this(null);
    }

    public Id3Decoder(FramePredicate framePredicate) {
        this.framePredicate = framePredicate;
    }

    public Metadata decode(MetadataInputBuffer inputBuffer) {
        ByteBuffer buffer = inputBuffer.data;
        return decode(buffer.array(), buffer.limit());
    }

    public Metadata decode(byte[] data, int size) {
        List id3Frames = new ArrayList();
        ParsableByteArray id3Data = new ParsableByteArray(data, size);
        Id3Decoder$Id3Header id3Header = decodeHeader(id3Data);
        if (id3Header == null) {
            return null;
        }
        int startPosition = id3Data.getPosition();
        int frameHeaderSize = Id3Decoder$Id3Header.access$000(id3Header) == 2 ? 6 : 10;
        int framesSize = Id3Decoder$Id3Header.access$100(id3Header);
        if (Id3Decoder$Id3Header.access$200(id3Header)) {
            framesSize = removeUnsynchronization(id3Data, Id3Decoder$Id3Header.access$100(id3Header));
        }
        id3Data.setLimit(startPosition + framesSize);
        boolean unsignedIntFrameSizeHack = false;
        if (!validateFrames(id3Data, Id3Decoder$Id3Header.access$000(id3Header), frameHeaderSize, false)) {
            if (Id3Decoder$Id3Header.access$000(id3Header) == 4 && validateFrames(id3Data, 4, frameHeaderSize, true)) {
                unsignedIntFrameSizeHack = true;
            } else {
                Log.w(TAG, "Failed to validate ID3 tag with majorVersion=" + Id3Decoder$Id3Header.access$000(id3Header));
                return null;
            }
        }
        while (id3Data.bytesLeft() >= frameHeaderSize) {
            Id3Frame frame = decodeFrame(Id3Decoder$Id3Header.access$000(id3Header), id3Data, unsignedIntFrameSizeHack, frameHeaderSize, this.framePredicate);
            if (frame != null) {
                id3Frames.add(frame);
            }
        }
        return new Metadata(id3Frames);
    }

    private static Id3Decoder$Id3Header decodeHeader(ParsableByteArray data) {
        boolean isUnsynchronized = true;
        if (data.bytesLeft() < 10) {
            Log.w(TAG, "Data too short to be an ID3 tag");
            return null;
        }
        int id = data.readUnsignedInt24();
        if (id != ID3_TAG) {
            Log.w(TAG, "Unexpected first three bytes of ID3 tag header: " + id);
            return null;
        }
        int majorVersion = data.readUnsignedByte();
        data.skipBytes(1);
        int flags = data.readUnsignedByte();
        int framesSize = data.readSynchSafeInt();
        if (majorVersion == 2) {
            boolean isCompressed;
            if ((flags & 64) != 0) {
                isCompressed = true;
            } else {
                isCompressed = false;
            }
            if (isCompressed) {
                Log.w(TAG, "Skipped ID3 tag with majorVersion=2 and undefined compression scheme");
                return null;
            }
        } else if (majorVersion == 3) {
            if ((flags & 64) != 0) {
                hasExtendedHeader = true;
            } else {
                hasExtendedHeader = false;
            }
            if (hasExtendedHeader) {
                extendedHeaderSize = data.readInt();
                data.skipBytes(extendedHeaderSize);
                framesSize -= extendedHeaderSize + 4;
            }
        } else if (majorVersion == 4) {
            boolean hasFooter;
            if ((flags & 64) != 0) {
                hasExtendedHeader = true;
            } else {
                hasExtendedHeader = false;
            }
            if (hasExtendedHeader) {
                extendedHeaderSize = data.readSynchSafeInt();
                data.skipBytes(extendedHeaderSize - 4);
                framesSize -= extendedHeaderSize;
            }
            if ((flags & 16) != 0) {
                hasFooter = true;
            } else {
                hasFooter = false;
            }
            if (hasFooter) {
                framesSize -= 10;
            }
        } else {
            Log.w(TAG, "Skipped ID3 tag with unsupported majorVersion=" + majorVersion);
            return null;
        }
        if (majorVersion >= 4 || (flags & 128) == 0) {
            isUnsynchronized = false;
        }
        return new Id3Decoder$Id3Header(majorVersion, isUnsynchronized, framesSize);
    }

    private static boolean validateFrames(ParsableByteArray id3Data, int majorVersion, int frameHeaderSize, boolean unsignedIntFrameSizeHack) {
        int startPosition = id3Data.getPosition();
        while (id3Data.bytesLeft() >= frameHeaderSize) {
            int id;
            long frameSize;
            int flags;
            if (majorVersion >= 3) {
                id = id3Data.readInt();
                frameSize = id3Data.readUnsignedInt();
                flags = id3Data.readUnsignedShort();
            } else {
                id = id3Data.readUnsignedInt24();
                frameSize = (long) id3Data.readUnsignedInt24();
                flags = 0;
            }
            if (id == 0 && frameSize == 0 && flags == 0) {
                id3Data.setPosition(startPosition);
                return true;
            }
            if (majorVersion == 4 && !unsignedIntFrameSizeHack) {
                if ((8421504 & frameSize) != 0) {
                    id3Data.setPosition(startPosition);
                    return false;
                }
                frameSize = (((255 & frameSize) | (((frameSize >> 8) & 255) << 7)) | (((frameSize >> 16) & 255) << 14)) | (((frameSize >> 24) & 255) << 21);
            }
            boolean hasGroupIdentifier = false;
            boolean hasDataLength = false;
            if (majorVersion == 4) {
                hasGroupIdentifier = (flags & 64) != 0;
                hasDataLength = (flags & 1) != 0;
            } else if (majorVersion == 3) {
                hasGroupIdentifier = (flags & 32) != 0;
                hasDataLength = (flags & 128) != 0;
            }
            int minimumFrameSize = 0;
            if (hasGroupIdentifier) {
                minimumFrameSize = 0 + 1;
            }
            if (hasDataLength) {
                minimumFrameSize += 4;
            }
            if (frameSize < ((long) minimumFrameSize)) {
                id3Data.setPosition(startPosition);
                return false;
            }
            try {
                if (((long) id3Data.bytesLeft()) < frameSize) {
                    return false;
                }
                id3Data.skipBytes((int) frameSize);
            } finally {
                id3Data.setPosition(startPosition);
            }
        }
        id3Data.setPosition(startPosition);
        return true;
    }

    private static Id3Frame decodeFrame(int majorVersion, ParsableByteArray id3Data, boolean unsignedIntFrameSizeHack, int frameHeaderSize, FramePredicate framePredicate) {
        int frameSize;
        int frameId0 = id3Data.readUnsignedByte();
        int frameId1 = id3Data.readUnsignedByte();
        int frameId2 = id3Data.readUnsignedByte();
        int frameId3 = majorVersion >= 3 ? id3Data.readUnsignedByte() : 0;
        if (majorVersion == 4) {
            frameSize = id3Data.readUnsignedIntToInt();
            if (!unsignedIntFrameSizeHack) {
                frameSize = (((frameSize & 255) | (((frameSize >> 8) & 255) << 7)) | (((frameSize >> 16) & 255) << 14)) | (((frameSize >> 24) & 255) << 21);
            }
        } else if (majorVersion == 3) {
            frameSize = id3Data.readUnsignedIntToInt();
        } else {
            frameSize = id3Data.readUnsignedInt24();
        }
        int flags = majorVersion >= 3 ? id3Data.readUnsignedShort() : 0;
        if (frameId0 == 0 && frameId1 == 0 && frameId2 == 0 && frameId3 == 0 && frameSize == 0 && flags == 0) {
            id3Data.setPosition(id3Data.limit());
            return null;
        }
        int nextFramePosition = id3Data.getPosition() + frameSize;
        if (nextFramePosition > id3Data.limit()) {
            Log.w(TAG, "Frame size exceeds remaining tag data");
            id3Data.setPosition(id3Data.limit());
            return null;
        } else if (framePredicate == null || framePredicate.evaluate(majorVersion, frameId0, frameId1, frameId2, frameId3)) {
            boolean isCompressed = false;
            boolean isEncrypted = false;
            boolean isUnsynchronized = false;
            boolean hasDataLength = false;
            boolean hasGroupIdentifier = false;
            if (majorVersion == 3) {
                isCompressed = (flags & 128) != 0;
                isEncrypted = (flags & 64) != 0;
                hasGroupIdentifier = (flags & 32) != 0;
                hasDataLength = isCompressed;
            } else if (majorVersion == 4) {
                hasGroupIdentifier = (flags & 64) != 0;
                isCompressed = (flags & 8) != 0;
                isEncrypted = (flags & 4) != 0;
                isUnsynchronized = (flags & 2) != 0;
                hasDataLength = (flags & 1) != 0;
            }
            if (isCompressed || isEncrypted) {
                Log.w(TAG, "Skipping unsupported compressed or encrypted frame");
                id3Data.setPosition(nextFramePosition);
                return null;
            }
            Id3Frame frame;
            if (hasGroupIdentifier) {
                frameSize--;
                id3Data.skipBytes(1);
            }
            if (hasDataLength) {
                frameSize -= 4;
                id3Data.skipBytes(4);
            }
            if (isUnsynchronized) {
                frameSize = removeUnsynchronization(id3Data, frameSize);
            }
            if (frameId0 == 84 && frameId1 == 88 && frameId2 == 88 && (majorVersion == 2 || frameId3 == 88)) {
                try {
                    frame = decodeTxxxFrame(id3Data, frameSize);
                } catch (UnsupportedEncodingException e) {
                    Log.w(TAG, "Unsupported character encoding");
                    id3Data.setPosition(nextFramePosition);
                    return null;
                } catch (Throwable th) {
                    id3Data.setPosition(nextFramePosition);
                    throw th;
                }
            } else if (frameId0 == 84) {
                frame = decodeTextInformationFrame(id3Data, frameSize, getFrameId(majorVersion, frameId0, frameId1, frameId2, frameId3));
            } else if (frameId0 == 87 && frameId1 == 88 && frameId2 == 88 && (majorVersion == 2 || frameId3 == 88)) {
                frame = decodeWxxxFrame(id3Data, frameSize);
            } else if (frameId0 == 87) {
                frame = decodeUrlLinkFrame(id3Data, frameSize, getFrameId(majorVersion, frameId0, frameId1, frameId2, frameId3));
            } else if (frameId0 == 80 && frameId1 == 82 && frameId2 == 73 && frameId3 == 86) {
                frame = decodePrivFrame(id3Data, frameSize);
            } else if (frameId0 == 71 && frameId1 == 69 && frameId2 == 79 && (frameId3 == 66 || majorVersion == 2)) {
                frame = decodeGeobFrame(id3Data, frameSize);
            } else if (!majorVersion != 2 ? !(frameId0 == 80 && frameId1 == 73 && frameId2 == 67) : !(frameId0 == 65 && frameId1 == 80 && frameId2 == 73 && frameId3 == 67)) {
                frame = decodeApicFrame(id3Data, frameSize, majorVersion);
            } else if (frameId0 == 67 && frameId1 == 79 && frameId2 == 77 && (frameId3 == 77 || majorVersion == 2)) {
                frame = decodeCommentFrame(id3Data, frameSize);
            } else if (frameId0 == 67 && frameId1 == 72 && frameId2 == 65 && frameId3 == 80) {
                frame = decodeChapterFrame(id3Data, frameSize, majorVersion, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            } else if (frameId0 == 67 && frameId1 == 84 && frameId2 == 79 && frameId3 == 67) {
                frame = decodeChapterTOCFrame(id3Data, frameSize, majorVersion, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            } else {
                frame = decodeBinaryFrame(id3Data, frameSize, getFrameId(majorVersion, frameId0, frameId1, frameId2, frameId3));
            }
            if (frame == null) {
                Log.w(TAG, "Failed to decode frame: id=" + getFrameId(majorVersion, frameId0, frameId1, frameId2, frameId3) + ", frameSize=" + frameSize);
            }
            id3Data.setPosition(nextFramePosition);
            return frame;
        } else {
            id3Data.setPosition(nextFramePosition);
            return null;
        }
    }

    private static TextInformationFrame decodeTxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        String value;
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int descriptionEndIndex = indexOfEos(data, 0, encoding);
        String description = new String(data, 0, descriptionEndIndex, charset);
        int valueStartIndex = descriptionEndIndex + delimiterLength(encoding);
        if (valueStartIndex < data.length) {
            value = new String(data, valueStartIndex, indexOfEos(data, valueStartIndex, encoding) - valueStartIndex, charset);
        } else {
            value = "";
        }
        return new TextInformationFrame("TXXX", description, value);
    }

    private static TextInformationFrame decodeTextInformationFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        return new TextInformationFrame(id, null, new String(data, 0, indexOfEos(data, 0, encoding), charset));
    }

    private static UrlLinkFrame decodeWxxxFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 1) {
            return null;
        }
        String url;
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int descriptionEndIndex = indexOfEos(data, 0, encoding);
        String description = new String(data, 0, descriptionEndIndex, charset);
        int urlStartIndex = descriptionEndIndex + delimiterLength(encoding);
        if (urlStartIndex < data.length) {
            url = new String(data, urlStartIndex, indexOfZeroByte(data, urlStartIndex) - urlStartIndex, CharEncoding.ISO_8859_1);
        } else {
            url = "";
        }
        return new UrlLinkFrame("WXXX", description, url);
    }

    private static UrlLinkFrame decodeUrlLinkFrame(ParsableByteArray id3Data, int frameSize, String id) throws UnsupportedEncodingException {
        byte[] data = new byte[frameSize];
        id3Data.readBytes(data, 0, frameSize);
        return new UrlLinkFrame(id, null, new String(data, 0, indexOfZeroByte(data, 0), CharEncoding.ISO_8859_1));
    }

    private static PrivFrame decodePrivFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        byte[] data = new byte[frameSize];
        id3Data.readBytes(data, 0, frameSize);
        int ownerEndIndex = indexOfZeroByte(data, 0);
        return new PrivFrame(new String(data, 0, ownerEndIndex, CharEncoding.ISO_8859_1), copyOfRangeIfValid(data, ownerEndIndex + 1, data.length));
    }

    private static GeobFrame decodeGeobFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        int mimeTypeEndIndex = indexOfZeroByte(data, 0);
        String mimeType = new String(data, 0, mimeTypeEndIndex, CharEncoding.ISO_8859_1);
        int filenameStartIndex = mimeTypeEndIndex + 1;
        int filenameEndIndex = indexOfEos(data, filenameStartIndex, encoding);
        String filename = new String(data, filenameStartIndex, filenameEndIndex - filenameStartIndex, charset);
        int descriptionStartIndex = filenameEndIndex + delimiterLength(encoding);
        int descriptionEndIndex = indexOfEos(data, descriptionStartIndex, encoding);
        return new GeobFrame(mimeType, filename, new String(data, descriptionStartIndex, descriptionEndIndex - descriptionStartIndex, charset), copyOfRangeIfValid(data, descriptionEndIndex + delimiterLength(encoding), data.length));
    }

    private static ApicFrame decodeApicFrame(ParsableByteArray id3Data, int frameSize, int majorVersion) throws UnsupportedEncodingException {
        int mimeTypeEndIndex;
        String mimeType;
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[(frameSize - 1)];
        id3Data.readBytes(data, 0, frameSize - 1);
        if (majorVersion == 2) {
            mimeTypeEndIndex = 2;
            mimeType = "image/" + Util.toLowerInvariant(new String(data, 0, 3, CharEncoding.ISO_8859_1));
            if (mimeType.equals("image/jpg")) {
                mimeType = "image/jpeg";
            }
        } else {
            mimeTypeEndIndex = indexOfZeroByte(data, 0);
            mimeType = Util.toLowerInvariant(new String(data, 0, mimeTypeEndIndex, CharEncoding.ISO_8859_1));
            if (mimeType.indexOf(47) == -1) {
                mimeType = "image/" + mimeType;
            }
        }
        int pictureType = data[mimeTypeEndIndex + 1] & 255;
        int descriptionStartIndex = mimeTypeEndIndex + 2;
        int descriptionEndIndex = indexOfEos(data, descriptionStartIndex, encoding);
        return new ApicFrame(mimeType, new String(data, descriptionStartIndex, descriptionEndIndex - descriptionStartIndex, charset), pictureType, copyOfRangeIfValid(data, descriptionEndIndex + delimiterLength(encoding), data.length));
    }

    private static CommentFrame decodeCommentFrame(ParsableByteArray id3Data, int frameSize) throws UnsupportedEncodingException {
        if (frameSize < 4) {
            return null;
        }
        String text;
        int encoding = id3Data.readUnsignedByte();
        String charset = getCharsetName(encoding);
        byte[] data = new byte[3];
        id3Data.readBytes(data, 0, 3);
        String language = new String(data, 0, 3);
        data = new byte[(frameSize - 4)];
        id3Data.readBytes(data, 0, frameSize - 4);
        int descriptionEndIndex = indexOfEos(data, 0, encoding);
        String description = new String(data, 0, descriptionEndIndex, charset);
        int textStartIndex = descriptionEndIndex + delimiterLength(encoding);
        if (textStartIndex < data.length) {
            text = new String(data, textStartIndex, indexOfEos(data, textStartIndex, encoding) - textStartIndex, charset);
        } else {
            text = "";
        }
        return new CommentFrame(language, description, text);
    }

    private static ChapterFrame decodeChapterFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, FramePredicate framePredicate) throws UnsupportedEncodingException {
        int framePosition = id3Data.getPosition();
        int chapterIdEndIndex = indexOfZeroByte(id3Data.data, framePosition);
        String chapterId = new String(id3Data.data, framePosition, chapterIdEndIndex - framePosition, CharEncoding.ISO_8859_1);
        id3Data.setPosition(chapterIdEndIndex + 1);
        int startTime = id3Data.readInt();
        int endTime = id3Data.readInt();
        long startOffset = id3Data.readUnsignedInt();
        if (startOffset == 4294967295L) {
            startOffset = -1;
        }
        long endOffset = id3Data.readUnsignedInt();
        if (endOffset == 4294967295L) {
            endOffset = -1;
        }
        ArrayList<Id3Frame> subFrames = new ArrayList();
        int limit = framePosition + frameSize;
        while (id3Data.getPosition() < limit) {
            Id3Frame frame = decodeFrame(majorVersion, id3Data, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            if (frame != null) {
                subFrames.add(frame);
            }
        }
        Id3Frame[] subFrameArray = new Id3Frame[subFrames.size()];
        subFrames.toArray(subFrameArray);
        return new ChapterFrame(chapterId, startTime, endTime, startOffset, endOffset, subFrameArray);
    }

    private static ChapterTocFrame decodeChapterTOCFrame(ParsableByteArray id3Data, int frameSize, int majorVersion, boolean unsignedIntFrameSizeHack, int frameHeaderSize, FramePredicate framePredicate) throws UnsupportedEncodingException {
        int framePosition = id3Data.getPosition();
        int elementIdEndIndex = indexOfZeroByte(id3Data.data, framePosition);
        String elementId = new String(id3Data.data, framePosition, elementIdEndIndex - framePosition, CharEncoding.ISO_8859_1);
        id3Data.setPosition(elementIdEndIndex + 1);
        int ctocFlags = id3Data.readUnsignedByte();
        boolean isRoot = (ctocFlags & 2) != 0;
        boolean isOrdered = (ctocFlags & 1) != 0;
        int childCount = id3Data.readUnsignedByte();
        String[] children = new String[childCount];
        for (int i = 0; i < childCount; i++) {
            int startIndex = id3Data.getPosition();
            int endIndex = indexOfZeroByte(id3Data.data, startIndex);
            children[i] = new String(id3Data.data, startIndex, endIndex - startIndex, CharEncoding.ISO_8859_1);
            id3Data.setPosition(endIndex + 1);
        }
        ArrayList<Id3Frame> subFrames = new ArrayList();
        int limit = framePosition + frameSize;
        while (id3Data.getPosition() < limit) {
            Id3Frame frame = decodeFrame(majorVersion, id3Data, unsignedIntFrameSizeHack, frameHeaderSize, framePredicate);
            if (frame != null) {
                subFrames.add(frame);
            }
        }
        Id3Frame[] subFrameArray = new Id3Frame[subFrames.size()];
        subFrames.toArray(subFrameArray);
        return new ChapterTocFrame(elementId, isRoot, isOrdered, children, subFrameArray);
    }

    private static BinaryFrame decodeBinaryFrame(ParsableByteArray id3Data, int frameSize, String id) {
        byte[] frame = new byte[frameSize];
        id3Data.readBytes(frame, 0, frameSize);
        return new BinaryFrame(id, frame);
    }

    private static int removeUnsynchronization(ParsableByteArray data, int length) {
        byte[] bytes = data.data;
        int i = data.getPosition();
        while (i + 1 < length) {
            if ((bytes[i] & 255) == 255 && bytes[i + 1] == (byte) 0) {
                System.arraycopy(bytes, i + 2, bytes, i + 1, (length - i) - 2);
                length--;
            }
            i++;
        }
        return length;
    }

    private static String getCharsetName(int encodingByte) {
        switch (encodingByte) {
            case 0:
                return CharEncoding.ISO_8859_1;
            case 1:
                return "UTF-16";
            case 2:
                return CharEncoding.UTF_16BE;
            case 3:
                return "UTF-8";
            default:
                return CharEncoding.ISO_8859_1;
        }
    }

    private static String getFrameId(int majorVersion, int frameId0, int frameId1, int frameId2, int frameId3) {
        if (majorVersion == 2) {
            return String.format(Locale.US, "%c%c%c", new Object[]{Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2)});
        }
        return String.format(Locale.US, "%c%c%c%c", new Object[]{Integer.valueOf(frameId0), Integer.valueOf(frameId1), Integer.valueOf(frameId2), Integer.valueOf(frameId3)});
    }

    private static int indexOfEos(byte[] data, int fromIndex, int encoding) {
        int terminationPos = indexOfZeroByte(data, fromIndex);
        if (encoding == 0 || encoding == 3) {
            return terminationPos;
        }
        while (terminationPos < data.length - 1) {
            if (terminationPos % 2 == 0 && data[terminationPos + 1] == (byte) 0) {
                return terminationPos;
            }
            terminationPos = indexOfZeroByte(data, terminationPos + 1);
        }
        return data.length;
    }

    private static int indexOfZeroByte(byte[] data, int fromIndex) {
        for (int i = fromIndex; i < data.length; i++) {
            if (data[i] == (byte) 0) {
                return i;
            }
        }
        return data.length;
    }

    private static int delimiterLength(int encodingByte) {
        return (encodingByte == 0 || encodingByte == 3) ? 1 : 2;
    }

    private static byte[] copyOfRangeIfValid(byte[] data, int from, int to) {
        if (to <= from) {
            return new byte[0];
        }
        return Arrays.copyOfRange(data, from, to);
    }
}
