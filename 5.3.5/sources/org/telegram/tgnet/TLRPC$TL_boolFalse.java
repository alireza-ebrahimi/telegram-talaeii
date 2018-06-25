package org.telegram.tgnet;

public class TLRPC$TL_boolFalse extends TLRPC$Bool {
    public static int constructor = -1132882121;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
