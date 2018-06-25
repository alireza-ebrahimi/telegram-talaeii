package org.telegram.tgnet;

public class TLRPC$TL_peerUser extends TLRPC$Peer {
    public static int constructor = -1649296275;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
    }
}
