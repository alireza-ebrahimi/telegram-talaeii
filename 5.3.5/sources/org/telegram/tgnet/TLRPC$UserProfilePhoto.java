package org.telegram.tgnet;

public abstract class TLRPC$UserProfilePhoto extends TLObject {
    public TLRPC$FileLocation photo_big;
    public long photo_id;
    public TLRPC$FileLocation photo_small;

    public static TLRPC$UserProfilePhoto TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$UserProfilePhoto result = null;
        switch (constructor) {
            case -1727196013:
                result = new TLRPC$TL_userProfilePhoto_old();
                break;
            case -715532088:
                result = new TLRPC$TL_userProfilePhoto();
                break;
            case 1326562017:
                result = new TLRPC$TL_userProfilePhotoEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in UserProfilePhoto", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
