package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.KeyboardButton;

public class TLRPC$TL_keyboardButtonBuy extends KeyboardButton {
    public static int constructor = -1344716869;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.text);
    }
}
