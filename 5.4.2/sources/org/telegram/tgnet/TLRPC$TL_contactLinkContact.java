package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.ContactLink;

public class TLRPC$TL_contactLinkContact extends ContactLink {
    public static int constructor = -721239344;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
