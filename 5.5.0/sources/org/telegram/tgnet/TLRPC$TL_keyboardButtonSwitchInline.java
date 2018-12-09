package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.KeyboardButton;

public class TLRPC$TL_keyboardButtonSwitchInline extends KeyboardButton {
    public static int constructor = 90744648;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.flags = abstractSerializedData.readInt32(z);
        this.same_peer = (this.flags & 1) != 0;
        this.text = abstractSerializedData.readString(z);
        this.query = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.same_peer ? this.flags | 1 : this.flags & -2;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeString(this.text);
        abstractSerializedData.writeString(this.query);
    }
}
