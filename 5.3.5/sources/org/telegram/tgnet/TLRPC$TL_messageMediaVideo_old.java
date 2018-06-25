package org.telegram.tgnet;

public class TLRPC$TL_messageMediaVideo_old extends TLRPC$TL_messageMediaVideo_layer45 {
    public static int constructor = -1563278704;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.video_unused = TLRPC$Video.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.video_unused.serializeToStream(stream);
    }
}
