package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionSetMessageTTL extends TLRPC$DecryptedMessageAction {
    public static int constructor = -1586283796;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.ttl_seconds = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.ttl_seconds);
    }
}
