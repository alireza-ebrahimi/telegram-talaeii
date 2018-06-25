package com.coremedia.iso;

import com.coremedia.iso.boxes.Box;
import com.coremedia.iso.boxes.Container;
import com.coremedia.iso.boxes.UserBox;
import com.googlecode.mp4parser.DataSource;
import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.logging.Logger;

public abstract class AbstractBoxParser implements BoxParser {
    private static Logger LOG = Logger.getLogger(AbstractBoxParser.class.getName());
    ThreadLocal<ByteBuffer> header = new C04161();

    /* renamed from: com.coremedia.iso.AbstractBoxParser$1 */
    class C04161 extends ThreadLocal<ByteBuffer> {
        C04161() {
        }

        protected ByteBuffer initialValue() {
            return ByteBuffer.allocate(32);
        }
    }

    public abstract Box createBox(String str, byte[] bArr, String str2);

    public Box parseBox(DataSource byteChannel, Container parent) throws IOException {
        long startPos = byteChannel.position();
        ((ByteBuffer) this.header.get()).rewind().limit(8);
        int b;
        do {
            b = byteChannel.read((ByteBuffer) this.header.get());
            if (b == 8) {
                ((ByteBuffer) this.header.get()).rewind();
                long size = IsoTypeReader.readUInt32((ByteBuffer) this.header.get());
                if (size >= 8 || size <= 1) {
                    long contentSize;
                    String type = IsoTypeReader.read4cc((ByteBuffer) this.header.get());
                    byte[] usertype = null;
                    if (size == 1) {
                        ((ByteBuffer) this.header.get()).limit(16);
                        byteChannel.read((ByteBuffer) this.header.get());
                        ((ByteBuffer) this.header.get()).position(8);
                        contentSize = IsoTypeReader.readUInt64((ByteBuffer) this.header.get()) - 16;
                    } else if (size == 0) {
                        contentSize = byteChannel.size() - byteChannel.position();
                        size = contentSize + 8;
                    } else {
                        contentSize = size - 8;
                    }
                    if (UserBox.TYPE.equals(type)) {
                        ((ByteBuffer) this.header.get()).limit(((ByteBuffer) this.header.get()).limit() + 16);
                        byteChannel.read((ByteBuffer) this.header.get());
                        usertype = new byte[16];
                        for (int i = ((ByteBuffer) this.header.get()).position() - 16; i < ((ByteBuffer) this.header.get()).position(); i++) {
                            usertype[i - (((ByteBuffer) this.header.get()).position() - 16)] = ((ByteBuffer) this.header.get()).get(i);
                        }
                        contentSize -= 16;
                    }
                    Box box = createBox(type, usertype, parent instanceof Box ? ((Box) parent).getType() : "");
                    box.setParent(parent);
                    ((ByteBuffer) this.header.get()).rewind();
                    box.parse(byteChannel, (ByteBuffer) this.header.get(), contentSize, this);
                    return box;
                }
                LOG.severe("Plausibility check failed: size < 8 (size = " + size + "). Stop parsing!");
                return null;
            }
        } while (b >= 0);
        byteChannel.position(startPos);
        throw new EOFException();
    }
}
