package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_photos_deletePhotos extends TLObject {
    public static int constructor = -2016444625;
    public ArrayList<TLRPC$InputPhoto> id = new ArrayList();

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            vector.objects.add(Long.valueOf(stream.readInt64(exception)));
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            ((TLRPC$InputPhoto) this.id.get(a)).serializeToStream(stream);
        }
    }
}
