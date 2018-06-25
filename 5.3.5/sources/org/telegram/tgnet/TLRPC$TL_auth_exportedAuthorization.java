package org.telegram.tgnet;

public class TLRPC$TL_auth_exportedAuthorization extends TLObject {
    public static int constructor = -543777747;
    public byte[] bytes;
    public int id;

    public static TLRPC$TL_auth_exportedAuthorization TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_auth_exportedAuthorization result = new TLRPC$TL_auth_exportedAuthorization();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_auth_exportedAuthorization", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.id = stream.readInt32(exception);
        this.bytes = stream.readByteArray(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.id);
        stream.writeByteArray(this.bytes);
    }
}
