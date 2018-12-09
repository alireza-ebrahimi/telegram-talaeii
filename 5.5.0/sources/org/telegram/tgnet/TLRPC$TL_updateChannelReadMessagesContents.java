package org.telegram.tgnet;

public class TLRPC$TL_updateChannelReadMessagesContents extends TLRPC$Update {
    public static int constructor = -1987495099;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.channel_id = abstractSerializedData.readInt32(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                this.messages.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
                i++;
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.channel_id);
        abstractSerializedData.writeInt32(481674261);
        int size = this.messages.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.messages.get(i)).intValue());
        }
    }
}
