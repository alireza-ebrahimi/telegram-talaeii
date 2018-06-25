package org.telegram.tgnet;

public class TLRPC$TL_messages_reportSpam extends TLObject {
    public static int constructor = -820669733;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
    }
}
