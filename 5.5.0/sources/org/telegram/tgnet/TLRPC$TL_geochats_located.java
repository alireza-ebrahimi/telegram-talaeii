package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Chat;
import org.telegram.tgnet.TLRPC.GeoChatMessage;
import org.telegram.tgnet.TLRPC.TL_chatLocated;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_geochats_located extends TLObject {
    public static int constructor = 1224651367;
    public ArrayList<Chat> chats = new ArrayList();
    public ArrayList<GeoChatMessage> messages = new ArrayList();
    public ArrayList<TL_chatLocated> results = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_geochats_located TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_geochats_located tLRPC$TL_geochats_located = new TLRPC$TL_geochats_located();
            tLRPC$TL_geochats_located.readParams(abstractSerializedData, z);
            return tLRPC$TL_geochats_located;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_geochats_located", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int i2;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            int readInt32 = abstractSerializedData.readInt32(z);
            i2 = 0;
            while (i2 < readInt32) {
                TL_chatLocated TLdeserialize = TL_chatLocated.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.results.add(TLdeserialize);
                    i2++;
                } else {
                    return;
                }
            }
            if (abstractSerializedData.readInt32(z) == 481674261) {
                readInt32 = abstractSerializedData.readInt32(z);
                i2 = 0;
                while (i2 < readInt32) {
                    GeoChatMessage TLdeserialize2 = GeoChatMessage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                    if (TLdeserialize2 != null) {
                        this.messages.add(TLdeserialize2);
                        i2++;
                    } else {
                        return;
                    }
                }
                if (abstractSerializedData.readInt32(z) == 481674261) {
                    readInt32 = abstractSerializedData.readInt32(z);
                    i2 = 0;
                    while (i2 < readInt32) {
                        Chat TLdeserialize3 = Chat.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                        if (TLdeserialize3 != null) {
                            this.chats.add(TLdeserialize3);
                            i2++;
                        } else {
                            return;
                        }
                    }
                    if (abstractSerializedData.readInt32(z) == 481674261) {
                        i2 = abstractSerializedData.readInt32(z);
                        while (i < i2) {
                            User TLdeserialize4 = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                            if (TLdeserialize4 != null) {
                                this.users.add(TLdeserialize4);
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
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(i2)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        int i;
        int i2 = 0;
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.results.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((TL_chatLocated) this.results.get(i)).serializeToStream(abstractSerializedData);
        }
        abstractSerializedData.writeInt32(481674261);
        size = this.messages.size();
        abstractSerializedData.writeInt32(size);
        for (i = 0; i < size; i++) {
            ((GeoChatMessage) this.messages.get(i)).serializeToStream(abstractSerializedData);
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
