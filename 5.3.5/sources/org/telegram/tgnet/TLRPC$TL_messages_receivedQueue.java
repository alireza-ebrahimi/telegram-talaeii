package org.telegram.tgnet;

public class TLRPC$TL_messages_receivedQueue extends TLObject {
    public static int constructor = 1436924774;
    public int max_qts;

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
        stream.writeInt32(this.max_qts);
    }
}
