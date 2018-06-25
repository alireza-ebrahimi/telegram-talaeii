package org.telegram.tgnet;

public class TLRPC$TL_inputPeerUser extends TLRPC$InputPeer {
    public static int constructor = 2072935910;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt64(this.access_hash);
    }
}
