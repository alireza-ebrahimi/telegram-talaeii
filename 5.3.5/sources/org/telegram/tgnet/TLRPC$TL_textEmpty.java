package org.telegram.tgnet;

public class TLRPC$TL_textEmpty extends TLRPC$RichText {
    public static int constructor = -599948721;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
