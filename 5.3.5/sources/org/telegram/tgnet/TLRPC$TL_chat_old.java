package org.telegram.tgnet;

public class TLRPC$TL_chat_old extends TLRPC$TL_chat {
    public static int constructor = 1855757255;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
        this.photo = TLRPC$ChatPhoto.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.participants_count = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.left = stream.readBool(exception);
        this.version = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
        this.photo.serializeToStream(stream);
        stream.writeInt32(this.participants_count);
        stream.writeInt32(this.date);
        stream.writeBool(this.left);
        stream.writeInt32(this.version);
    }
}
