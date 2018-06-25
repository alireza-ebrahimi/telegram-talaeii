package org.telegram.tgnet;

public class TLRPC$TL_stickerSet extends TLRPC$StickerSet {
    public static int constructor = -852477119;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z;
        boolean z2 = true;
        this.flags = stream.readInt32(exception);
        this.installed = (this.flags & 1) != 0;
        if ((this.flags & 2) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.archived = z;
        if ((this.flags & 4) != 0) {
            z = true;
        } else {
            z = false;
        }
        this.official = z;
        if ((this.flags & 8) == 0) {
            z2 = false;
        }
        this.masks = z2;
        this.id = stream.readInt64(exception);
        this.access_hash = stream.readInt64(exception);
        this.title = stream.readString(exception);
        this.short_name = stream.readString(exception);
        this.count = stream.readInt32(exception);
        this.hash = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.installed ? this.flags | 1 : this.flags & -2;
        this.flags = this.archived ? this.flags | 2 : this.flags & -3;
        this.flags = this.official ? this.flags | 4 : this.flags & -5;
        this.flags = this.masks ? this.flags | 8 : this.flags & -9;
        stream.writeInt32(this.flags);
        stream.writeInt64(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
        stream.writeString(this.short_name);
        stream.writeInt32(this.count);
        stream.writeInt32(this.hash);
    }
}
