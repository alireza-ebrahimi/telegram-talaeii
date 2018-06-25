package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterVoice extends TLRPC$MessagesFilter {
    public static int constructor = 1358283666;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
