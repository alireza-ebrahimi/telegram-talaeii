package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterGif extends TLRPC$MessagesFilter {
    public static int constructor = -3644025;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
