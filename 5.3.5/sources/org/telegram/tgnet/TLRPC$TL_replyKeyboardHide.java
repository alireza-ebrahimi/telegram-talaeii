package org.telegram.tgnet;

public class TLRPC$TL_replyKeyboardHide extends TLRPC$ReplyMarkup {
    public static int constructor = -1606526075;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.selective = (this.flags & 4) != 0;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.selective ? this.flags | 4 : this.flags & -5;
        stream.writeInt32(this.flags);
    }
}
