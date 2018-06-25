package org.telegram.tgnet;

public class TLRPC$TL_updateNewGeoChatMessage extends TLRPC$Update {
    public static int constructor = 1516823543;
    public TLRPC$GeoChatMessage message;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.message = TLRPC$GeoChatMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.message.serializeToStream(stream);
    }
}
