package org.telegram.tgnet;

public class TLRPC$TL_peerChannel extends TLRPC$Peer {
    public static int constructor = -1109531342;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.channel_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.channel_id);
    }
}
