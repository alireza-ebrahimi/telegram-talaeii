package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterMusic extends TLRPC$MessagesFilter {
    public static int constructor = 928101534;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
