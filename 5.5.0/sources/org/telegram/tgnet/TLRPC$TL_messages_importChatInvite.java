package org.telegram.tgnet;

public class TLRPC$TL_messages_importChatInvite extends TLObject {
    public static int constructor = 1817183516;
    public String hash;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$Updates.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeString(this.hash);
    }
}
