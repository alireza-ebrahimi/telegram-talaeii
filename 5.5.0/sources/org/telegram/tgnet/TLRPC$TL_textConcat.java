package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_textConcat extends RichText {
    public static int constructor = 2120376535;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                RichText TLdeserialize = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.texts.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.texts.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((RichText) this.texts.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
