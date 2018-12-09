package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.GeoPoint;
import org.telegram.tgnet.TLRPC.PhotoSize;

public class TLRPC$TL_photo_old extends TLRPC$TL_photo {
    public static int constructor = 582313809;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.user_id = abstractSerializedData.readInt32(z);
        this.date = abstractSerializedData.readInt32(z);
        this.caption = abstractSerializedData.readString(z);
        this.geo = GeoPoint.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                PhotoSize TLdeserialize = PhotoSize.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.sizes.add(TLdeserialize);
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
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeInt32(this.user_id);
        abstractSerializedData.writeInt32(this.date);
        abstractSerializedData.writeString(this.caption);
        this.geo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.sizes.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((PhotoSize) this.sizes.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
