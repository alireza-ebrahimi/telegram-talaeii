package org.telegram.tgnet;

public class TLRPC$TL_keyboardButtonUrl extends TLRPC$KeyboardButton {
    public static int constructor = 629866245;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
        this.url = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
        stream.writeString(this.url);
    }
}
