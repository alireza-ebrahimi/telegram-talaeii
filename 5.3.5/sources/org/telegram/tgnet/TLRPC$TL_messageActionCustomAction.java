package org.telegram.tgnet;

public class TLRPC$TL_messageActionCustomAction extends TLRPC$MessageAction {
    public static int constructor = -85549226;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.message);
    }
}
