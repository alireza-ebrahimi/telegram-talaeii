package org.telegram.tgnet;

public class TLRPC$TL_inputGeoChat extends TLObject {
    public static int constructor = 1960072954;
    public long access_hash;
    public int chat_id;

    public static TLRPC$TL_inputGeoChat TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputGeoChat result = new TLRPC$TL_inputGeoChat();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputGeoChat", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.chat_id = stream.readInt32(exception);
        this.access_hash = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.chat_id);
        stream.writeInt64(this.access_hash);
    }
}
