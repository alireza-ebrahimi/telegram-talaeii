package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$photos_Photos extends TLObject {
    public int count;
    public ArrayList<TLRPC$Photo> photos = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$photos_Photos TLdeserialize(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$photos_Photos result = null;
        switch (constructor) {
            case -1916114267:
                result = new TLRPC$TL_photos_photos();
                break;
            case 352657236:
                result = new TLRPC$TL_photos_photosSlice();
                break;
        }
        if (result == null && exception) {
            throw new RuntimeException(String.format("can't parse magic %x in photos_Photos", new Object[]{Integer.valueOf(constructor)}));
        }
        if (result != null) {
            result.readParams(stream, exception);
        }
        return result;
    }
}
