package org.telegram.tgnet;

public class TLRPC$TL_messageActionGroupCall extends TLRPC$MessageAction {
    public static int constructor = 2047704898;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.call = TLRPC$TL_inputGroupCall.TLdeserialize(stream, stream.readInt32(exception), exception);
        if ((this.flags & 1) != 0) {
            this.duration = stream.readInt32(exception);
        }
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.flags);
        this.call.serializeToStream(stream);
        if ((this.flags & 1) != 0) {
            stream.writeInt32(this.duration);
        }
    }
}
