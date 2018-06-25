package org.telegram.tgnet;

public class TLRPC$TL_inputPaymentCredentials extends TLRPC$InputPaymentCredentials {
    public static int constructor = 873977640;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.flags = stream.readInt32(exception);
        this.save = (this.flags & 1) != 0;
        this.data = TLRPC$TL_dataJSON.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.flags = this.save ? this.flags | 1 : this.flags & -2;
        stream.writeInt32(this.flags);
        this.data.serializeToStream(stream);
    }
}
