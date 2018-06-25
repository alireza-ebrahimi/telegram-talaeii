package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaEmpty extends MessageMedia {
    public static int constructor = 1038967584;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
