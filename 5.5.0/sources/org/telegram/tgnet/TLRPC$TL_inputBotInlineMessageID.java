package org.telegram.tgnet;

public class TLRPC$TL_inputBotInlineMessageID extends TLObject {
    public static int constructor = -1995686519;
    public long access_hash;
    public int dc_id;
    public long id;

    public static TLRPC$TL_inputBotInlineMessageID TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputBotInlineMessageID tLRPC$TL_inputBotInlineMessageID = new TLRPC$TL_inputBotInlineMessageID();
            tLRPC$TL_inputBotInlineMessageID.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputBotInlineMessageID;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputBotInlineMessageID", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.dc_id = abstractSerializedData.readInt32(z);
        this.id = abstractSerializedData.readInt64(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.dc_id);
        abstractSerializedData.writeInt64(this.id);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
