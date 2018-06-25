package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;

public class TLRPC$TL_contactLinkNone extends ContactLink {
    public static int constructor = -17968211;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
