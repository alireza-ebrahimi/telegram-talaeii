package org.telegram.tgnet;

public class TLRPC$TL_channelForbidden_layer67 extends TLRPC$TL_channelForbidden {
    public static int constructor = -2059962289;

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
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.broadcast ? this.flags | 32 : this.flags & -33;
        this.flags = this.megagroup ? this.flags | 256 : this.flags & -257;
        stream.writeInt32(this.flags);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
    }
}
