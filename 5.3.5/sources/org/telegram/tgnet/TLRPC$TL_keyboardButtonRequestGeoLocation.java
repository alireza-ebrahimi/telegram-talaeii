package org.telegram.tgnet;

public class TLRPC$TL_keyboardButtonRequestGeoLocation extends TLRPC$KeyboardButton {
    public static int constructor = -59151553;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
    }
}
