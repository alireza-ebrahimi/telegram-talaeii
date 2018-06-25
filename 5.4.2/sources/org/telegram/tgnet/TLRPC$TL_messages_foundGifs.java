package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.FoundGif;

public class TLRPC$TL_messages_foundGifs extends TLObject {
    public static int constructor = 1158290442;
    public int next_offset;
    public ArrayList<FoundGif> results = new ArrayList();

    public static TLRPC$TL_messages_foundGifs TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messages_foundGifs tLRPC$TL_messages_foundGifs = new TLRPC$TL_messages_foundGifs();
            tLRPC$TL_messages_foundGifs.readParams(abstractSerializedData, z);
            return tLRPC$TL_messages_foundGifs;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_foundGifs", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.next_offset = abstractSerializedData.readInt32(z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                FoundGif TLdeserialize = FoundGif.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.results.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.next_offset);
        abstractSerializedData.writeInt32(481674261);
        int size = this.results.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((FoundGif) this.results.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
