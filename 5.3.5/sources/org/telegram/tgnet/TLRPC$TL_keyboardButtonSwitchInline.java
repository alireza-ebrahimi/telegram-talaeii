package org.telegram.tgnet;

public class TLRPC$TL_keyboardButtonSwitchInline extends TLRPC$KeyboardButton {
    public static int constructor = 90744648;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.same_peer = (this.flags & 1) != 0;
        this.text = stream.readString(exception);
        this.query = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.same_peer ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        stream.writeString(this.text);
        stream.writeString(this.query);
    }
}
