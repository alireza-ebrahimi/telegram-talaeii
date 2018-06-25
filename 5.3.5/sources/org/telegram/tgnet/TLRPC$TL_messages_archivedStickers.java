package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_archivedStickers extends TLObject {
    public static int constructor = 1338747336;
    public int count;
    public ArrayList<TLRPC$StickerSetCovered> sets = new ArrayList();

    public static TLRPC$TL_messages_archivedStickers TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_archivedStickers result = new TLRPC$TL_messages_archivedStickers();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_archivedStickers", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.count = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$StickerSetCovered object = TLRPC$StickerSetCovered.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.sets.add(object);
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
        stream.writeInt32(this.count);
        stream.writeInt32(481674261);
        int count = this.sets.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$StickerSetCovered) this.sets.get(a)).serializeToStream(stream);
        }
    }
}
