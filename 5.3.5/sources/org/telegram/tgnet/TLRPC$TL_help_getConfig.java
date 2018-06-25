package org.telegram.tgnet;

public class TLRPC$TL_help_getConfig extends TLObject {
    public static int constructor = -990308245;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_config.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
