package org.telegram.tgnet;

public class TLRPC$TL_topPeerCategoryPhoneCalls extends TLRPC$TopPeerCategory {
    public static int constructor = 511092620;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
