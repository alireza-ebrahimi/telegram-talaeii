package org.telegram.tgnet;

public class TLRPC$TL_inputNotifyGeoChatPeer extends TLRPC$InputNotifyPeer {
    public static int constructor = 1301143240;
    public TLRPC$TL_inputGeoChat peer;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.peer = TLRPC$TL_inputGeoChat.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
