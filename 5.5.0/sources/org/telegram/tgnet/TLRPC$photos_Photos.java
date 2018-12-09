package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.User;

public abstract class TLRPC$photos_Photos extends TLObject {
    public int count;
    public ArrayList<Photo> photos = new ArrayList();
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$photos_Photos TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLRPC$photos_Photos tLRPC$photos_Photos = null;
        switch (i) {
            case -1916114267:
                tLRPC$photos_Photos = new TLRPC$TL_photos_photos();
                break;
            case 352657236:
                tLRPC$photos_Photos = new TLRPC$TL_photos_photosSlice();
                break;
        }
        if (tLRPC$photos_Photos == null && z) {
            throw new RuntimeException(String.format("can't parse magic %x in photos_Photos", new Object[]{Integer.valueOf(i)}));
        }
        if (tLRPC$photos_Photos != null) {
            tLRPC$photos_Photos.readParams(abstractSerializedData, z);
        }
        return tLRPC$photos_Photos;
    }
}
