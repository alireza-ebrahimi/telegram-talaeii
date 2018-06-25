package org.telegram.tgnet;

public class TLRPC$TL_contactLinkUnknown extends TLRPC$ContactLink {
    public static int constructor = 1599050311;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
