package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_pageBlockList extends TLRPC$PageBlock {
    public static int constructor = 978896884;
    public ArrayList<TLRPC$RichText> items = new ArrayList();

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.ordered = stream.readBool(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$RichText object = TLRPC$RichText.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.items.add(object);
                    a++;
                } else {
                    return;
                }
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeBool(this.ordered);
        stream.writeInt32(481674261);
        int count = this.items.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$RichText) this.items.get(a)).serializeToStream(stream);
        }
    }
}
