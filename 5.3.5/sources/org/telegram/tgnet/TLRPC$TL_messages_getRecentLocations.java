package org.telegram.tgnet;

public class TLRPC$TL_messages_getRecentLocations extends TLObject {
    public static int constructor = 613691874;
    public int limit;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$messages_Messages.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        stream.writeInt32(this.limit);
    }
}
