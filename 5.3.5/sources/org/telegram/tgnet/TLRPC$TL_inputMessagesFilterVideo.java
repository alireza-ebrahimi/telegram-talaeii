package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterVideo extends TLRPC$MessagesFilter {
    public static int constructor = -1614803355;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
