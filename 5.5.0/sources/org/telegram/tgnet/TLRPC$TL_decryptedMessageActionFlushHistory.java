package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageAction;

public class TLRPC$TL_decryptedMessageActionFlushHistory extends DecryptedMessageAction {
    public static int constructor = 1729750108;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
