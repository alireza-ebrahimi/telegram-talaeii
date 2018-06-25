package com.crashlytics.android.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.List;

final class ByteString {
    public static final ByteString EMPTY = new ByteString(new byte[0]);
    private final byte[] bytes;
    private volatile int hash;

    static final class CodedBuilder {
        private final byte[] buffer;
        private final CodedOutputStream output;

        private CodedBuilder(int size) {
            this.buffer = new byte[size];
            this.output = CodedOutputStream.newInstance(this.buffer);
        }

        public ByteString build() {
            this.output.checkNoSpaceLeft();
            return new ByteString(this.buffer);
        }

        public CodedOutputStream getCodedOutput() {
            return this.output;
        }
    }

    static final class Output extends FilterOutputStream {
        private final ByteArrayOutputStream bout;

        private Output(ByteArrayOutputStream bout) {
            super(bout);
            this.bout = bout;
        }

        public ByteString toByteString() {
            return new ByteString(this.bout.toByteArray());
        }
    }

    private ByteString(byte[] bytes) {
        this.hash = 0;
        this.bytes = bytes;
    }

    public byte byteAt(int index) {
        return this.bytes[index];
    }

    public int size() {
        return this.bytes.length;
    }

    public boolean isEmpty() {
        return this.bytes.length == 0;
    }

    public static ByteString copyFrom(byte[] bytes, int offset, int size) {
        byte[] copy = new byte[size];
        System.arraycopy(bytes, offset, copy, 0, size);
        return new ByteString(copy);
    }

    public static ByteString copyFrom(byte[] bytes) {
        return copyFrom(bytes, 0, bytes.length);
    }

    public static ByteString copyFrom(ByteBuffer bytes, int size) {
        byte[] copy = new byte[size];
        bytes.get(copy);
        return new ByteString(copy);
    }

    public static ByteString copyFrom(ByteBuffer bytes) {
        return copyFrom(bytes, bytes.remaining());
    }

    public static ByteString copyFrom(String text, String charsetName) throws UnsupportedEncodingException {
        return new ByteString(text.getBytes(charsetName));
    }

    public static ByteString copyFromUtf8(String text) {
        try {
            return new ByteString(text.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported.", e);
        }
    }

    public static ByteString copyFrom(List<ByteString> list) {
        if (list.size() == 0) {
            return EMPTY;
        }
        if (list.size() == 1) {
            return (ByteString) list.get(0);
        }
        int size = 0;
        for (ByteString str : list) {
            size += str.size();
        }
        byte[] bytes = new byte[size];
        int pos = 0;
        for (ByteString str2 : list) {
            System.arraycopy(str2.bytes, 0, bytes, pos, str2.size());
            pos += str2.size();
        }
        return new ByteString(bytes);
    }

    public void copyTo(byte[] target, int offset) {
        System.arraycopy(this.bytes, 0, target, offset, this.bytes.length);
    }

    public void copyTo(byte[] target, int sourceOffset, int targetOffset, int size) {
        System.arraycopy(this.bytes, sourceOffset, target, targetOffset, size);
    }

    public void copyTo(ByteBuffer target) {
        target.put(this.bytes, 0, this.bytes.length);
    }

    public byte[] toByteArray() {
        int size = this.bytes.length;
        byte[] copy = new byte[size];
        System.arraycopy(this.bytes, 0, copy, 0, size);
        return copy;
    }

    public ByteBuffer asReadOnlyByteBuffer() {
        return ByteBuffer.wrap(this.bytes).asReadOnlyBuffer();
    }

    public String toString(String charsetName) throws UnsupportedEncodingException {
        return new String(this.bytes, charsetName);
    }

    public String toStringUtf8() {
        try {
            return new String(this.bytes, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("UTF-8 not supported?", e);
        }
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ByteString)) {
            return false;
        }
        ByteString other = (ByteString) o;
        int size = this.bytes.length;
        if (size != other.bytes.length) {
            return false;
        }
        byte[] thisBytes = this.bytes;
        byte[] otherBytes = other.bytes;
        for (int i = 0; i < size; i++) {
            if (thisBytes[i] != otherBytes[i]) {
                return false;
            }
        }
        return true;
    }

    public int hashCode() {
        int h = this.hash;
        if (h == 0) {
            byte[] thisBytes = this.bytes;
            int size = this.bytes.length;
            h = size;
            for (int i = 0; i < size; i++) {
                h = (h * 31) + thisBytes[i];
            }
            if (h == 0) {
                h = 1;
            }
            this.hash = h;
        }
        return h;
    }

    public InputStream newInput() {
        return new ByteArrayInputStream(this.bytes);
    }

    public static Output newOutput(int initialCapacity) {
        return new Output(new ByteArrayOutputStream(initialCapacity));
    }

    public static Output newOutput() {
        return newOutput(32);
    }

    static CodedBuilder newCodedBuilder(int size) {
        return new CodedBuilder(size);
    }
}
