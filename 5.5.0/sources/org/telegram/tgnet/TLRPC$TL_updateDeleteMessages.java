package org.telegram.tgnet;

public class TLRPC$TL_updateDeleteMessages extends TLRPC$Update {
    public static int constructor = -1576161051;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                this.messages.add(Integer.valueOf(abstractSerializedData.readInt32(z)));
                i++;
            }
            this.pts = abstractSerializedData.readInt32(z);
            this.pts_count = abstractSerializedData.readInt32(z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.messages.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            abstractSerializedData.writeInt32(((Integer) this.messages.get(i)).intValue());
        }
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
    }
}
