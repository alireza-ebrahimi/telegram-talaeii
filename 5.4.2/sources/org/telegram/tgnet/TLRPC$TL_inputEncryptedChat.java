package org.telegram.tgnet;

public class TLRPC$TL_inputEncryptedChat extends TLObject {
    public static int constructor = -247351839;
    public long access_hash;
    public int chat_id;

    public static TLRPC$TL_inputEncryptedChat TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_inputEncryptedChat tLRPC$TL_inputEncryptedChat = new TLRPC$TL_inputEncryptedChat();
            tLRPC$TL_inputEncryptedChat.readParams(abstractSerializedData, z);
            return tLRPC$TL_inputEncryptedChat;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_inputEncryptedChat", new Object[]{Integer.valueOf(i)}));
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
