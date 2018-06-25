package org.telegram.tgnet;

public class TLRPC$TL_replyKeyboardForceReply extends TLRPC$ReplyMarkup {
    public static int constructor = -200242528;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.single_use = z;
        if ((this.flags & 4) == 0) {
            z2 = false;
        }
        this.selective = z2;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.single_use ? this.flags | 2 : this.flags & -3;
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        stream.writeInt32(this.flags);
    }
}
