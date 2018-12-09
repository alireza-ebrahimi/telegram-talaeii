package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageAction;

public class TLRPC$TL_messageActionChatCreate extends MessageAction {
    public static int constructor = -1503425638;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.title = abstractSerializedData.readString(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                this.users.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
                i++;
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.users.get(i)).intValue());
        }
    }
}
