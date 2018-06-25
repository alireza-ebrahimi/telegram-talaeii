package org.telegram.tgnet;

public class TLRPC$TL_keyboardButtonRequestPhone extends TLRPC$KeyboardButton {
    public static int constructor = -1318425559;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
    }
}
