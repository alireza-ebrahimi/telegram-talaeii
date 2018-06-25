package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.StickerSet;

public class TLRPC$TL_stickerSet extends StickerSet {
    public static int constructor = -852477119;

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        boolean z2 = true;
        this.flags = abstractSerializedData.readInt32(z);
        this.installed = (this.flags & 1) != 0;
        this.archived = (this.flags & 2) != 0;
        this.official = (this.flags & 4) != 0;
        if ((this.flags & 8) == 0) {
            z2 = false;
        }
        this.masks = z2;
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
        this.title = abstractSerializedData.readString(z);
        this.short_name = abstractSerializedData.readString(z);
        this.count = abstractSerializedData.readInt32(z);
        this.hash = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.flags = this.installed ? this.flags | 1 : this.flags & -2;
        this.flags = this.archived ? this.flags | 2 : this.flags & -3;
        this.flags = this.official ? this.flags | 4 : this.flags & -5;
        this.flags = this.masks ? this.flags | 8 : this.flags & -9;
        abstractSerializedData.writeInt32(this.flags);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
        abstractSerializedData.writeString(this.title);
        abstractSerializedData.writeString(this.short_name);
        abstractSerializedData.writeInt32(this.count);
        abstractSerializedData.writeInt32(this.hash);
    }
}
