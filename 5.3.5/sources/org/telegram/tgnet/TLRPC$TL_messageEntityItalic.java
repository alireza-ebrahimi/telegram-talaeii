package org.telegram.tgnet;

public class TLRPC$TL_messageEntityItalic extends TLRPC$MessageEntity {
    public static int constructor = -2106619040;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.offset = stream.readInt32(exception);
        this.length = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.offset);
        stream.writeInt32(this.length);
    }
}
