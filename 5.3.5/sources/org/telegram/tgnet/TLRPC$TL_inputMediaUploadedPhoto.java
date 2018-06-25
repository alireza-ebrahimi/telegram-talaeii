package org.telegram.tgnet;

public class TLRPC$TL_inputMediaUploadedPhoto extends TLRPC$InputMedia {
    public static int constructor = 792191537;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.file = TLRPC$InputFile.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.caption = stream.readString(exception);
        if ((this.flags & 1) != 0) {
            if (stream.readInt32(exception) == 481674261) {
                int count = stream.readInt32(exception);
                int a = 0;
                while (a < count) {
                    TLRPC$InputDocument object = TLRPC$InputDocument.TLdeserialize(stream, stream.readInt32(exception), exception);
                    if (object != null) {
                        this.stickers.add(object);
                        a++;
                    } else {
                        return;
                    }
                }
            } else if (exception) {
                throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
            } else {
                return;
            }
        }
        if ((this.flags & 2) != 0) {
            this.ttl_seconds = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.file.serializeToStream(stream);
        stream.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(481674261);
            int count = this.stickers.size();
            stream.writeInt32(count);
            for (int a = 0; a < count; a++) {
                ((TLRPC$InputDocument) this.stickers.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.ttl_seconds);
        }
    }
}
