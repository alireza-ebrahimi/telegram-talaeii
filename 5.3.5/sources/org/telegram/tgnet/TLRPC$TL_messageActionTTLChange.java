package org.telegram.tgnet;

public class TLRPC$TL_messageActionTTLChange extends TLRPC$MessageAction {
    public static int constructor = 1431655762;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.ttl = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.ttl);
    }
}
