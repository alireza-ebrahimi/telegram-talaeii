package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.PageBlock;
import org.telegram.tgnet.TLRPC.RichText;

public class TLRPC$TL_pageBlockSlideshow extends PageBlock {
    public static int constructor = 319588707;
    public ArrayList<PageBlock> items = new ArrayList();

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                PageBlock TLdeserialize = PageBlock.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.items.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
            this.caption = RichText.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.items.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((PageBlock) this.items.get(i)).serializeToStream(abstractSerializedData);
        }
        this.caption.serializeToStream(abstractSerializedData);
    }
}
