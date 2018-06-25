package org.telegram.tgnet;

public class TLRPC$TL_messageActionChannelCreate extends TLRPC$MessageAction {
    public static int constructor = -1781355374;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.title = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.title);
    }
}
