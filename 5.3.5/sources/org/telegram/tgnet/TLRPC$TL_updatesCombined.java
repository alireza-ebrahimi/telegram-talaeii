package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_updatesCombined extends TLRPC$Updates {
    public static int constructor = 1918567619;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Update object = TLRPC$Update.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.updates.add(object);
                    a++;
                } else {
                    return;
                }
            }
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
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        TLRPC$Chat object3 = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object3 != null) {
                            this.chats.add(object3);
                            a++;
                        } else {
                            return;
                        }
                    }
                    this.date = stream.readInt32(exception);
                    this.seq_start = stream.readInt32(exception);
                    this.seq = stream.readInt32(exception);
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
}
