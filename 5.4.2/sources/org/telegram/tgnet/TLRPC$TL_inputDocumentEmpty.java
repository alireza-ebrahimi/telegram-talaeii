package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputDocument;

public class TLRPC$TL_inputDocumentEmpty extends InputDocument {
    public static int constructor = 1928391342;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
