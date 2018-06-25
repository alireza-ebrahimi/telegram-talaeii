package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessage extends TLRPC$DecryptedMessage {
    public static int constructor = -1848883596;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.random_id = stream.readInt64(exception);
        this.ttl = stream.readInt32(exception);
        this.message = stream.readString(exception);
        if ((this.flags & 512) != 0) {
            this.media = TLRPC$DecryptedMessageMedia.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 128) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    TLRPC$MessageEntity object = TLRPC$MessageEntity.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.entities.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            } else {
                return;
            }
        }
        if ((this.flags & 2048) != 0) {
            this.via_bot_name = stream.readString(exception);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_random_id = stream.readInt64(exception);
        }
        if ((this.flags & 131072) != 0) {
            this.grouped_id = stream.readInt64(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        stream.writeInt64(this.random_id);
        stream.writeInt32(this.ttl);
        stream.writeString(this.message);
        if ((this.flags & 512) != 0) {
            this.media.serializeToStream(stream);
        }
        if ((this.flags & 128) != 0) {
            stream.writeInt32(481674261);
            int count = this.entities.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$MessageEntity) this.entities.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 2048) != 0) {
            stream.writeString(this.via_bot_name);
        }
        if ((this.flags & 8) != 0) {
            stream.writeInt64(this.reply_to_random_id);
        }
        if ((this.flags & 131072) != 0) {
            stream.writeInt64(this.grouped_id);
        }
    }
}
