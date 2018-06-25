package com.persianswitch.okhttp3.internal.framed;

import com.persianswitch.okio.Buffer;
import com.persianswitch.okio.BufferedSource;
import com.persianswitch.okio.ByteString;
import com.persianswitch.okio.ForwardingSource;
import com.persianswitch.okio.InflaterSource;
import com.persianswitch.okio.Okio;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Inflater;

class NameValueBlockReader {
    private int compressedLimit;
    private final InflaterSource inflaterSource;
    private final BufferedSource source = Okio.buffer(this.inflaterSource);

    /* renamed from: com.persianswitch.okhttp3.internal.framed.NameValueBlockReader$2 */
    class C07522 extends Inflater {
        C07522() {
        }

        public int inflate(byte[] buffer, int offset, int count) throws DataFormatException {
            int result = super.inflate(buffer, offset, count);
            if (result != 0 || !needsDictionary()) {
                return result;
            }
            setDictionary(Spdy3.DICTIONARY);
            return super.inflate(buffer, offset, count);
        }
    }

    public NameValueBlockReader(BufferedSource source) {
        this.inflaterSource = new InflaterSource(new ForwardingSource(source) {
            public long read(Buffer sink, long byteCount) throws IOException {
                if (NameValueBlockReader.this.compressedLimit == 0) {
                    return -1;
                }
                long read = super.read(sink, Math.min(byteCount, (long) NameValueBlockReader.this.compressedLimit));
                if (read == -1) {
                    return -1;
                }
                NameValueBlockReader.this.compressedLimit = (int) (((long) NameValueBlockReader.this.compressedLimit) - read);
                return read;
            }
        }, new C07522());
    }

    public List<Header> readNameValueBlock(int length) throws IOException {
        this.compressedLimit += length;
        int numberOfPairs = this.source.readInt();
        if (numberOfPairs < 0) {
            throw new IOException("numberOfPairs < 0: " + numberOfPairs);
        } else if (numberOfPairs > 1024) {
            throw new IOException("numberOfPairs > 1024: " + numberOfPairs);
        } else {
            List<Header> entries = new ArrayList(numberOfPairs);
            for (int i = 0; i < numberOfPairs; i++) {
                ByteString name = readByteString().toAsciiLowercase();
                ByteString values = readByteString();
                if (name.size() == 0) {
                    throw new IOException("name.size == 0");
                }
                entries.add(new Header(name, values));
            }
            doneReading();
            return entries;
        }
    }

    private ByteString readByteString() throws IOException {
        return this.source.readByteString((long) this.source.readInt());
    }

    private void doneReading() throws IOException {
        if (this.compressedLimit > 0) {
            this.inflaterSource.refill();
            if (this.compressedLimit != 0) {
                throw new IOException("compressedLimit > 0: " + this.compressedLimit);
            }
        }
    }

    public void close() throws IOException {
        this.source.close();
    }
}
