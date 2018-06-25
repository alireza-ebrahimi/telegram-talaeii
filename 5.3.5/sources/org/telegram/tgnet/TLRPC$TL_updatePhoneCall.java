package org.telegram.tgnet;

public class TLRPC$TL_updatePhoneCall extends TLRPC$Update {
    public static int constructor = -1425052898;

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.phone_call = TLRPC$PhoneCall.TLdeserialize(stream, stream.readInt32(exception), exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        this.phone_call.serializeToStream(stream);
    }
}
