package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionNotifyLayer extends TLRPC$DecryptedMessageAction {
    public static int constructor = -217806717;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.layer = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.layer);
    }
}
