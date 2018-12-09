package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DecryptedMessageMedia;

public class TLRPC$TL_decryptedMessageMediaEmpty extends DecryptedMessageMedia {
    public static int constructor = 144661578;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
