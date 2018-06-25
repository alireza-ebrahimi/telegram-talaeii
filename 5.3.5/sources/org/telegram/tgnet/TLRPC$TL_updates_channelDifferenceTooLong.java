package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_updates_channelDifferenceTooLong extends TLRPC$updates_ChannelDifference {
    public static int constructor = 1788705589;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.isFinal = z;
        this.pts = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            this.timeout = stream.readInt32(exception);
        }
        this.top_message = stream.readInt32(exception);
        this.read_inbox_max_id = stream.readInt32(exception);
        this.read_outbox_max_id = stream.readInt32(exception);
        this.unread_count = stream.readInt32(exception);
        this.unread_mentions_count = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Message object = TLRPC$Message.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.messages.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$Chat object2 = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.chats.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        User object3 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object3 != null) {
                            this.users.add(object3);
                            a++;
                        } else {
                            return;
                        }
                    }
                } else if (exception) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        int a;
        stream.writeInt32(constructor);
        if (this.isFinal) {
            i = this.flags | 1;
        } else {
            i = this.flags & -2;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.pts);
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.timeout);
        }
        stream.writeInt32(this.top_message);
        stream.writeInt32(this.read_inbox_max_id);
        stream.writeInt32(this.read_outbox_max_id);
        stream.writeInt32(this.unread_count);
        stream.writeInt32(this.unread_mentions_count);
        stream.writeInt32(481674261);
        int count = this.messages.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Message) this.messages.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.chats.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Chat) this.chats.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
