package org.telegram.tgnet;

public class TLRPC$TL_langPackStringPluralized extends TLRPC$LangPackString {
    public static int constructor = 1816636575;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.key = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.zero_value = stream.readString(exception);
        }
        if ((this.flags & 2) != 0) {
            this.one_value = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.two_value = stream.readString(exception);
        }
        if ((this.flags & 8) != 0) {
            this.few_value = stream.readString(exception);
        }
        if ((this.flags & 16) != 0) {
            this.many_value = stream.readString(exception);
        }
        this.other_value = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeString(this.key);
        if ((this.flags & 1) != 0) {
            stream.writeString(this.zero_value);
        }
        if ((this.flags & 2) != 0) {
            stream.writeString(this.one_value);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.two_value);
        }
        if ((this.flags & 8) != 0) {
            stream.writeString(this.few_value);
        }
        if ((this.flags & 16) != 0) {
            stream.writeString(this.many_value);
        }
        stream.writeString(this.other_value);
    }
}
