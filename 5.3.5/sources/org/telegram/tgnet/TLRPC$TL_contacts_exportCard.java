package org.telegram.tgnet;

public class TLRPC$TL_contacts_exportCard extends TLObject {
    public static int constructor = -2065352905;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            vector.objects.add(Integer.valueOf(stream.readInt32(exception)));
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
