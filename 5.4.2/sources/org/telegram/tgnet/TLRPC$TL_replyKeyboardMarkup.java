package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_replyKeyboardMarkup extends ReplyMarkup {
    public static int constructor = 889353612;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.flags = abstractSerializedData.readInt32(z);
        this.resize = (this.flags & 1) != 0;
        this.single_use = (this.flags & 2) != 0;
        this.selective = (this.flags & 4) != 0;
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
        this.flags = this.resize ? this.flags | 1 : this.flags & -2;
        this.flags = this.single_use ? this.flags | 2 : this.flags & -3;
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt32(481674261);
        int size = this.rows.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((TLRPC$TL_keyboardButtonRow) this.rows.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
