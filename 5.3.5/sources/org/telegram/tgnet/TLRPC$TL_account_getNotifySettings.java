package org.telegram.tgnet;

public class TLRPC$TL_account_getNotifySettings extends TLObject {
    public static int constructor = 313765169;
    public TLRPC$InputNotifyPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$PeerNotifySettings.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
