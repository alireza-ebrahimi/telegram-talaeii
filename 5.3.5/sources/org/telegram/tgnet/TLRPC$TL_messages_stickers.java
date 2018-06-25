package org.telegram.tgnet;

public class TLRPC$TL_messages_stickers extends TLRPC$messages_Stickers {
    public static int constructor = -1970352846;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.hash = stream.readString(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Document object = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.stickers.add(object);
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
        stream.writeString(this.hash);
        stream.writeInt32(481674261);
        int count = this.stickers.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$Document) this.stickers.get(a)).serializeToStream(stream);
        }
    }
}
