package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.MessageMedia;

public class TLRPC$TL_messageMediaWebPage extends MessageMedia {
    public static int constructor = -1557277184;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.webpage = TLRPC$WebPage.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.webpage.serializeToStream(abstractSerializedData);
    }
}
