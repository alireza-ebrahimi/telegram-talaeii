package org.telegram.messenger.audioinfo.mp3;

public class ID3v2FrameHeader {
    private int bodySize;
    private boolean compression;
    private int dataLengthIndicator;
    private boolean encryption;
    private String frameId;
    private int headerSize;
    private boolean unsynchronization;

    public ID3v2FrameHeader(ID3v2TagBody iD3v2TagBody) {
        boolean z = true;
        long position = iD3v2TagBody.getPosition();
        ID3v2DataInput data = iD3v2TagBody.getData();
        if (iD3v2TagBody.getTagHeader().getVersion() == 2) {
            this.frameId = new String(data.readFully(3), "ISO-8859-1");
        } else {
            this.frameId = new String(data.readFully(4), "ISO-8859-1");
        }
        if (iD3v2TagBody.getTagHeader().getVersion() == 2) {
            this.bodySize = (((data.readByte() & 255) << 16) | ((data.readByte() & 255) << 8)) | (data.readByte() & 255);
        } else if (iD3v2TagBody.getTagHeader().getVersion() == 3) {
            this.bodySize = data.readInt();
        } else {
            this.bodySize = data.readSyncsafeInt();
        }
        if (iD3v2TagBody.getTagHeader().getVersion() > 2) {
            int i;
            int i2;
            int i3;
            int i4;
            int i5;
            data.readByte();
            byte readByte = data.readByte();
            if (iD3v2TagBody.getTagHeader().getVersion() == 3) {
                i = 32;
                i2 = 64;
                i3 = 0;
                i4 = 128;
                i5 = 0;
            } else {
                i2 = 4;
                i5 = 2;
                i4 = 8;
                i = 64;
                i3 = 1;
            }
            this.compression = (i4 & readByte) != 0;
            this.unsynchronization = (readByte & i5) != 0;
            if ((readByte & i2) == 0) {
                z = false;
            }
            this.encryption = z;
            if (iD3v2TagBody.getTagHeader().getVersion() == 3) {
                if (this.compression) {
                    this.dataLengthIndicator = data.readInt();
                    this.bodySize -= 4;
                }
                if (this.encryption) {
                    data.readByte();
                    this.bodySize--;
                }
                if ((readByte & i) != 0) {
                    data.readByte();
                    this.bodySize--;
                }
            } else {
                if ((readByte & i) != 0) {
                    data.readByte();
                    this.bodySize--;
                }
                if (this.encryption) {
                    data.readByte();
                    this.bodySize--;
                }
                if ((readByte & i3) != 0) {
                    this.dataLengthIndicator = data.readSyncsafeInt();
                    this.bodySize -= 4;
                }
            }
        }
        this.headerSize = (int) (iD3v2TagBody.getPosition() - position);
    }

    public int getBodySize() {
        return this.bodySize;
    }

    public int getDataLengthIndicator() {
        return this.dataLengthIndicator;
    }

    public String getFrameId() {
        return this.frameId;
    }

    public int getHeaderSize() {
        return this.headerSize;
    }

    public boolean isCompression() {
        return this.compression;
    }

    public boolean isEncryption() {
        return this.encryption;
    }

    public boolean isPadding() {
        for (int i = 0; i < this.frameId.length(); i++) {
            if (this.frameId.charAt(0) != '\u0000') {
                return false;
            }
        }
        return this.bodySize == 0;
    }

    public boolean isUnsynchronization() {
        return this.unsynchronization;
    }

    public boolean isValid() {
        int i = 0;
        while (i < this.frameId.length()) {
            if ((this.frameId.charAt(i) < 'A' || this.frameId.charAt(i) > 'Z') && (this.frameId.charAt(i) < '0' || this.frameId.charAt(i) > '9')) {
                return false;
            }
            i++;
        }
        return this.bodySize > 0;
    }

    public String toString() {
        return String.format("%s[id=%s, bodysize=%d]", new Object[]{getClass().getSimpleName(), this.frameId, Integer.valueOf(this.bodySize)});
    }
}
