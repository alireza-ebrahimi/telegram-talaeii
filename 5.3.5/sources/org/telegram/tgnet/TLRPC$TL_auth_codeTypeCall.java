package org.telegram.tgnet;

public class TLRPC$TL_auth_codeTypeCall extends TLRPC$auth_CodeType {
    public static int constructor = 1948046307;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
