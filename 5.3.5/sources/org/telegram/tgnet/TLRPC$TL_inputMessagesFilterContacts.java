package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterContacts extends TLRPC$MessagesFilter {
    public static int constructor = -530392189;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
