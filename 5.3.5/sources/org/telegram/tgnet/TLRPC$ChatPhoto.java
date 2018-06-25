package org.telegram.tgnet;

public abstract class TLRPC$ChatPhoto extends TLObject {
    public TLRPC$FileLocation photo_big;
    public TLRPC$FileLocation photo_small;

    public static TLRPC$ChatPhoto TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$ChatPhoto result = null;
        switch (constructor) {
            case 935395612:
                result = new TLRPC$TL_chatPhotoEmpty();
                break;
            case 1632839530:
                result = new TLRPC$TL_chatPhoto();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in ChatPhoto", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
