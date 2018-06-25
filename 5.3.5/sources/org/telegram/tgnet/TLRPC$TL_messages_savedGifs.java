package org.telegram.tgnet;

public class TLRPC$TL_messages_savedGifs extends TLRPC$messages_SavedGifs {
    public static int constructor = 772213157;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.hash = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$Document object = TLRPC$Document.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.gifs.add(object);
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
        stream.writeInt32(this.hash);
        stream.writeInt32(481674261);
        int count = this.gifs.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$Document) this.gifs.get(a)).serializeToStream(stream);
        }
    }
}
