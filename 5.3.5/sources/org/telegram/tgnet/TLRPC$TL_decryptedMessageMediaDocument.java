package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.DocumentAttribute;

public class TLRPC$TL_decryptedMessageMediaDocument extends TLRPC$DecryptedMessageMedia {
    public static int constructor = 2063502050;
    public byte[] thumb;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.thumb = stream.readByteArray(exception);
        this.thumb_w = stream.readInt32(exception);
        this.thumb_h = stream.readInt32(exception);
        this.mime_type = stream.readString(exception);
        this.size = stream.readInt32(exception);
        this.key = stream.readByteArray(exception);
        this.iv = stream.readByteArray(exception);
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
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeByteArray(this.thumb);
        stream.writeInt32(this.thumb_w);
        stream.writeInt32(this.thumb_h);
        stream.writeString(this.mime_type);
        stream.writeInt32(this.size);
        stream.writeByteArray(this.key);
        stream.writeByteArray(this.iv);
        stream.writeInt32(481674261);
        int count = this.attributes.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((DocumentAttribute) this.attributes.get(a)).serializeToStream(stream);
        }
        stream.writeString(this.caption);
    }
}
