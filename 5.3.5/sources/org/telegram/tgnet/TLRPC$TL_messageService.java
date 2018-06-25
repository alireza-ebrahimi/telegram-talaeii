package org.telegram.tgnet;

public class TLRPC$TL_messageService extends TLRPC$Message {
    public static int constructor = -1642487306;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.unread = (this.flags & 1) != 0;
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
        if ((this.flags & 16384) == 0) {
            z2 = false;
        }
        this.post = z2;
        this.id = stream.readInt32(exception);
        if ((this.flags & 256) != 0) {
            this.from_id = stream.readInt32(exception);
        }
        this.to_id = TLRPC$Peer.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 8) != 0) {
            this.reply_to_msg_id = stream.readInt32(exception);
        }
        this.date = stream.readInt32(exception);
        this.action = TLRPC$MessageAction.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.unread ? this.flags | 1 : this.flags & -2;
        this.flags = this.out ? this.flags | 2 : this.flags & -3;
        this.flags = this.mentioned ? this.flags | 16 : this.flags & -17;
        this.flags = this.media_unread ? this.flags | 32 : this.flags & -33;
        this.flags = this.silent ? this.flags | 8192 : this.flags & -8193;
        this.flags = this.post ? this.flags | 16384 : this.flags & -16385;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        if ((this.flags & 256) != 0) {
            stream.writeInt32(this.from_id);
        }
        this.to_id.serializeToStream(stream);
        if ((this.flags & 8) != 0) {
            stream.writeInt32(this.reply_to_msg_id);
        }
        stream.writeInt32(this.date);
        this.action.serializeToStream(stream);
    }
}
