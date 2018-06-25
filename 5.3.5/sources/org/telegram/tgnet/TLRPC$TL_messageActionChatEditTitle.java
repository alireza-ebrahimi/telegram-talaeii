package org.telegram.tgnet;

public class TLRPC$TL_messageActionChatEditTitle extends TLRPC$MessageAction {
    public static int constructor = -1247687078;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.title = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.title);
    }
}
