package org.telegram.tgnet;

public class TLRPC$TL_importedContact extends TLObject {
    public static int constructor = -805141448;
    public long client_id;
    public int user_id;

    public static TLRPC$TL_importedContact TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_importedContact result = new TLRPC$TL_importedContact();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_importedContact", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.user_id = stream.readInt32(exception);
        this.client_id = stream.readInt64(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.user_id);
        stream.writeInt64(this.client_id);
    }
}
