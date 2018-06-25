package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_pageBlockSlideshow extends TLRPC$PageBlock {
    public static int constructor = 319588707;
    public ArrayList<TLRPC$PageBlock> items = new ArrayList();

    public void readParams(AbstractSerializedData stream, boolean exception) {
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PageBlock object = TLRPC$PageBlock.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.items.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.caption = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.items.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$PageBlock) this.items.get(a)).serializeToStream(stream);
        }
        this.caption.serializeToStream(stream);
    }
}
