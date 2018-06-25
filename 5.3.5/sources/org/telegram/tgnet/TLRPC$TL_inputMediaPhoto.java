package org.telegram.tgnet;

public class TLRPC$TL_inputMediaPhoto extends TLRPC$InputMedia {
    public static int constructor = -2114308294;
    public TLRPC$InputPhoto id;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = TLRPC$InputPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.ttl_seconds = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.id.serializeToStream(stream);
        stream.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.ttl_seconds);
        }
    }
}
