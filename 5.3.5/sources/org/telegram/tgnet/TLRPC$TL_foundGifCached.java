package org.telegram.tgnet;

public class TLRPC$TL_foundGifCached extends TLRPC$FoundGif {
    public static int constructor = -1670052855;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.document = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.url);
        this.photo.serializeToStream(stream);
        this.document.serializeToStream(stream);
    }
}
