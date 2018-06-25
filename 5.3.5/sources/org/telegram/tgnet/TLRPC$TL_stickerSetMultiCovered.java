package org.telegram.tgnet;

public class TLRPC$TL_stickerSetMultiCovered extends TLRPC$StickerSetCovered {
    public static int constructor = 872932635;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.set = TLRPC$StickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Document object = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.covers.add(object);
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
        this.set.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.covers.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$Document) this.covers.get(a)).serializeToStream(stream);
        }
    }
}
