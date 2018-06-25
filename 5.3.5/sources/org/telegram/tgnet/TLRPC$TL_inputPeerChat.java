package org.telegram.tgnet;

public class TLRPC$TL_inputPeerChat extends TLRPC$InputPeer {
    public static int constructor = 396093539;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
    }
}
