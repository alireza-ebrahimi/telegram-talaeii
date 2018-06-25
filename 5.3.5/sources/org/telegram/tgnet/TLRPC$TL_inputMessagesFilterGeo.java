package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterGeo extends TLRPC$MessagesFilter {
    public static int constructor = -419271411;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
