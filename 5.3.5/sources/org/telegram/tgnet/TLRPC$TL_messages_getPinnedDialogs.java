package org.telegram.tgnet;

public class TLRPC$TL_messages_getPinnedDialogs extends TLObject {
    public static int constructor = -497756594;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_messages_peerDialogs.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
