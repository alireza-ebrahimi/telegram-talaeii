package org.telegram.tgnet;

public class TLRPC$TL_receivedNotifyMessage extends TLObject {
    public static int constructor = -1551583367;
    public int flags;
    public int id;

    public static TLRPC$TL_receivedNotifyMessage TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_receivedNotifyMessage tLRPC$TL_receivedNotifyMessage = new TLRPC$TL_receivedNotifyMessage();
            tLRPC$TL_receivedNotifyMessage.readParams(abstractSerializedData, z);
            return tLRPC$TL_receivedNotifyMessage;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_receivedNotifyMessage", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.id = abstractSerializedData.readInt32(z);
        this.flags = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.id);
        abstractSerializedData.writeInt32(this.flags);
    }
}
