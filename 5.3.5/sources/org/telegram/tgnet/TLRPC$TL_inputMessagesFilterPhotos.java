package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterPhotos extends TLRPC$MessagesFilter {
    public static int constructor = -1777752804;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
