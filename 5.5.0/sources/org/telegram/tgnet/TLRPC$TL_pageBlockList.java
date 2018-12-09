package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockList extends PageBlock {
    public static int constructor = 978896884;
    public ArrayList<RichText> items = new ArrayList();

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.ordered = abstractSerializedData.readBool(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                RichText TLdeserialize = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.items.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeBool(this.ordered);
        abstractSerializedData.writeInt32(481674261);
        int size = this.items.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((RichText) this.items.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
