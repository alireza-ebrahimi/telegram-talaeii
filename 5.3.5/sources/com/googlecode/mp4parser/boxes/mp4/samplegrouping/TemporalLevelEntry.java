package com.googlecode.mp4parser.boxes.mp4.samplegrouping;

import java.nio.ByteBuffer;

public class TemporalLevelEntry extends GroupEntry {
    public static final String TYPE = "tele";
    private boolean levelIndependentlyDecodable;
    private short reserved;

    public String getType() {
        return TYPE;
    }

    public boolean isLevelIndependentlyDecodable() {
        return this.levelIndependentlyDecodable;
    }

    public void setLevelIndependentlyDecodable(boolean levelIndependentlyDecodable) {
        this.levelIndependentlyDecodable = levelIndependentlyDecodable;
    }

    public void parse(ByteBuffer byteBuffer) {
        this.levelIndependentlyDecodable = (byteBuffer.get() & 128) == 128;
    }

    public ByteBuffer get() {
        ByteBuffer content = ByteBuffer.allocate(1);
        content.put((byte) (this.levelIndependentlyDecodable ? 128 : 0));
        content.rewind();
        return content;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TemporalLevelEntry that = (TemporalLevelEntry) o;
        if (this.levelIndependentlyDecodable != that.levelIndependentlyDecodable) {
            return false;
        }
        if (this.reserved != that.reserved) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return ((this.levelIndependentlyDecodable ? 1 : 0) * 31) + this.reserved;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("TemporalLevelEntry");
        sb.append("{levelIndependentlyDecodable=").append(this.levelIndependentlyDecodable);
        sb.append('}');
        return sb.toString();
    }
}
