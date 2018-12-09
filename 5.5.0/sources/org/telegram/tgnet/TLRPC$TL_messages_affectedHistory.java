package org.telegram.tgnet;

public class TLRPC$TL_messages_affectedHistory extends TLObject {
    public static int constructor = -1269012015;
    public int offset;
    public int pts;
    public int pts_count;

    public static TLRPC$TL_messages_affectedHistory TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_messages_affectedHistory tLRPC$TL_messages_affectedHistory = new TLRPC$TL_messages_affectedHistory();
            tLRPC$TL_messages_affectedHistory.readParams(abstractSerializedData, z);
            return tLRPC$TL_messages_affectedHistory;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_messages_affectedHistory", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.pts = abstractSerializedData.readInt32(z);
        this.pts_count = abstractSerializedData.readInt32(z);
        this.offset = abstractSerializedData.readInt32(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.pts);
        abstractSerializedData.writeInt32(this.pts_count);
        abstractSerializedData.writeInt32(this.offset);
    }
}
