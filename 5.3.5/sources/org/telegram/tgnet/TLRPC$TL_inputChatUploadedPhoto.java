package org.telegram.tgnet;

public class TLRPC$TL_inputChatUploadedPhoto extends TLRPC$InputChatPhoto {
    public static int constructor = -1837345356;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.file = TLRPC$InputFile.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.file.serializeToStream(stream);
    }
}
