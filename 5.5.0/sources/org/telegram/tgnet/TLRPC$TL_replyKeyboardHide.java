package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_replyKeyboardHide extends ReplyMarkup {
    public static int constructor = -1606526075;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.selective = (this.flags & 4) != 0;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        abstractSerializedData.writeInt32(this.flags);
    }
}
