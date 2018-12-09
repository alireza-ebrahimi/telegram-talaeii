package org.telegram.tgnet;

public class TLRPC$TL_messages_getPinnedDialogs extends TLObject {
    public static int constructor = -497756594;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$TL_messages_peerDialogs.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
