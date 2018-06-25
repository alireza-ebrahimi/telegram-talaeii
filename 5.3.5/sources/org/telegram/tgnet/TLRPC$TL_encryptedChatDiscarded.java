package org.telegram.tgnet;

public class TLRPC$TL_encryptedChatDiscarded extends TLRPC$EncryptedChat {
    public static int constructor = 332848423;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
    }
}
