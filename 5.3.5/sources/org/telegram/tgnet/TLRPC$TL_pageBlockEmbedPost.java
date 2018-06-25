package org.telegram.tgnet;

public class TLRPC$TL_pageBlockEmbedPost extends TLRPC$PageBlock {
    public static int constructor = 690781161;
    public String author;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.url = stream.readString(exception);
        this.webpage_id = stream.readInt64(exception);
        this.author_photo_id = stream.readInt64(exception);
        this.author = stream.readString(exception);
        this.date = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PageBlock object = TLRPC$PageBlock.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.blocks.add(object);
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
        stream.writeString(this.url);
        stream.writeInt64(this.webpage_id);
        stream.writeInt64(this.author_photo_id);
        stream.writeString(this.author);
        stream.writeInt32(this.date);
        stream.writeInt32(481674261);
        int count = this.blocks.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$PageBlock) this.blocks.get(a)).serializeToStream(stream);
        }
        this.caption.serializeToStream(stream);
    }
}
