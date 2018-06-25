package org.telegram.tgnet;

public class TLRPC$TL_webPageNotModified extends TLRPC$WebPage {
    public static int constructor = -2054908813;

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
