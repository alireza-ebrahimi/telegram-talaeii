package org.telegram.tgnet;

public class TLRPC$TL_decryptedMessageActionFlushHistory extends TLRPC$DecryptedMessageAction {
    public static int constructor = 1729750108;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
