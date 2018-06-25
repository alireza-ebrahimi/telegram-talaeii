package org.telegram.tgnet;

public class TLRPC$TL_langPackStringDeleted extends TLRPC$LangPackString {
    public static int constructor = 695856818;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.key = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.key);
    }
}
