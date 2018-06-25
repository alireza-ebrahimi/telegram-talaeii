package org.telegram.tgnet;

public class TLRPC$TL_messages_receivedMessages extends TLObject {
    public static int constructor = 94983360;
    public int max_id;

    public TLObject deserializeResponse(AbstractSerializedData stream, int constructor, boolean exception) {
        TLRPC$Vector vector = new TLRPC$Vector();
        int size = stream.readInt32(exception);
        for (int a = 0; a < size; a++) {
            TLRPC$TL_receivedNotifyMessage object = TLRPC$TL_receivedNotifyMessage.TLdeserialize(stream, stream.readInt32(exception), exception);
            if (object == null) {
                break;
            }
            vector.objects.add(object);
        }
        return vector;
    }

    public void serializeToStream(AbstractSerializedData stream) {
        stream.writeInt32(constructor);
        stream.writeInt32(this.max_id);
    }
}
