package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.EncryptedChat;
import org.telegram.tgnet.TLRPC.InputUser;

public class TLRPC$TL_messages_requestEncryption extends TLObject {
    public static int constructor = -162681021;
    public byte[] g_a;
    public int random_id;
    public InputUser user_id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return EncryptedChat.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.user_id.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(this.random_id);
        abstractSerializedData.writeByteArray(this.g_a);
    }
}
