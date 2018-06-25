package org.telegram.tgnet;

public class TLRPC$TL_photo extends TLRPC$Photo {
    public static int constructor = -1836524247;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        this.flags = stream.readInt32(exception);
        if ((this.flags & 1) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.has_stickers = z;
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
        int i;
        stream.writeInt32(constructor);
        if (this.has_stickers) {
            i = this.flags | 1;
        } else {
            i = this.flags & -2;
        }
        this.flags = i;
        stream.writeInt32(this.flags);
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
