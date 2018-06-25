package org.telegram.tgnet;

public class TLRPC$TL_contactLinkHasPhone extends TLRPC$ContactLink {
    public static int constructor = 646922073;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
