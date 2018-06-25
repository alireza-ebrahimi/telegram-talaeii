package org.telegram.tgnet;

public class TLRPC$TL_inputAppEvent extends TLObject {
    public static int constructor = 1996904104;
    public String data;
    public long peer;
    public double time;
    public String type;

    public static TLRPC$TL_inputAppEvent TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        if (constructor == constructor) {
            TLRPC$TL_inputAppEvent result = new TLRPC$TL_inputAppEvent();
            result.readParams(stream, exception);
            return result;
        } else if (!exception) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputAppEvent", new Object[]{Integer.valueOf(constructor)}));
        }
    }

    public void readParams(AbstractSerializedData stream, boolean exception) {
        this.time = stream.readDouble(exception);
        this.type = stream.readString(exception);
        this.peer = stream.readInt64(exception);
        this.data = stream.readString(exception);
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeDouble(this.time);
        stream.writeString(this.type);
        stream.writeInt64(this.peer);
        stream.writeString(this.data);
    }
}
