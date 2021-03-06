package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_contacts_topPeers extends TLRPC$contacts_TopPeers {
    public static int constructor = 1891070632;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_topPeerCategoryPeers object = TLRPC$TL_topPeerCategoryPeers.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.categories.add(object);
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
        int a;
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.categories.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_topPeerCategoryPeers) this.categories.get(a)).serializeToStream(stream);
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
