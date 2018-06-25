package org.telegram.tgnet;

public class TLRPC$TL_auth_exportAuthorization extends TLObject {
    public static int constructor = -440401971;
    public int dc_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        return TLRPC$TL_auth_exportedAuthorization.TLdeserialize(stream, constructor, exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.dc_id);
    }
}
