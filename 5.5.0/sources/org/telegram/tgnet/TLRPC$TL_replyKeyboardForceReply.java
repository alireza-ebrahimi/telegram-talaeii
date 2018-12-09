package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ReplyMarkup;

public class TLRPC$TL_replyKeyboardForceReply extends ReplyMarkup {
    public static int constructor = -200242528;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.single_use = (this.flags & 2) != 0;
        if ((this.flags & 4) == 0) {
            z2 = false;
        }
        this.selective = z2;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.single_use ? this.flags | 2 : this.flags & -3;
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        abstractSerializedData.writeInt32(this.flags);
    }
}
