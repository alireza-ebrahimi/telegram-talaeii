package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_messages_messages extends TLRPC$messages_Messages {
    public static int constructor = -1938715001;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                Message TLdeserialize = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.messages.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    Chat TLdeserialize2 = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.chats.add(TLdeserialize2);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    i2 = abstractSerializedData.readInt32(z);
                    while (i < i2) {
                        User TLdeserialize3 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize3 != null) {
                            this.users.add(TLdeserialize3);
                            i++;
                        } else {
                            return;
                        }
                    }
                } else if (z) {
                    throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
                }
            } else if (z) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.messages.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((Message) this.messages.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        size = this.chats.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((Chat) this.chats.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        i = this.users.size();
        abstractSerializedData.writeInt32(i);
        while (i2 < i) {
            ((User) this.users.get(i2)).serializeToStream(abstractSerializedData);
            i2++;
        }
    }
}
