package org.telegram.tgnet;

public class TLRPC$TL_inputAppEvent extends TLObject {
    public static int constructor = 1996904104;
    public String data;
    public long peer;
    public double time;
    public String type;

    public static TLRPC$TL_inputAppEvent TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputAppEvent tLRPC$TL_inputAppEvent = new TLRPC$TL_inputAppEvent();
            tLRPC$TL_inputAppEvent.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputAppEvent;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputAppEvent", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.time = abstractSerializedData.readDouble(z);
        this.type = abstractSerializedData.readString(z);
        this.peer = abstractSerializedData.readInt64(z);
        this.data = abstractSerializedData.readString(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeDouble(this.time);
        abstractSerializedData.writeString(this.type);
        abstractSerializedData.writeInt64(this.peer);
        abstractSerializedData.writeString(this.data);
    }
}
