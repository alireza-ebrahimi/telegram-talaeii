package org.telegram.tgnet;

public abstract class TLRPC$InputPhoto extends TLObject {
    public long access_hash;
    public long id;

    public static TLRPC$InputPhoto TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$InputPhoto result = null;
        switch (constructor) {
            case -74070332:
                result = new TLRPC$TL_inputPhoto();
                break;
            case 483901197:
                result = new TLRPC$TL_inputPhotoEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in InputPhoto", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
