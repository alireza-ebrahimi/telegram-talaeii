package org.telegram.tgnet;

public class TLRPC$TL_inputDocumentEmpty extends TLRPC$InputDocument {
    public static int constructor = 1928391342;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
