package org.telegram.tgnet;

public class TLRPC$TL_inputNotifyPeer extends TLRPC$InputNotifyPeer {
    public static int constructor = -1195615476;
    public TLRPC$InputPeer peer;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.peer = TLRPC$InputPeer.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
