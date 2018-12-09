package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.Photo;
import org.telegram.tgnet.TLRPC.User;

public class TLRPC$TL_photos_photo extends TLObject {
    public static int constructor = 539045032;
    public Photo photo;
    public ArrayList<User> users = new ArrayList();

    public static TLRPC$TL_photos_photo TLdeserialize(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        if (constructor == i) {
            TLRPC$TL_photos_photo tLRPC$TL_photos_photo = new TLRPC$TL_photos_photo();
            tLRPC$TL_photos_photo.readParams(abstractSerializedData, z);
            return tLRPC$TL_photos_photo;
        } else if (!z) {
            return null;
        } else {
            throw new RuntimeException(String.format("can't parse magic %x in TL_photos_photo", new Object[]{Integer.valueOf(i)}));
        }
    }

    public void readParams(AbstractSerializedData abstractSerializedData, boolean z) {
        int i = 0;
        this.photo = Photo.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
        int readInt32;
        if (abstractSerializedData.readInt32(z) == 481674261) {
            readInt32 = abstractSerializedData.readInt32(z);
            while (i < readInt32) {
                User TLdeserialize = User.TLdeserialize(abstractSerializedData, abstractSerializedData.readInt32(z), z);
                if (TLdeserialize != null) {
                    this.users.add(TLdeserialize);
                    i++;
                } else {
                    return;
                }
            }
        } else if (z) {
            throw new RuntimeException(String.format("wrong Vector magic, got %x", new Object[]{Integer.valueOf(readInt32)}));
        }
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        this.photo.serializeToStream(abstractSerializedData);
        abstractSerializedData.writeInt32(481674261);
        int size = this.users.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((User) this.users.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
