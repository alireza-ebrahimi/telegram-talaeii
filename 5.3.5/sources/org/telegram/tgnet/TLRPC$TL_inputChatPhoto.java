package org.telegram.tgnet;

public class TLRPC$TL_inputChatPhoto extends TLRPC$InputChatPhoto {
    public static int constructor = -1991004873;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = TLRPC$InputPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.id.serializeToStream(stream);
    }
}
