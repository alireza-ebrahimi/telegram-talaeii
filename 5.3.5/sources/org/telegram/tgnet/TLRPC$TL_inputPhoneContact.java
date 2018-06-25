package org.telegram.tgnet;

public class TLRPC$TL_inputPhoneContact extends TLObject {
    public static int constructor = -208488460;
    public long client_id;
    public String first_name;
    public String last_name;
    public String phone;

    public static TLRPC$TL_inputPhoneContact TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputPhoneContact result = new TLRPC$TL_inputPhoneContact();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputPhoneContact", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.client_id = stream.readInt64(exception);
        this.phone = stream.readString(exception);
        this.first_name = stream.readString(exception);
        this.last_name = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt64(this.client_id);
        stream.writeString(this.phone);
        stream.writeString(this.first_name);
        stream.writeString(this.last_name);
    }
}
