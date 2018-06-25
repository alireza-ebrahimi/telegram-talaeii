package org.telegram.tgnet;

public class TLRPC$TL_messages_readMentions extends TLObject {
    public static int constructor = 251759059;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_affectedHistory.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
