package org.telegram.tgnet;

public class TLRPC$TL_updateShort extends TLRPC$Updates {
    public static int constructor = 2027216577;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.update = TLRPC$Update.TLdeserialize(stream, stream.readInt32(exception), exception);
        this.date = stream.readInt32(exception);
    }
}
