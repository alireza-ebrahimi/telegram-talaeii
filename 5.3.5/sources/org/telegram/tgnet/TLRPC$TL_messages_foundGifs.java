package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_foundGifs extends TLObject {
    public static int constructor = 1158290442;
    public int next_offset;
    public ArrayList<TLRPC$FoundGif> results = new ArrayList();

    public static TLRPC$TL_messages_foundGifs TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_messages_foundGifs result = new TLRPC$TL_messages_foundGifs();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_foundGifs", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.next_offset = stream.readInt32(exception);
        if (stream.readInt32(exception) == 481674261) {
            int count = stream.readInt32(exception);
            int a = 0;
            while (a < count) {
                TLRPC$FoundGif object = TLRPC$FoundGif.TLdeserialize(stream, stream.readInt32(exception), exception);
                if (object != null) {
                    this.results.add(object);
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
        stream.writeInt32(this.next_offset);
        stream.writeInt32(481674261);
        int count = this.results.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$FoundGif) this.results.get(a)).serializeToStream(stream);
        }
    }
}
