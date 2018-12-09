package org.telegram.tgnet;

public class TLRPC$TL_inputGeoChat extends TLObject {
    public static int constructor = 1960072954;
    public long access_hash;
    public int chat_id;

    public static TLRPC$TL_inputGeoChat TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputGeoChat tLRPC$TL_inputGeoChat = new TLRPC$TL_inputGeoChat();
            tLRPC$TL_inputGeoChat.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputGeoChat;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputGeoChat", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        this.chat_id = abstractSerializedData.readInt32(z);
        this.access_hash = abstractSerializedData.readInt64(z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(this.chat_id);
        abstractSerializedData.writeInt64(this.access_hash);
    }
}
