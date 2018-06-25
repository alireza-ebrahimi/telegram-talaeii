package org.telegram.tgnet;

public class TLRPC$TL_langPackString extends TLRPC$LangPackString {
    public static int constructor = -892239370;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.key = stream.readString(exception);
        this.value = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.key);
        stream.writeString(this.value);
    }
}
