package org.telegram.tgnet;

public class TLRPC$TL_document_old extends TLRPC$TL_document {
    public static int constructor = -1627626714;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.user_id = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        this.file_name = stream.readString(exception);
        this.mime_type = stream.readString(exception);
        this.size = stream.readInt32(exception);
        this.thumb = TLRPC$PhotoSize.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.dc_id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.user_id);
        stream.writeInt32(this.date);
        stream.writeString(this.file_name);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.size);
        this.thumb.serializeToStream(stream);
        stream.writeInt32(this.dc_id);
    }
}
