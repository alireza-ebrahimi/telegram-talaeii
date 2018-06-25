package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterEmpty extends TLRPC$MessagesFilter {
    public static int constructor = 1474492012;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
