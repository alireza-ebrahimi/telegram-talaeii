package org.telegram.messenger.audioinfo.mp3;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.apache.commons.lang3.CharEncoding;
import org.telegram.messenger.audioinfo.util.PositionInputStream;

public class ID3v2TagHeader {
    private boolean compression;
    private int footerSize;
    private int headerSize;
    private int paddingSize;
    private int revision;
    private int totalTagSize;
    private boolean unsynchronization;
    private int version;

    public ID3v2TagHeader(InputStream input) throws IOException, ID3v2Exception {
        this(new PositionInputStream(input));
    }

    ID3v2TagHeader(PositionInputStream input) throws IOException, ID3v2Exception {
        boolean z = true;
        this.version = 0;
        this.revision = 0;
        this.headerSize = 0;
        this.totalTagSize = 0;
        this.paddingSize = 0;
        this.footerSize = 0;
        long startPosition = input.getPosition();
        ID3v2DataInput data = new ID3v2DataInput(input);
        String id = new String(data.readFully(3), CharEncoding.ISO_8859_1);
        if ("ID3".equals(id)) {
            this.version = data.readByte();
            if (this.version == 2 || this.version == 3 || this.version == 4) {
                this.revision = data.readByte();
                byte flags = data.readByte();
                this.totalTagSize = data.readSyncsafeInt() + 10;
                if (this.version == 2) {
                    this.unsynchronization = (flags & 128) != 0;
                    if ((flags & 64) == 0) {
                        z = false;
                    }
                    this.compression = z;
                } else {
                    if ((flags & 128) == 0) {
                        z = false;
                    }
                    this.unsynchronization = z;
                    if ((flags & 64) != 0) {
                        if (this.version == 3) {
                            int extendedHeaderSize = data.readInt();
                            data.readByte();
                            data.readByte();
                            this.paddingSize = data.readInt();
                            data.skipFully((long) (extendedHeaderSize - 6));
                        } else {
                            data.skipFully((long) (data.readSyncsafeInt() - 4));
                        }
                    }
                    if (this.version >= 4 && (flags & 16) != 0) {
                        this.footerSize = 10;
                        this.totalTagSize += 10;
                    }
                }
                this.headerSize = (int) (input.getPosition() - startPosition);
                return;
            }
            throw new ID3v2Exception("Unsupported ID3v2 version: " + this.version);
        }
        throw new ID3v2Exception("Invalid ID3 identifier: " + id);
    }

    public ID3v2TagBody tagBody(InputStream input) throws IOException, ID3v2Exception {
        if (this.compression) {
            throw new ID3v2Exception("Tag compression is not supported");
        } else if (this.version >= 4 || !this.unsynchronization) {
            return new ID3v2TagBody(input, (long) this.headerSize, (this.totalTagSize - this.headerSize) - this.footerSize, this);
        } else {
            byte[] bytes = new ID3v2DataInput(input).readFully(this.totalTagSize - this.headerSize);
            boolean ff = false;
            int len = 0;
            for (byte b : bytes) {
                if (!(ff && b == (byte) 0)) {
                    int len2 = len + 1;
                    bytes[len] = b;
                    len = len2;
                }
                ff = b == (byte) -1;
            }
            return new ID3v2TagBody(new ByteArrayInputStream(bytes, 0, len), (long) this.headerSize, len, this);
        }
    }

    public int getVersion() {
        return this.version;
    }

    public int getRevision() {
        return this.revision;
    }

    public int getTotalTagSize() {
        return this.totalTagSize;
    }

    public boolean isUnsynchronization() {
        return this.unsynchronization;
    }

    public boolean isCompression() {
        return this.compression;
    }

    public int getHeaderSize() {
        return this.headerSize;
    }

    public int getFooterSize() {
        return this.footerSize;
    }

    public int getPaddingSize() {
        return this.paddingSize;
    }

    public String toString() {
        return String.format("%s[version=%s, totalTagSize=%d]", new Object[]{getClass().getSimpleName(), Integer.valueOf(this.version), Integer.valueOf(this.totalTagSize)});
    }
}
