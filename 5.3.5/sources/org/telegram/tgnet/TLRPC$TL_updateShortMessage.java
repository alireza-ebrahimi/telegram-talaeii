package org.telegram.tgnet;

public class TLRPC$TL_updateShortMessage extends TLRPC$Updates {
    public static int constructor = -1857044719;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.out = z;
        if ((this.flags & 16) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.mentioned = z;
        if ((this.flags & 32) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.media_unread = z;
        if ((this.flags & 8192) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.silent = z;
        this.id = stream.readInt32(exception);
        this.user_id = stream.readInt32(exception);
        this.message = stream.readString(exception);
        this.pts = stream.readInt32(exception);
        this.pts_count = stream.readInt32(exception);
        this.date = stream.readInt32(exception);
        if ((this.flags & 4) != 0) {
            this.fwd_from = TLRPC$MessageFwdHeader.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if ((this.flags & 2048) != 0) {
            this.via_bot_id = stream.readInt32(exception);
        }
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = stream.readInt32(exception);
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
            }
        }
    }
}
