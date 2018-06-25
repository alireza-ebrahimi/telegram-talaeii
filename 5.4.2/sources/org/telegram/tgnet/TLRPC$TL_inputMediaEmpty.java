package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputMedia;

public class TLRPC$TL_inputMediaEmpty extends InputMedia {
    public static int constructor = -1771768449;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
