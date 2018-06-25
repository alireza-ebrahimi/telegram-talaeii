package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_stickerSet extends TLObject {
    public static int constructor = -1240849242;
    public ArrayList<TLRPC$Document> documents = new ArrayList();
    public ArrayList<TLRPC$TL_stickerPack> packs = new ArrayList();
    public TLRPC$StickerSet set;

    public static TLRPC$TL_messages_stickerSet TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_stickerSet result = new TLRPC$TL_messages_stickerSet();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_stickerSet", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.set = TLRPC$StickerSet.TLdeserialize(stream, stream.readInt32(exception), exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$TL_stickerPack object = TLRPC$TL_stickerPack.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.packs.add(object);
                    a++;
                } else {
                    return;
                }
            }
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                a = 0;
                while (a < count) {
                    TLRPC$Document object2 = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object2 != null) {
                        this.documents.add(object2);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            }
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int a;
        stream.writeInt32(constructor);
        this.set.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.packs.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$TL_stickerPack) this.packs.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.documents.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$Document) this.documents.get(a)).serializeToStream(stream);
        }
    }
}
