package org.telegram.tgnet;

public class TLRPC$TL_photo_layer55 extends TLRPC$TL_photo {
    public static int constructor = -840088834;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$PhotoSize object = TLRPC$PhotoSize.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.sizes.add(object);
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
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeInt32(this.date);
        stream.writeInt32(481674261);
        int count = this.sizes.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$PhotoSize) this.sizes.get(a)).serializeToStream(stream);
        }
    }
}
