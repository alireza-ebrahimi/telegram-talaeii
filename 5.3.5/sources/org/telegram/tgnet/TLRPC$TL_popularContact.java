package org.telegram.tgnet;

public class TLRPC$TL_popularContact extends TLObject {
    public static int constructor = 1558266229;
    public long client_id;
    public int importers;

    public static TLRPC$TL_popularContact TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_popularContact result = new TLRPC$TL_popularContact();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_popularContact", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.client_id = stream.readInt64(exception);
        this.importers = stream.readInt32(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.client_id);
        stream.writeInt32(this.importers);
    }
}
