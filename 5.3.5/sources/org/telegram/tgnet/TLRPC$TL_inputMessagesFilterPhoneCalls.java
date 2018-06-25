package org.telegram.tgnet;

public class TLRPC$TL_inputMessagesFilterPhoneCalls extends TLRPC$MessagesFilter {
    public static int constructor = -2134272152;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.missed = (this.flags & 1) != 0;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.missed ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
    }
}
