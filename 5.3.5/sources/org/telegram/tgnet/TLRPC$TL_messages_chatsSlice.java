package org.telegram.tgnet;

public class TLRPC$TL_messages_chatsSlice extends TLRPC$messages_Chats {
    public static int constructor = -1663561404;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.count = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Chat object = TLRPC$Chat.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.chats.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.count);
        stream.writeInt32(481674261);
        int count = this.chats.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$Chat) this.chats.get(a)).serializeToStream(stream);
        }
    }
}
