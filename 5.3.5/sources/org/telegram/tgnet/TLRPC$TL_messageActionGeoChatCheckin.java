package org.telegram.tgnet;

public class TLRPC$TL_messageActionGeoChatCheckin extends TLRPC$MessageAction {
    public static int constructor = 209540062;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
