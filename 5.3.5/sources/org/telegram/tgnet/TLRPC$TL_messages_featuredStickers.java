package org.telegram.tgnet;

public class TLRPC$TL_messages_featuredStickers extends TLRPC$messages_FeaturedStickers {
    public static int constructor = -123893531;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.hash = stream.readInt32(exception);
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
            if (stream.readInt32(exception) == 481674261) {
                count = stream.readInt32(exception);
                for (a = 0; a < count; a++) {
                    this.unread.add(Long.valueOf(stream.readInt64(exception)));
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
        stream.writeInt32(this.hash);
        stream.writeInt32(481674261);
        int count = this.sets.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((TLRPC$StickerSetCovered) this.sets.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(481674261);
        count = this.unread.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            stream.writeInt64(((Long) this.unread.get(a)).longValue());
        }
    }
}
