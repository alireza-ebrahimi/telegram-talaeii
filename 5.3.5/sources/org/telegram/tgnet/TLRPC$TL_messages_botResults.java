package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_messages_botResults extends TLRPC$messages_BotResults {
    public static int constructor = -1803769784;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.gallery = z;
        this.query_id = stream.readInt64(exception);
        if ((this.flags & 2) != 0) {
            this.next_offset = stream.readString(exception);
        }
        if ((this.flags & 4) != 0) {
            this.switch_pm = TLRPC$TL_inlineBotSwitchPM.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$BotInlineResult object = TLRPC$BotInlineResult.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.results.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.cache_time = stream.readInt32(exception);
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    User object2 = User.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.users.add(object2);
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
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        int a;
        stream.writeInt32(constructor);
        if (this.gallery) {
            i = this.flags | 1;
        } else {
            i = this.flags & -2;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.query_id);
        if ((this.flags & 2) != 0) {
            stream.writeString(this.next_offset);
        }
        if ((this.flags & 4) != 0) {
            this.switch_pm.serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        int count = this.results.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$BotInlineResult) this.results.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.cache_time);
        stream.writeInt32(481674261);
        count = this.users.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((User) this.users.get(a)).serializeToStream(stream);
        }
    }
}
