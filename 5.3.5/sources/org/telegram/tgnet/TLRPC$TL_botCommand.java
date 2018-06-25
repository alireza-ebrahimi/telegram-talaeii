package org.telegram.tgnet;

public class TLRPC$TL_botCommand extends TLObject {
    public static int constructor = -1032140601;
    public String command;
    public String description;

    public static TLRPC$TL_botCommand TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_botCommand result = new TLRPC$TL_botCommand();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_botCommand", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.command = stream.readString(exception);
        this.description = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.command);
        stream.writeString(this.description);
    }
}
