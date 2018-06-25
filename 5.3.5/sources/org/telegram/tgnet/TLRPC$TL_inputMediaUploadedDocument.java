package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_inputMediaUploadedDocument extends TLRPC$InputMedia {
    public static int constructor = -476700163;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 8) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.nosound_video = z;
        this.file = TLRPC$InputFile.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 4) != 0) {
            this.thumb = TLRPC$InputFile.TLdeserialize(stream, stream.readInt32(exception), exception);
        }
        this.mime_type = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                DocumentAttribute object = DocumentAttribute.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.attributes.add(object);
                    a++;
                } else {
                    return;
                }
            }
            this.caption = stream.readString(exception);
            if ((this.flags & 1) != 0) {
                if (stream.readInt32(exception) == 481674261) {
                    count = stream.readInt32(exception);
                    a = 0;
                    while (a < count) {
                        TLRPC$InputDocument object2 = TLRPC$InputDocument.TLdeserialize(stream, stream.readInt32(exception), exception);
                        if (object2 != null) {
                            this.stickers.add(object2);
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
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        int i;
        int a;
        stream.writeInt32(constructor);
        if (this.nosound_video) {
            i = this.flags | 8;
        } else {
            i = this.flags & -9;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
        this.file.serializeToStream(stream);
        if ((this.flags & 4) != 0) {
            this.thumb.serializeToStream(stream);
        }
        stream.writeString(this.mime_type);
        stream.writeInt32(481674261);
        int count = this.attributes.size();
        stream.writeInt32(count);
        for (a = 0; a < count; a++) {
            ((DocumentAttribute) this.attributes.get(a)).serializeToStream(stream);
        }
        stream.writeString(this.caption);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(481674261);
            count = this.stickers.size();
            stream.writeInt32(count);
            for (a = 0; a < count; a++) {
                ((TLRPC$InputDocument) this.stickers.get(a)).serializeToStream(stream);
            }
        }
        if ((this.flags & 2) != 0) {
            stream.writeInt32(this.ttl_seconds);
        }
    }
}
