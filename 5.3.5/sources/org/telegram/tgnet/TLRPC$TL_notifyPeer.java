package org.telegram.tgnet;

public class TLRPC$TL_notifyPeer extends TLRPC$NotifyPeer {
    public static int constructor = -1613493288;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.peer = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
