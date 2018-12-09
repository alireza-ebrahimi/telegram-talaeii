package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.KeyboardButton;

public class TLRPC$TL_keyboardButtonRequestPhone extends KeyboardButton {
    public static int constructor = -1318425559;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
