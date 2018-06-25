package org.telegram.tgnet;

public class TLRPC$TL_help_getAppUpdate extends TLObject {
    public static int constructor = -1372724842;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$help_AppUpdate.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
