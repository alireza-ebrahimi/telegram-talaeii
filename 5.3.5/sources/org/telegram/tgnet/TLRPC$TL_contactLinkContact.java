package org.telegram.tgnet;

public class TLRPC$TL_contactLinkContact extends TLRPC$ContactLink {
    public static int constructor = -721239344;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
