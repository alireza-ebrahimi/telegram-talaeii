package org.telegram.tgnet;

import java.util.ArrayList;
import org.telegram.tgnet.TLRPC.InputPhoto;

public class TLRPC$TL_photos_deletePhotos extends TLObject {
    public static int constructor = -2016444625;
    public ArrayList<InputPhoto> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData abstractSerializedData, int i, boolean z) {
        TLObject tLRPC$Vector = new TLRPC$Vector();
        int readInt32 = abstractSerializedData.readInt32(z);
        for (int i2 = 0; i2 < readInt32; i2++) {
            tLRPC$Vector.objects.add(Long.valueOf(abstractSerializedData.readInt64(z)));
        }
        return tLRPC$Vector;
    }

    public void serializeToStream(AbstractSerializedData abstractSerializedData) {
        abstractSerializedData.writeInt32(constructor);
        abstractSerializedData.writeInt32(481674261);
        int size = this.id.size();
        abstractSerializedData.writeInt32(size);
        for (int i = 0; i < size; i++) {
            ((InputPhoto) this.id.get(i)).serializeToStream(abstractSerializedData);
        }
    }
}
