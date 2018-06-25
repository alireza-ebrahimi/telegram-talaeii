package org.telegram.tgnet;

import java.util.ArrayList;

public abstract class TLRPC$Photo extends TLObject {
    public long access_hash;
    public String caption;
    public int date;
    public int flags;
    public TLRPC$GeoPoint geo;
    public boolean has_stickers;
    public long id;
    public ArrayList<TLRPC$PhotoSize> sizes = new ArrayList();
    public int user_id;

    public static TLRPC$Photo TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Photo result = null;
        switch (constructor) {
            case -1836524247:
                result = new TLRPC$TL_photo();
                break;
            case -1014792074:
                result = new TLRPC$TL_photo_old2();
                break;
            case -840088834:
                result = new TLRPC$TL_photo_layer55();
                break;
            case 582313809:
                result = new TLRPC$TL_photo_old();
                break;
            case 590459437:
                result = new TLRPC$TL_photoEmpty();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in Photo", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
