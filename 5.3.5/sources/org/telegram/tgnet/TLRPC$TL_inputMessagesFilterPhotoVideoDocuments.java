package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterPhotoVideoDocuments extends TLRPC$MessagesFilter {
    public static int constructor = -648121413;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
