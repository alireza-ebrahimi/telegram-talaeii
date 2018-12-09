package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.KeyboardButton;

public class TLRPC$TL_keyboardButton extends KeyboardButton {
    public static int constructor = -1560655744;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
