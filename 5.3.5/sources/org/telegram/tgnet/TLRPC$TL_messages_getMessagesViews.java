package org.telegram.tgnet;

import java.util.ArrayList;

public class TLRPC$TL_messages_getMessagesViews extends TLObject {
    public static int constructor = -993483427;
    public ArrayList<Integer> id = new ArrayList();
    public boolean increment;
    public TLRPC$InputPeer peer;

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
        this.peer.serializeToStream(stream);
        stream.writeInt32(481674261);
        int count = this.id.size();
        stream.writeInt32(count);
        for (int a = 0; a < count; a++) {
            stream.writeInt32(((Integer) this.id.get(a)).intValue());
        }
        stream.writeBool(this.increment);
    }
}
