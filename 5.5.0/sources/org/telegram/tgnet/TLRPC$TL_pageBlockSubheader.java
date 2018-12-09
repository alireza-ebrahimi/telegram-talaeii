package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockSubheader extends PageBlock {
    public static int constructor = -248793375;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.text = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.text.serializeToStream(abstractSerializedData);
    }
}
