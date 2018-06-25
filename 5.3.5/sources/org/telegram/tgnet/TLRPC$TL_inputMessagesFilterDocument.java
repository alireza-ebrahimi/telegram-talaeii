package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterDocument extends TLRPC$MessagesFilter {
    public static int constructor = -1629621880;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
