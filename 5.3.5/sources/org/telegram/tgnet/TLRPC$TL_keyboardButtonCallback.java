package org.telegram.tgnet;

public class TLRPC$TL_keyboardButtonCallback extends TLRPC$KeyboardButton {
    public static int constructor = 1748655686;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
        this.data = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
        stream.writeByteArray(this.data);
    }
}
