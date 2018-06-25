package org.telegram.tgnet;

public class TLRPC$TL_inlineBotSwitchPM extends TLObject {
    public static int constructor = 1008755359;
    public String start_param;
    public String text;

    public static TLRPC$TL_inlineBotSwitchPM TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inlineBotSwitchPM result = new TLRPC$TL_inlineBotSwitchPM();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inlineBotSwitchPM", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.text = stream.readString(exception);
        this.start_param = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeString(this.text);
        stream.writeString(this.start_param);
    }
}
