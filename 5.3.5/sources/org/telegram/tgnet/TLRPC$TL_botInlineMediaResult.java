package org.telegram.tgnet;

public class TLRPC$TL_botInlineMediaResult extends TLRPC$BotInlineResult {
    public static int constructor = 400266251;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readString(exception);
        this.type = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            this.photo = TLRPC$Photo.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2) != 0) {
            this.document = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 4) != 0) {
            this.title = stream.readString(exception);
        }
        if ((this.flags & 8) != 0) {
            this.description = stream.readString(exception);
        }
        this.send_message = TLRPC$BotInlineMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeString(this.id);
        stream.writeString(this.type);
        if ((this.flags & 1) != 0) {
            this.photo.serializeToStream(stream);
        }
        if ((this.flags & 2) != 0) {
            this.document.serializeToStream(stream);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.title);
        }
        if ((this.flags & 8) != 0) {
            stream.writeString(this.description);
        }
        this.send_message.serializeToStream(stream);
    }
}
