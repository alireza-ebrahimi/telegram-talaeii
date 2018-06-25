package org.telegram.tgnet;

public class TLRPC$TL_channelForbidden extends TLRPC$Chat {
    public static int constructor = 681420594;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        boolean z = true;
        this.flags = stream.readInt32(exception);
        this.broadcast = (this.flags & 32) != 0;
        if ((this.flags & 256) == 0) {
            z = false;
        }
        this.megagroup = z;
        this.id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
        this.title = stream.readString(exception);
        if ((this.flags & 65536) != 0) {
            this.until_date = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
        this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
        if ((this.flags & 65536) != 0) {
            stream.writeInt32(this.until_date);
        }
    }
}
