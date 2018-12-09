package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;

public class TLRPC$TL_contactLinkHasPhone extends ContactLink {
    public static int constructor = 646922073;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
