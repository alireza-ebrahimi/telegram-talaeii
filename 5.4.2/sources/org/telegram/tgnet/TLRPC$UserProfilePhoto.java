package org.telegram.tgnet;

import org.telegram.tgnet.TLRPC.FileLocation;

public abstract class TLRPC$UserProfilePhoto extends TLObject {
    public FileLocation photo_big;
    public long photo_id;
    public FileLocation photo_small;

    public static TLRPC$UserProfilePhoto TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$UserProfilePhoto tLRPC$UserProfilePhoto = null;
        switch (i) {
            case -1727196013:
                tLRPC$UserProfilePhoto = new TLRPC$TL_userProfilePhoto_old();
                break;
            case -715532088:
                tLRPC$UserProfilePhoto = new TLRPC$TL_userProfilePhoto();
                break;
            case 1326562017:
                tLRPC$UserProfilePhoto = new TLRPC$TL_userProfilePhotoEmpty();
                break;
        }
        if (tLRPC$UserProfilePhoto == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in UserProfilePhoto", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$UserProfilePhoto != null) {
            tLRPC$UserProfilePhoto.readParams(abstractSerializedData, z);
        }
        return tLRPC$UserProfilePhoto;
    }
}
