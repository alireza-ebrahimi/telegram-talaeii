package org.telegram.tgnet;

public class TLRPC$TL_messages_setTyping extends TLObject {
    public static int constructor = -1551737264;
    public TLRPC$SendMessageAction action;
    public TLRPC$InputPeer peer;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$Bool.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.peer.serializeToStream(stream);
        this.action.serializeToStream(stream);
    }
}
