package org.telegram.tgnet;

public class TLRPC$TL_updateGroupCall extends TLRPC$Update {
    public static int constructor = -2046916883;
    public TLRPC$GroupCall call;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.call = TLRPC$GroupCall.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.call.serializeToStream(stream);
    }
}
