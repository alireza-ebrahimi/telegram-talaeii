package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;

public class TLRPC$TL_contactLinkUnknown extends ContactLink {
    public static int constructor = 1599050311;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
