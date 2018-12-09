package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockAuthorDate extends PageBlock {
    public static int constructor = -1162877472;
    public RichText author;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.author = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        this.published_date = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.author.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.published_date);
    }
}
