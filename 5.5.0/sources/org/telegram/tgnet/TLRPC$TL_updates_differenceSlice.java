package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.EncryptedMessage;
import org.telegram.tgnet.TLRPC.Message;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_updates_differenceSlice extends TLRPC$updates_Difference {
    public static int constructor = -1459938943;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                Message TLdeserialize = Message.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.new_messages.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    EncryptedMessage TLdeserialize2 = EncryptedMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.new_encrypted_messages.add(TLdeserialize2);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    readInt32 = abstractSerializedData.readInt32(z);
                    i2 = 0;
                    while (i2 < readInt32) {
                        TLRPC$Update TLdeserialize3 = TLRPC$Update.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize3 != null) {
                            this.other_updates.add(TLdeserialize3);
                            i2++;
                        } else {
                            return;
                        }
                    }
                    if (abstractSerializedData.readInt32(z) == 481674261) {
                        readInt32 = abstractSerializedData.readInt32(z);
                        i2 = 0;
                        while (i2 < readInt32) {
                            Chat TLdeserialize4 = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                            if (TLdeserialize4 != null) {
                                this.chats.add(TLdeserialize4);
                                i2++;
                            } else {
                                return;
                            }
                        }
                        if (abstractSerializedData.readInt32(z) == 481674261) {
                            i2 = abstractSerializedData.readInt32(z);
                            while (i < i2) {
                                User TLdeserialize5 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                                if (TLdeserialize5 != null) {
                                    this.users.add(TLdeserialize5);
                                    i++;
                                } else {
                                    return;
                                }
                            }
                            this.intermediate_state = TLRPC$TL_updates_state.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        } else if (z) {
                            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
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
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.new_messages.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((Message) this.new_messages.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        size = this.new_encrypted_messages.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((EncryptedMessage) this.new_encrypted_messages.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        size = this.other_updates.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((TLRPC$Update) this.other_updates.get(i)).serializeToStream(abstractSerializedData);
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
        this.intermediate_state.serializeToStream(abstractSerializedData);
    }
}
