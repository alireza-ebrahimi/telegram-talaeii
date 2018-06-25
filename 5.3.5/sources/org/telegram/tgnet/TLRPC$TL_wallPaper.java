package org.telegram.tgnet;

public class TLRPC$TL_wallPaper extends TLRPC$WallPaper {
    public static int constructor = -860866985;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.title = stream.readString(exception);
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
            this.color = stream.readInt32(exception);
        } else if (exception) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(magic)}));
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeString(this.title);
        stream.writeInt32(481674261);
        int count = this.sizes.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$PhotoSize) this.sizes.get(a)).serializeToStream(stream);
        }
        stream.writeInt32(this.color);
    }
}
