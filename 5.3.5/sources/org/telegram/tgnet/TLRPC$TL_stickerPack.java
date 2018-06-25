package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_stickerPack extends TLObject {
    public static int constructor = 313694676;
    public ArrayList<Long> documents = new ArrayList();
    public String emoticon;

    public static TLRPC$TL_stickerPack TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_stickerPack result = new TLRPC$TL_stickerPack();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_stickerPack", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.emoticon = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            for (int a = 0; a < count; a++) {
                this.documents.add(Long.valueOf(stream.readInt64(exception)));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.emoticon);
        stream.writeInt32(481674261);
        int count = this.documents.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.documents.get(a)).longValue());
        }
    }
}
