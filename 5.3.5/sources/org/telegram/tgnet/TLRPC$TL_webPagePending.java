package org.telegram.tgnet;

public class TLRPC$TL_webPagePending extends TLRPC$WebPage {
    public static int constructor = -981018084;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt64(exception);
        this.date = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.id);
        stream.writeInt32(this.date);
    }
}
