package com.coremedia.iso.boxes.sampleentry;

import android.support.v4.media.session.PlaybackStateCompat;
import com.coremedia.iso.BoxParser;
import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import com.googlecode.mp4parser.DataSource;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.WritableByteChannel;
import java.util.Arrays;

public class TextSampleEntry extends AbstractSampleEntry {
    public static final String TYPE1 = "tx3g";
    public static final String TYPE_ENCRYPTED = "enct";
    private int[] backgroundColorRgba;
    private BoxRecord boxRecord;
    private long displayFlags;
    private int horizontalJustification;
    private StyleRecord styleRecord;
    private int verticalJustification;

    public static class BoxRecord {
        int bottom;
        int left;
        int right;
        int top;

        public BoxRecord(int top, int left, int bottom, int right) {
            this.top = top;
            this.left = left;
            this.bottom = bottom;
            this.right = right;
        }

        public void parse(ByteBuffer in) {
            this.top = IsoTypeReader.readUInt16(in);
            this.left = IsoTypeReader.readUInt16(in);
            this.bottom = IsoTypeReader.readUInt16(in);
            this.right = IsoTypeReader.readUInt16(in);
        }

        public void getContent(ByteBuffer bb) {
            IsoTypeWriter.writeUInt16(bb, this.top);
            IsoTypeWriter.writeUInt16(bb, this.left);
            IsoTypeWriter.writeUInt16(bb, this.bottom);
            IsoTypeWriter.writeUInt16(bb, this.right);
        }

        public int getSize() {
            return 8;
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            BoxRecord boxRecord = (BoxRecord) o;
            if (this.bottom != boxRecord.bottom) {
                return false;
            }
            if (this.left != boxRecord.left) {
                return false;
            }
            if (this.right != boxRecord.right) {
                return false;
            }
            if (this.top != boxRecord.top) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            return (((((this.top * 31) + this.left) * 31) + this.bottom) * 31) + this.right;
        }
    }

    public static class StyleRecord {
        int endChar;
        int faceStyleFlags;
        int fontId;
        int fontSize;
        int startChar;
        int[] textColor = new int[]{255, 255, 255, 255};

        public StyleRecord(int startChar, int endChar, int fontId, int faceStyleFlags, int fontSize, int[] textColor) {
            this.startChar = startChar;
            this.endChar = endChar;
            this.fontId = fontId;
            this.faceStyleFlags = faceStyleFlags;
            this.fontSize = fontSize;
            this.textColor = textColor;
        }

        public void parse(ByteBuffer in) {
            this.startChar = IsoTypeReader.readUInt16(in);
            this.endChar = IsoTypeReader.readUInt16(in);
            this.fontId = IsoTypeReader.readUInt16(in);
            this.faceStyleFlags = IsoTypeReader.readUInt8(in);
            this.fontSize = IsoTypeReader.readUInt8(in);
            this.textColor = new int[4];
            this.textColor[0] = IsoTypeReader.readUInt8(in);
            this.textColor[1] = IsoTypeReader.readUInt8(in);
            this.textColor[2] = IsoTypeReader.readUInt8(in);
            this.textColor[3] = IsoTypeReader.readUInt8(in);
        }

        public void getContent(ByteBuffer bb) {
            IsoTypeWriter.writeUInt16(bb, this.startChar);
            IsoTypeWriter.writeUInt16(bb, this.endChar);
            IsoTypeWriter.writeUInt16(bb, this.fontId);
            IsoTypeWriter.writeUInt8(bb, this.faceStyleFlags);
            IsoTypeWriter.writeUInt8(bb, this.fontSize);
            IsoTypeWriter.writeUInt8(bb, this.textColor[0]);
            IsoTypeWriter.writeUInt8(bb, this.textColor[1]);
            IsoTypeWriter.writeUInt8(bb, this.textColor[2]);
            IsoTypeWriter.writeUInt8(bb, this.textColor[3]);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            StyleRecord that = (StyleRecord) o;
            if (this.endChar != that.endChar) {
                return false;
            }
            if (this.faceStyleFlags != that.faceStyleFlags) {
                return false;
            }
            if (this.fontId != that.fontId) {
                return false;
            }
            if (this.fontSize != that.fontSize) {
                return false;
            }
            if (this.startChar != that.startChar) {
                return false;
            }
            if (Arrays.equals(this.textColor, that.textColor)) {
                return true;
            }
            return false;
        }

        public int hashCode() {
            return (((((((((this.startChar * 31) + this.endChar) * 31) + this.fontId) * 31) + this.faceStyleFlags) * 31) + this.fontSize) * 31) + (this.textColor != null ? Arrays.hashCode(this.textColor) : 0);
        }

        public int getSize() {
            return 12;
        }
    }

    public TextSampleEntry() {
        super(TYPE1);
        this.backgroundColorRgba = new int[4];
        this.boxRecord = new BoxRecord();
        this.styleRecord = new StyleRecord();
    }

    public TextSampleEntry(String type) {
        super(type);
        this.backgroundColorRgba = new int[4];
        this.boxRecord = new BoxRecord();
        this.styleRecord = new StyleRecord();
    }

    public void setType(String type) {
        this.type = type;
    }

    public void parse(DataSource dataSource, ByteBuffer header, long contentSize, BoxParser boxParser) throws IOException {
        ByteBuffer content = ByteBuffer.allocate(38);
        dataSource.read(content);
        content.position(6);
        this.dataReferenceIndex = IsoTypeReader.readUInt16(content);
        this.displayFlags = IsoTypeReader.readUInt32(content);
        this.horizontalJustification = IsoTypeReader.readUInt8(content);
        this.verticalJustification = IsoTypeReader.readUInt8(content);
        this.backgroundColorRgba = new int[4];
        this.backgroundColorRgba[0] = IsoTypeReader.readUInt8(content);
        this.backgroundColorRgba[1] = IsoTypeReader.readUInt8(content);
        this.backgroundColorRgba[2] = IsoTypeReader.readUInt8(content);
        this.backgroundColorRgba[3] = IsoTypeReader.readUInt8(content);
        this.boxRecord = new BoxRecord();
        this.boxRecord.parse(content);
        this.styleRecord = new StyleRecord();
        this.styleRecord.parse(content);
        initContainer(dataSource, contentSize - 38, boxParser);
    }

    public void getBox(WritableByteChannel writableByteChannel) throws IOException {
        writableByteChannel.write(getHeader());
        ByteBuffer byteBuffer = ByteBuffer.allocate(38);
        byteBuffer.position(6);
        IsoTypeWriter.writeUInt16(byteBuffer, this.dataReferenceIndex);
        IsoTypeWriter.writeUInt32(byteBuffer, this.displayFlags);
        IsoTypeWriter.writeUInt8(byteBuffer, this.horizontalJustification);
        IsoTypeWriter.writeUInt8(byteBuffer, this.verticalJustification);
        IsoTypeWriter.writeUInt8(byteBuffer, this.backgroundColorRgba[0]);
        IsoTypeWriter.writeUInt8(byteBuffer, this.backgroundColorRgba[1]);
        IsoTypeWriter.writeUInt8(byteBuffer, this.backgroundColorRgba[2]);
        IsoTypeWriter.writeUInt8(byteBuffer, this.backgroundColorRgba[3]);
        this.boxRecord.getContent(byteBuffer);
        this.styleRecord.getContent(byteBuffer);
        writableByteChannel.write((ByteBuffer) byteBuffer.rewind());
        writeContainer(writableByteChannel);
    }

    public String toString() {
        return "TextSampleEntry";
    }

    public BoxRecord getBoxRecord() {
        return this.boxRecord;
    }

    public void setBoxRecord(BoxRecord boxRecord) {
        this.boxRecord = boxRecord;
    }

    public StyleRecord getStyleRecord() {
        return this.styleRecord;
    }

    public void setStyleRecord(StyleRecord styleRecord) {
        this.styleRecord = styleRecord;
    }

    public boolean isScrollIn() {
        return (this.displayFlags & 32) == 32;
    }

    public void setScrollIn(boolean scrollIn) {
        if (scrollIn) {
            this.displayFlags |= 32;
        } else {
            this.displayFlags &= -33;
        }
    }

    public boolean isScrollOut() {
        return (this.displayFlags & 64) == 64;
    }

    public void setScrollOut(boolean scrollOutIn) {
        if (scrollOutIn) {
            this.displayFlags |= 64;
        } else {
            this.displayFlags &= -65;
        }
    }

    public boolean isScrollDirection() {
        return (this.displayFlags & 384) == 384;
    }

    public void setScrollDirection(boolean scrollOutIn) {
        if (scrollOutIn) {
            this.displayFlags |= 384;
        } else {
            this.displayFlags &= -385;
        }
    }

    public boolean isContinuousKaraoke() {
        return (this.displayFlags & PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH) == PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
    }

    public void setContinuousKaraoke(boolean continuousKaraoke) {
        if (continuousKaraoke) {
            this.displayFlags |= PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH;
        } else {
            this.displayFlags &= -2049;
        }
    }

    public boolean isWriteTextVertically() {
        return (this.displayFlags & PlaybackStateCompat.ACTION_PREPARE_FROM_URI) == PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
    }

    public void setWriteTextVertically(boolean writeTextVertically) {
        if (writeTextVertically) {
            this.displayFlags |= PlaybackStateCompat.ACTION_PREPARE_FROM_URI;
        } else {
            this.displayFlags &= -131073;
        }
    }

    public boolean isFillTextRegion() {
        return (this.displayFlags & PlaybackStateCompat.ACTION_SET_REPEAT_MODE) == PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
    }

    public void setFillTextRegion(boolean fillTextRegion) {
        if (fillTextRegion) {
            this.displayFlags |= PlaybackStateCompat.ACTION_SET_REPEAT_MODE;
        } else {
            this.displayFlags &= -262145;
        }
    }

    public int getHorizontalJustification() {
        return this.horizontalJustification;
    }

    public void setHorizontalJustification(int horizontalJustification) {
        this.horizontalJustification = horizontalJustification;
    }

    public int getVerticalJustification() {
        return this.verticalJustification;
    }

    public void setVerticalJustification(int verticalJustification) {
        this.verticalJustification = verticalJustification;
    }

    public int[] getBackgroundColorRgba() {
        return this.backgroundColorRgba;
    }

    public void setBackgroundColorRgba(int[] backgroundColorRgba) {
        this.backgroundColorRgba = backgroundColorRgba;
    }

    public long getSize() {
        long s = getContainerSize();
        long j = s + 38;
        int i = (this.largeBox || s + 38 >= 4294967296L) ? 16 : 8;
        return ((long) i) + j;
    }
}
