package org.telegram.tgnet;

public class TLRPC$TL_contacts_getStatuses extends TLObject {
    public static int constructor = -995929106;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            TLRPC$TL_contactStatus object = TLRPC$TL_contactStatus.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
    }
}
