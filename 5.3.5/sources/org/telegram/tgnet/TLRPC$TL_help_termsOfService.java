package org.telegram.tgnet;

public class TLRPC$TL_help_termsOfService extends TLObject {
    public static int constructor = -236044656;
    public String text;

    public static TLRPC$TL_help_termsOfService TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_help_termsOfService result = new TLRPC$TL_help_termsOfService();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_help_termsOfService", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
    }
}
