package org.telegram.tgnet;

public class TLRPC$TL_auth_codeTypeSms extends TLRPC$auth_CodeType {
    public static int constructor = 1923290508;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
