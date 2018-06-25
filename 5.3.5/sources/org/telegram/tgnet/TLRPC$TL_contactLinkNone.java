package org.telegram.tgnet;

public class TLRPC$TL_contactLinkNone extends TLRPC$ContactLink {
    public static int constructor = -17968211;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
