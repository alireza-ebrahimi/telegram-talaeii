package org.telegram.tgnet;

public class TLRPC$TL_disabledFeature extends TLObject {
    public static int constructor = -1369215196;
    public String description;
    public String feature;

    public static TLRPC$TL_disabledFeature TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_disabledFeature result = new TLRPC$TL_disabledFeature();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_disabledFeature", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.feature = stream.readString(exception);
        this.description = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.feature);
        stream.writeString(this.description);
    }
}
