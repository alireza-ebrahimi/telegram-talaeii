package org.telegram.tgnet;

public class TLRPC$TL_messageActionChatJoinedByLink extends TLRPC$MessageAction {
    public static int constructor = -123931160;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.inviter_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.inviter_id);
    }
}
