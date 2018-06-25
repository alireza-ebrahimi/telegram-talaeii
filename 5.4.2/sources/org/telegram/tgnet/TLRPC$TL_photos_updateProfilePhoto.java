package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.InputPhoto;

public class TLRPC$TL_photos_updateProfilePhoto extends TLObject {
    public static int constructor = -256159406;
    public InputPhoto id;

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        return TLRPC$UserProfilePhoto.TLdeserialize(abstractSerializedData, i, z);
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.id.serializeToStream(abstractSerializedData);
    }
}
