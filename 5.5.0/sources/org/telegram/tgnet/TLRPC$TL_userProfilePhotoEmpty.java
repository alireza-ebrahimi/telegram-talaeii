package org.telegram.tgnet;

public class TLRPC$TL_userProfilePhotoEmpty extends TLRPC$UserProfilePhoto {
    public static int constructor = 1326562017;

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
    }
}
