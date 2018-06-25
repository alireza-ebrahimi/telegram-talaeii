package org.telegram.tgnet;

public class TLRPC$TL_messageEmpty extends TLRPC$Message {
    public static int constructor = -2082087340;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.to_id = new TLRPC$TL_peerUser();
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
    }
}
