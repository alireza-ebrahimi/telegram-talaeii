package org.telegram.tgnet;

public class TLRPC$TL_auth_codeTypeFlashCall extends TLRPC$auth_CodeType {
    public static int constructor = 577556219;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
