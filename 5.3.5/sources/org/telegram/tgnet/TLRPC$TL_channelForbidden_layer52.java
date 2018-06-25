package org.telegram.tgnet;

public class TLRPC$TL_channelForbidden_layer52 extends TLRPC$TL_channelForbidden {
    public static int constructor = 763724588;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
        this.title = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeInt64(this.access_hash);
        stream.writeString(this.title);
    }
}
