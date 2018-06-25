package org.telegram.tgnet;

public class TLRPC$TL_botInlineResult extends TLRPC$BotInlineResult {
    public static int constructor = -1679053127;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.id = stream.readString(exception);
        this.type = stream.readString(exception);
        if ((this.flags & 2) != 0) {
            this.title = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.description = stream.readString(exception);
        }
        if ((this.flags & 8) != 0) {
            this.url = stream.readString(exception);
        }
        if ((this.flags & 16) != 0) {
            this.thumb_url = stream.readString(exception);
        }
        if ((this.flags & 32) != 0) {
            this.content_url = stream.readString(exception);
        }
        if ((this.flags & 32) != 0) {
            this.content_type = stream.readString(exception);
        }
        if ((this.flags & 64) != 0) {
            this.w = stream.readInt32(exception);
        }
        if ((this.flags & 64) != 0) {
            this.h = stream.readInt32(exception);
        }
        if ((this.flags & 128) != 0) {
            this.duration = stream.readInt32(exception);
        }
        this.send_message = TLRPC$BotInlineMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeString(this.id);
        stream.writeString(this.type);
        if ((this.flags & 2) != 0) {
            stream.writeString(this.title);
        }
        if ((this.flags & 4) != 0) {
            stream.writeString(this.description);
        }
        if ((this.flags & 8) != 0) {
            stream.writeString(this.url);
        }
        if ((this.flags & 16) != 0) {
            stream.writeString(this.thumb_url);
        }
        if ((this.flags & 32) != 0) {
            stream.writeString(this.content_url);
        }
        if ((this.flags & 32) != 0) {
            stream.writeString(this.content_type);
        }
        if ((this.flags & 64) != 0) {
            stream.writeInt32(this.w);
        }
        if ((this.flags & 64) != 0) {
            stream.writeInt32(this.h);
        }
        if ((this.flags & 128) != 0) {
            stream.writeInt32(this.duration);
        }
        this.send_message.serializeToStream(stream);
    }
}
