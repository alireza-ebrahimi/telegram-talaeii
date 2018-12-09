package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_replyInlineMarkup extends ReplyMarkup {
    public static int constructor = 1218642516;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                TLRPC$TL_keyboardButtonRow TLdeserialize = TLRPC$TL_keyboardButtonRow.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.rows.add(TLdeserialize);
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
        int size = this.rows.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_keyboardButtonRow) this.rows.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
